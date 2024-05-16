package com.tloj.game.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.items.HealthPotion;
import com.tloj.game.game.CharacterObserver;
import com.tloj.game.game.Controller;
import com.tloj.game.game.Level;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.rooms.Room;
import com.tloj.game.skills.CharacterSkill;
import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;

import org.fusesource.jansi.Ansi;


// Needed to serialize/deserialize subclasses of Character, by including the class name in the JSON
@JsonTypeInfo(
  use = JsonTypeInfo.Id.CLASS, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "@class")

/**
 * Abstract class to represent a character in the game.<br>
 * A Character is a moving entity that can engage in combat with Mobs, and it is controlled by the user.<br>
 * @see MovingEntity
 * @see CombatEntity
 * @see Mob
 * @see PlayerAttack
*/
public abstract class Character extends CombatEntity implements MovingEntity {
    public static final int REQ_XP_BASE = 15;
    public static final int INITIAL_LVL = 1;

    /**
     * Dice faces used to level up hp, mana, attack and defense
     */
    public static final int D5 = 5;
    public static final int D3 = 3;
    
    /** Used for abilities */
    protected int mana; 
    protected int maxMana;
    /** Experience points, needed to level up */
    protected int xp;

    /** Experience points required to reach next level */
    protected int requiredXp;
    /** Current level */
    protected int lvl;
    /** The maximum weight a Character can carry. The sum of the items' weight in the inventory shall always be lower or equal than this field */
    protected int maxWeight;
    protected int money;
    /** A collection of {@link Item}s the Character carries during the game */
    @JsonProperty("inventory")
    protected ArrayList<Item> inventory;
    protected Weapon weapon;
    protected Level currentLevel;
    protected Room currentRoom;
    protected CharacterSkill skill;
    /** Observers to notify when the player is defeated or a mob is defeated */
    protected ArrayList<CharacterObserver> observers = new ArrayList<CharacterObserver>();
    protected boolean usedItem = false;

    /**
     * Constructor to create a Character from loaded data<br>
     * @param hp The character's health points<br>
     * @param atk The character's attack points<br>
     * @param def The character's defense points<br>
     * @param mana The character's mana points<br>
     * @param xp The character's experience points<br>
     * @param lvl The character's level<br>
     * @param maxWeight The character's maximum weight capacity<br>
     * @param money The character's money<br>
     * @param weapon The character's weapon<br>
     * @param inventory The character's inventory<br>
     * @param position The character's position<br>
     * @see com.tloj.game.entities.Entity
     */
    protected Character(
        int hp,
        int atk,
        int def,
        int mana,
        int xp,
        int lvl,
        int maxWeight,
        int money,
        Weapon weapon,
        ArrayList<Item> inventory,
        Coordinates position
    ) {
        super(hp, atk, def, position);
        this.mana = mana;
        this.maxMana = mana;
        this.xp = xp;
        this.lvl = lvl;
        this.maxWeight = maxWeight;
        this.money = money;
        this.inventory = inventory;
        this.weapon = weapon;

        this.updateRequiredXp();
    }

    /** 
     * Constructor to create an entirely new Character<br> 
     * @param hp The character's health points<br>
     * @param atk The character's attack points<br>
     * @param def The character's defense points<br>
     * @param mana The character's mana points<br>
     * @param maxWeight The character's maximum weight capacity<br>
     * @param money The character's money<br>
     * @param weapon The character's weapon<br>
     * @param position The character's position<br>
     * @see Entity
    */
    protected Character(
        int hp,
        int atk,
        int def,
        int mana,
        int maxWeight,
        int money,
        Weapon weapon,
        Coordinates position
    ) {
        super(hp, atk, def, position);
        this.mana = mana;
        this.maxMana = mana;
        this.lvl = INITIAL_LVL;
        this.maxWeight = maxWeight;
        this.money = money;
        this.inventory = new ArrayList<Item>();
        this.weapon = weapon;

        this.inventory.add(new HealthPotion());
        this.updateRequiredXp();
    }

    public Weapon getWeapon() {
        return this.weapon;
    }
    
    @JsonIgnore
    public double getCarriedWeight() {
        double weight = this.weapon.getWeight();
        for (Item item : this.inventory) weight += item.getWeight();
        return Math.floor(weight * 10) / 10;
    }

    public boolean canCarry(Item item) {
        return this.getCarriedWeight() + item.getWeight() <= this.maxWeight;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void pay(int amount) {
        this.money -= amount;
        if (this.money < 0) this.money = 0;
    }

    public boolean canAfford(PurchasableItem item) {
        return this.money >= item.getPrice();
    }

    public int getLvl() {
        return this.lvl;
    }

    public int getMana() {
        return this.mana;
    }

    public int getMaxMana() {
        return this.maxMana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public double getMaxWeight() {
        return this.maxWeight;
    }
    
    @JsonIgnore
    public double getFreeWeight() {
        return Math.floor((this.maxWeight - this.getCarriedWeight()) * 10) / 10;
    }

    public Level getCurrentLevel() {
        return this.currentLevel;
    }

    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    public CharacterSkill getSkill() {
        return this.skill;
    }

    public boolean hasUsedItem() {
        return this.usedItem;
    }

    public void setUsedItem(boolean usedItem) {
        this.usedItem = usedItem;
    }

    public void setCurrentLevel(Level level) {
        this.currentLevel = level;
        this.currentRoom = this.currentLevel.getStartRoom();
        this.position = this.currentRoom.getCoordinates();
    }

    public void useMana(int amount) {
        this.mana -= amount;
    }

    @JsonIgnore
    public int getInventorySize() {
        return this.inventory.size();
    }

    @JsonIgnore
    public Item getInventoryItem(int index) {
        return this.inventory.get(index);
    }

    @JsonIgnore
    public String getInventory() {
        String inventory = ConsoleHandler.YELLOW + "Jordan's Inventory:" + ConsoleHandler.RESET + "\n";
        for (int i = 0; i < this.inventory.size(); i++)
            inventory += (i + 1) + ". " + this.inventory.get(i) + "\n";

        return inventory;
    }

    public Item searchInventoryItem(Item item){
        for (Item i : this.inventory) 
            if (i.equals(item)) return i;
        
        return null;
    }

    public void removeInventoryItem(Item item) {
        this.inventory.remove(item);
        this.sortInventory();
    }

    public Item removeInventoryItem(int index) {
        Item item = this.inventory.remove(index);
        this.sortInventory();

        return item;
    }

    public Item removeRandomInventoryItem() {
        if (this.inventory.size() == 0) return null;

        int index = (int) (Math.random() * this.inventory.size());
        Item removed = this.inventory.remove(index);
        this.sortInventory();
        return removed;
    }

    public boolean addInventoryItem(Item item) {
        if (item == null) return false;

        if (this.getCarriedWeight() + item.getWeight() > this.maxWeight) {
            ConsoleHandler.println(ConsoleHandler.RED + "You can't carry more weight, drop something first." + ConsoleHandler.RESET);
            return false;
        }
        
        this.inventory.add(item);
        this.sortInventory();
        return true;
    }

    public boolean hasItem(Item item) {
        for (Item i : this.inventory)
            if (i.getId() == item.getId()) return true;

        return false;
    }

    @JsonIgnore
    public Item getItemByName(String itemName) {
        for (Item item : this.inventory) 
            if (itemName.equalsIgnoreCase(item.toString())) return item;

        return null;
    }

    /*
     * Sorts the inventory by item id, lower id first
     */
    public void sortInventory() {
        Collections.sort(inventory, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                if (item1.getId() < item2.getId()) return -1;
                else return 1;
            }
        });
    }

    @Override
    public void move(Coordinates to) {
        this.position = to;
        this.currentRoom = this.currentLevel.getRoom(to);
    }

    public void useSkill() {
        if (this.skill == null) {
            ConsoleHandler.println(ConsoleHandler.RED + "You don't have a skill to use" + ConsoleHandler.RESET);
            return;
        }

        if (this.skill.isActivated()) {
            ConsoleHandler.println(ConsoleHandler.RED + "You've already used your skill this turn" + ConsoleHandler.RESET);
            return;
        }

        this.skill.activate();
    }

    @Override
    public void attack(CombatEntity t) throws IllegalArgumentException {
        if (!(t instanceof Mob)) throw new IllegalArgumentException("Characters can only attack Mobs");

        Mob target = (Mob) t;

        PlayerAttack currentAttack = new PlayerAttack(this, target);
        
        if (this.weapon != null) this.weapon.modifyAttack(currentAttack);
        if (this.skill != null) this.skill.execute(currentAttack);
        target.defend(currentAttack);

        currentAttack.perform();

        if (!target.isAlive()) {
            if (target instanceof Boss) this.observers.forEach(observer -> observer.onBossDefeated((Boss) target));
            else this.observers.forEach(observer -> observer.onMobDefeated(target));
        }
    }

    public void restoreMana(int amount) {
        if (this.mana + amount > this.maxMana) this.mana = this.maxMana;
        else this.mana += amount;
    }

    public void lootMob(Mob mob) {
        Item drop = mob.getDrop();
        if (
            drop == null || 
            this.getCarriedWeight() + drop.getWeight() > this.maxWeight ||
            !this.addInventoryItem(drop)
        ) {
            ConsoleHandler.println(ConsoleHandler.GREEN_BOLD_BRIGHT + "You've defeated " + mob + "!" + ConsoleHandler.RESET);
            ConsoleHandler.println("You gain " + ConsoleHandler.GREEN + mob.dropXp() + " experience points"  + ConsoleHandler.RESET +  " and " + ConsoleHandler.YELLOW + mob.getMoneyDrop() + " BTC" + ConsoleHandler.RESET);      
        } else {
            Controller.printSideBySideText(
                drop.getASCII(),
                ConsoleHandler.GREEN_BOLD_BRIGHT + "You've defeated " + mob + "!" + ConsoleHandler.RESET + "\n" +
                ConsoleHandler.YELLOW_BOLD_BRIGHT + mob + " dropped a " + drop + "!" + ConsoleHandler.RESET + "\n" +
                "You gain " + ConsoleHandler.GREEN + mob.dropXp() + " experience points"  + ConsoleHandler.RESET +  " and " + ConsoleHandler.YELLOW + mob.getMoneyDrop() + " BTC" + ConsoleHandler.RESET,
                7
            );
        }

        System.out.println();

        this.addXp(mob.dropXp());
        this.money += mob.getMoneyDrop();
    }

    public void useItem(ConsumableItem item) {
        item.consume(this);
    }

    public void addXp(int amount) {
        this.xp += amount;
        if (this.xp >= this.requiredXp) this.levelUp();
    }

    public void updateRequiredXp() {
        // this.requiredXp = REQ_XP_BASE * (this.lvl * (this.lvl + 1)) / 2;
        this.requiredXp = REQ_XP_BASE * this.lvl;
    }

    public void levelUp() {
        Dice fiveDice = new Dice(D5);
        Dice threeDice = new Dice(D3);
    
        // Store initial stats
        int initialMaxHp = this.maxHp;
        int initialMaxMana = this.maxMana;
        int initialAtk = this.atk;
        int initialDef = this.def;
    
        this.xp = this.xp - REQ_XP_BASE * this.lvl;
        this.lvl++;
        this.updateRequiredXp();
        this.maxHp += fiveDice.roll();
        this.hp = this.maxHp;
        this.maxMana += fiveDice.roll();
        this.mana = this.maxMana;
        this.atk += threeDice.roll();
        this.def += threeDice.roll();
        
        // Update current fight stats keeping eventual buffs from items/skills
        this.currentFightAtk = (this.currentFightAtk - this.atk) + this.atk;
        this.currentFightDef = (this.currentFightDef - this.def) + this.def;

        ConsoleHandler.clearLog();
        ConsoleHandler.println(ConsoleHandler.GREEN_BRIGHT + "You've leveled up! You are now level " + this.lvl + "!\n" + ConsoleHandler.RESET);
    
        // Print changes in stats
        ConsoleHandler.println("HP: " + ConsoleHandler.RED + initialMaxHp + ConsoleHandler.RESET + " --> " + ConsoleHandler.RED_BRIGHT + this.maxHp + ConsoleHandler.RESET);
        ConsoleHandler.println("Mana: " + ConsoleHandler.BLUE + initialMaxMana + ConsoleHandler.RESET + " --> " + ConsoleHandler.BLUE_BRIGHT + this.maxMana + ConsoleHandler.RESET);
        ConsoleHandler.println("Attack: " + ConsoleHandler.PURPLE + initialAtk + ConsoleHandler.RESET + " --> " + ConsoleHandler.PURPLE_BRIGHT + this.atk + ConsoleHandler.RESET);
        ConsoleHandler.println("Defense: " + ConsoleHandler.PURPLE + initialDef + ConsoleHandler.RESET + " --> " + ConsoleHandler.PURPLE_BRIGHT + this.def + ConsoleHandler.RESET + "\n");
    }

    public void addObserver(CharacterObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(CharacterObserver observer) {
        this.observers.remove(observer);
    }

    public boolean swapWeapon(int index) {
        if (index < 1 || index > this.getInventorySize()) {
            ConsoleHandler.println(ConsoleHandler.RED + "Invalid choice" + ConsoleHandler.RESET);
            return false;
        }

        Item item = this.getInventoryItem(index - 1);
        if (item instanceof Weapon) {
            this.inventory.add(this.weapon);

            this.weapon = (Weapon) item;
            this.inventory.remove(item);
            return true;
        } else {
            ConsoleHandler.println(ConsoleHandler.RED + item + " is not a weapon" + ConsoleHandler.RESET);
            return false;
        }
    }

    public void notifyDefeat() {
        this.observers.forEach(observer -> observer.onPlayerDefeated());
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
    }

    @JsonIgnore
    public int getItemCount(Item item) {
        int count = 0;
        for (Item i : this.inventory) 
            if (i.getId() == item.getId()) count++;

        return count;
    }

    @Override 
    public String getCombatASCII() {
        return "";
    }

    @JsonIgnore
    public String getManaBar() {
        return this.getBar(this.mana, this.maxMana);
    }

    @JsonIgnore
    public String getXpBar() {
        return this.getBar(this.xp, this.requiredXp);
    }

    @JsonIgnore
    public String getWeightBar() {
        return this.getBar((int) this.getCarriedWeight(), this.maxWeight);
    }

    @JsonIgnore
    public String getPrettifiedStatus() {
        return 
            "Jordan - lvl " + ConsoleHandler.GREEN_BRIGHT + this.lvl + ConsoleHandler.RESET + ": \n\n" + 
            " ⸭ HP:   " + ConsoleHandler.RED + this.getHpBar() + " " + this.hp + "/" + this.maxHp + ConsoleHandler.RESET + "\n" +
            " ⸭ Mana: " + ConsoleHandler.BLUE + this.getManaBar() + " " + this.mana + "/" + this.maxMana + ConsoleHandler.RESET + "\n" +
            " ⸭ Xp:   " + ConsoleHandler.GREEN + this.getXpBar() + " " + this.xp + "/" + this.requiredXp + ConsoleHandler.RESET + "\n\n";
    }

    @Override
    public String toString() {
        String status = 
            this.getName() + "\n\n" +
           " ⸭ Lvl:  " + ConsoleHandler.GREEN + this.lvl + ConsoleHandler.RESET + "\n" +
            " ⸭ XP:   " + Ansi.ansi().fg(Ansi.Color.GREEN).a(this.getXpBar() + " " + this.xp + "/" + this.requiredXp).reset() + "\n" +
            " ⸭ HP:   " + ConsoleHandler.RED + this.getHpBar() + " " + this.hp + "/" + this.maxHp + ConsoleHandler.RESET + "\n" +
            " ⸭ Mana: " + ConsoleHandler.BLUE + this.getManaBar() + " " + this.mana + "/" + this.maxMana + ConsoleHandler.RESET + "\n" +
            " ⸭ Atk:  " + ConsoleHandler.PURPLE + this.currentFightAtk + ConsoleHandler.RESET + "\n" +
            " ⸭ Def:  " + ConsoleHandler.PURPLE + this.currentFightDef + ConsoleHandler.RESET + "\n" +
            " ⸭ Weapon: " + ConsoleHandler.CYAN + this.weapon + ConsoleHandler.RESET + "\n" +
            " ⸭ Weight: " + ConsoleHandler.YELLOW + this.getWeightBar() + " " + this.getCarriedWeight() + "/" + this.maxWeight + " MB" + ConsoleHandler.RESET + "\n" +
            " ⸭ BTC: " + ConsoleHandler.YELLOW + this.money + ConsoleHandler.RESET + "\n";

        return status;
    }
}
