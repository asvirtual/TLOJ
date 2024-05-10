package com.tloj.game.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.collectables.items.AttackElixir;
import com.tloj.game.collectables.items.HealthPotion;
import com.tloj.game.collectables.items.ManaPotion;
import com.tloj.game.game.CharacterObserver;
import com.tloj.game.game.Level;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.skills.CharacterSkill;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;


// Needed to avoid circular references with Weapon when serializing/deserializing 
@JsonIdentityInfo(
  generator = ObjectIdGenerators.IntSequenceGenerator.class, 
  property = "@id")
// Needed to serialize/deserialize subclasses of Character, by including the class name in the JSON
@JsonTypeInfo(
  use = JsonTypeInfo.Id.CLASS, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "@class")

/**
 * Abstract class to represent a character in the game.<br>
 * A Character is a moving entity that can engage in combat, and it is controlled by the user.<br>
 * @see MovingEntity
 * @see CombatEntity
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

    /** Experience points required */
    protected int requiredXp;
    /** Current level */
    protected int lvl;
    /** The maximum weight a Character can carry. The sum of the items' weight in the {@link inventory} shall be lower or equal than this field */
    protected int maxWeight;
    protected int money;
    /** A collection of {@link Item}s the Character carries during the game */
    protected ArrayList<Item> inventory;
    protected Weapon weapon;
    protected Level currentLevel;
    protected PlayerAttack currentAttack;
    protected CharacterSkill skill;
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
     * @see com.tloj.game.entities.MovingEntity
     * @see com.tloj.game.entities.CombatEntity 
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

        if (weapon != null) this.weapon.assignTo(this);
        this.reqXpUpdate();
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
     * @see MovingEntity
     * @see CombatEntity
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
        if (weapon != null) this.weapon.assignTo(this);

        this.inventory.add(new HealthPotion());
        this.reqXpUpdate();
    }

    public Weapon getWeapon() {
        return this.weapon;
    }
    
    @JsonIgnore
    public int getCarriedWeight() {
        int weight = 0;
        for (Item item : this.inventory)
            weight += item.getWeight();

        return weight;
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

    public Level getCurrentLevel() {
        return this.currentLevel;
    }

    public void setCurrentLevel(Level level) {
        this.currentLevel = level;
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
        return this.inventory.contains(item);
    }

    public Item getItem(String itemName) {
        for (Item item : this.inventory) 
            if (itemName.equalsIgnoreCase(item.toString())) return item;

        return null;
    }

    @JsonIgnore
    public Stream<Item> getInventoryStream() {
        return this.inventory.stream();
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
        else this.currentAttack.setTarget(target);

        if (this.weapon != null) this.weapon.modifyAttack(this.currentAttack);
        target.defend(this.currentAttack);

        this.currentAttack.perform();
        this.currentAttack = null;

        if (!target.isAlive()) {
            if (target instanceof Boss) this.observers.forEach(observer -> observer.onBossDefeated());
            else this.observers.forEach(observer -> observer.onMobDefeated(target));
        }
    }
    
    public void heal(int amount) {
        this.hp += amount;
    }

    public void restoreMana(int amount) {
        this.mana += amount;
    }

    public void lootMob(Mob mob) {
        System.out.println("You gain " + mob.xpDrop + " experience points and " + mob.moneyDrop + " BTC");

        this.addXp(mob.xpDrop);
        this.money += mob.moneyDrop;

        Item drop = mob.getDrop();
        if (this.addInventoryItem(drop)) System.out.println("You found a " + drop);
    }

    public void useItem(ConsumableItem item) {
        item.consume(this);
    }

    public void addXp(int amount) {
        this.xp += amount;
        if (this.xp >= this.requiredXp) this.levelUp();
    }

    public void reqXpUpdate() {
        this.requiredXp += REQ_XP_BASE * this.lvl;
    }

    public void levelUp(){
        Dice fiveDice = new Dice(D5);
        Dice threeDice = new Dice(D3);

        this.lvl++;
        this.xp = 0;
        this.reqXpUpdate();
        this.maxHp += fiveDice.roll();
        this.hp = this.maxHp;
        this.maxMana += fiveDice.roll();
        this.mana = this.maxMana;
        this.atk += threeDice.roll();
        this.def += threeDice.roll();
    }

    public void addObserver(CharacterObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(CharacterObserver observer) {
        this.observers.remove(observer);
    }

    public void swapWeapon(int index) {
        if (index < 0 || index >= this.getInventorySize()) {
            System.out.println("Invalid index");
            return;
        }

        Item item = this.getInventoryItem(index);
        if (item instanceof Weapon) {
            this.weapon = (Weapon) item;
            this.weapon.assignTo(this);
        } else {
            System.out.println("This item is not a weapon");
        }
    }

    @Override
    public void takeDamage(int damage) {
        super.takeDamage(damage);
        if (!this.isAlive()) this.observers.forEach(observer -> observer.onPlayerDefeated());
    }

    /**
     * TODO: Better graphical representation of the Character's status
     */
    @Override
    public String toString() {
        String status = "HP: " + this.hp + "/" + this.maxHp + " | Mana: " + this.mana + "/" + this.maxMana + " | Atk: " + this.currentFightAtk + " | Def: " + this.currentFightDef + " | Lvl: " + this.lvl + " | XP: " + this.xp + "/" + this.requiredXp + " | BTC: " + this.money;
        return status;
    }

    @JsonIgnore
    public int getItemCount(Item item) {
        int count = 0;
        for (Item i : this.inventory) 
            if (i.equals(item)) count++;

        return count;
    }
}
