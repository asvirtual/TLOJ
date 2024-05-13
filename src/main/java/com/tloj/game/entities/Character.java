package com.tloj.game.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.items.HealthPotion;
import com.tloj.game.game.CharacterObserver;
import com.tloj.game.game.Level;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.game.PlayerRoomVisitor;
import com.tloj.game.rooms.Room;
import com.tloj.game.skills.CharacterSkill;
import com.tloj.game.utilities.ConsoleColors;
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
    public static final int REQ_XP_BASE = 10;
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
    protected ArrayList<Item> inventory;
    protected Weapon weapon;
    protected Level currentLevel;
    protected Room currentRoom;
    protected PlayerAttack currentAttack;
    protected CharacterSkill skill;
    // Observers to notify when the player is defeated or a mob is defeated
    protected ArrayList<CharacterObserver> observers = new ArrayList<CharacterObserver>();

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

    public int getHp() {
        return this.hp;
    }

    public int getMaxHp() {
        return this.maxHp;
    }

    public void setHp(int hp) {
        this.hp = hp;
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
    
    public double getFreeWeight() {
        return Math.floor((this.maxWeight - this.getCarriedWeight()) * 10) / 10;
    }

    public Level getCurrentLevel() {
        return this.currentLevel;
    }

    public Room getCurrentRoom() {
        return this.currentRoom;
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

    public Item searchInventoryItem(Item item){
        for (Item i : this.inventory) 
            if (i.equals(item)) return i;
        
        return null;
    }

    public void removeInventoryItem(Item item) {
        this.inventory.remove(item);
        this.sortInventory();
    }

    public void removeInventoryItem(int index) {
        this.inventory.remove(index);
        this.sortInventory();
    }

    public boolean addInventoryItem(Item item) {
        if(item == null) return false;

        if (this.getCarriedWeight() + item.getWeight() > this.maxWeight) {
            System.out.println("You can't carry more weight, drop something first.");
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
        this.currentAttack = null;
    }

    public void useSkill() {
        if (this.currentAttack == null) this.currentAttack = new PlayerAttack(this);
        this.skill.use(this.currentAttack);
    }

    @Override
    public void attack(CombatEntity t) throws IllegalArgumentException {
        if (!(t instanceof Mob)) throw new IllegalArgumentException("Characters can only attack Mobs");

        Mob target = (Mob) t;

        if (this.currentAttack == null) this.currentAttack = new PlayerAttack(this, target);
        else {
            this.currentAttack.setOnHit(null);
            this.currentAttack.setBaseDamage(this.currentFightAtk);
            this.currentAttack.setTarget(target);
        }

        if (this.weapon != null) this.weapon.modifyAttack(this.currentAttack);
        target.defend(this.currentAttack);

        // If skill has an effect to be executed on use, do it
        this.skill.executeOnUse();
        this.currentAttack.perform();

        if (!target.isAlive()) {
            this.currentAttack = null;
            if (target instanceof Boss) this.observers.forEach(observer -> observer.onBossDefeated());
            else this.observers.forEach(observer -> observer.onMobDefeated(target));
        }
    }
    
    public void heal(int amount) {
        if (this.hp + amount > this.maxHp) this.hp = this.maxHp;
        else this.hp += amount;
    }

    public void restoreMana(int amount) {
        if (this.mana + amount > this.maxMana) this.mana = this.maxMana;
        else this.mana += amount;
    }

    public void lootMob(Mob mob) {
        System.out.println("You gain " + ConsoleColors.GREEN + mob.dropXp() + " experience points"  + ConsoleColors.RESET +  " and " + ConsoleColors.YELLOW + mob.getMoneyDrop() + " BTC" + ConsoleColors.RESET);

        this.addXp(mob.dropXp());
        this.money += mob.getMoneyDrop();

        Item drop = mob.getDrop();
        if (drop == null) return;
        if (this.getCarriedWeight() + drop.getWeight() > this.maxWeight) return;
        if (this.addInventoryItem(drop)) {
            System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + mob + " dropped a " + drop + ConsoleColors.RESET);
            System.out.println(drop.getASCII());
        }
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

        System.out.println(ConsoleColors.GREEN_BRIGHT + "You've leveled up! You are now level " + this.lvl + "!\n" + ConsoleColors.RESET);
    
        // Print changes in stats
        System.out.println("\n" + "HP: " + ConsoleColors.RED + initialMaxHp + ConsoleColors.RESET + " --> " + ConsoleColors.RED_BRIGHT + this.maxHp + ConsoleColors.RESET);
        System.out.println("Mana: " + ConsoleColors.BLUE + initialMaxMana + ConsoleColors.RESET + " --> " + ConsoleColors.BLUE_BRIGHT + this.maxMana + ConsoleColors.RESET);
        System.out.println("Attack: " + ConsoleColors.PURPLE + initialAtk + ConsoleColors.RESET + " --> " + ConsoleColors.PURPLE_BRIGHT + this.atk + ConsoleColors.RESET);
        System.out.println("Defense: " + ConsoleColors.PURPLE + initialDef + ConsoleColors.RESET + " --> " + ConsoleColors.PURPLE_BRIGHT + this.def + ConsoleColors.RESET + "\n");
    }

    public void addObserver(CharacterObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(CharacterObserver observer) {
        this.observers.remove(observer);
    }

    public void swapWeapon(int index) {
        if (index < 1 || index > this.getInventorySize()) {
            System.out.println("Invalid choice");
            return;
        }

        Item item = this.getInventoryItem(index - 1);
        if (item instanceof Weapon) {
            this.inventory.add(this.weapon);

            this.weapon = (Weapon) item;
            this.inventory.remove(item);
        } else {
            System.out.println(item + " is not a weapon");
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
            "Jordan - lvl " + this.lvl + ": \n\n" + 
            " ⸭ HP:   " + ConsoleColors.RED + this.getHpBar() + " " + this.hp + "/" + this.maxHp + ConsoleColors.RESET + "\n" +
            " ⸭ Mana: " + ConsoleColors.BLUE + this.getManaBar() + " " + this.mana + "/" + this.maxMana + ConsoleColors.RESET + "\n" +
            " ⸭ Xp:   " + ConsoleColors.GREEN + this.getXpBar() + " " + this.xp + "/" + this.requiredXp + ConsoleColors.RESET + "\n\n";
    }

    @Override
    public String toString() {
        String status = 
            String.join(" ", this.getClass().getSimpleName().split("(?=[A-Z])")) + "\n\n" +
            " ⸭ Lvl:  " + ConsoleColors.GREEN + this.lvl + ConsoleColors.RESET + "\n" +
            " ⸭ XP:   " + Ansi.ansi().fg(Ansi.Color.GREEN).a(this.getXpBar() + " " + this.xp + "/" + this.requiredXp).reset() + "\n" +
            " ⸭ HP:   " + ConsoleColors.RED + this.getHpBar() + " " + this.hp + "/" + this.maxHp + ConsoleColors.RESET + "\n" +
            " ⸭ Mana: " + ConsoleColors.BLUE + this.getManaBar() + " " + this.mana + "/" + this.maxMana + ConsoleColors.RESET + "\n" +
            " ⸭ Atk:  " + ConsoleColors.PURPLE + this.currentFightAtk + ConsoleColors.RESET + "\n" +
            " ⸭ Def:  " + ConsoleColors.PURPLE + this.currentFightDef + ConsoleColors.RESET + "\n" +
            " ⸭ Weapon: " + ConsoleColors.CYAN + this.weapon + ConsoleColors.RESET + "\n" +
            " ⸭ Weight: " + ConsoleColors.YELLOW + this.getWeightBar() + " " + this.getCarriedWeight() + "/" + this.maxWeight + " MB" + ConsoleColors.RESET + "\n" +
            " ⸭ BTC: " + ConsoleColors.YELLOW + this.money + ConsoleColors.RESET + "\n";

        return status;
    }
}
