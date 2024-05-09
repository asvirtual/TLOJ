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
import com.tloj.game.collectables.Weapon;
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
 * {@inheritDoc}
 * @see MovingEntity
 * @see CombatEntity
*/
public abstract class Character extends CombatEntity implements MovingEntity {
    public static final int REQ_XP = 10;
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
     * @param requiredXp The character's required experience points<br>
     * @param lvl The character's level<br>
     * @param maxWeight The character's maximum weight capacity<br>
     * @param money The character's money<br>
     * @param weapon The character's weapon<br>
     * @param ability The character's ability<br>
     * @param passiveAbility The character's passive ability<br>
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
        this.requiredXp = REQ_XP;
        this.lvl = lvl;
        this.maxWeight = maxWeight;
        this.money = money;
        this.inventory = inventory;
        this.weapon = weapon;
        if (weapon != null) this.weapon.assignTo(this);
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
     * @param ability The character's ability<br>
     * @param passiveAbility The character's passive ability<br>
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
        this.xp = 1;
        this.lvl = 1;
        this.maxWeight = maxWeight;
        this.money = money;
        this.inventory = new ArrayList<Item>();
        this.weapon = weapon;
        if (weapon != null) this.weapon.assignTo(this);
    }

    public Weapon getWeapon() {
        return this.weapon;
    }
    
    public int getWeight() {
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

    public int getInventorySize() {
        return this.inventory.size();
    }

    public Item getInventoryItem(int index) {
        return this.inventory.get(index);
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
        if (this.getWeight() + item.getWeight() > this.maxWeight) {
            System.out.println("You can't carry more weight, drop something first.");
            return false;
        }
        
        this.inventory.add(item);
        this.sortInventory();
        return true;
    }

    @JsonIgnore
    public Stream<Item> getInventoryStream() {
        return this.inventory.stream();
    }

    /*
     * TODO: Implement a better sorting algorithm to sort items by type
     */
    private void sortInventory() {
        Collections.sort(inventory, new Comparator<Item>() {
            @Override
            public int compare(Item item1, Item item2) {
                if (item1 instanceof Weapon && !(item2 instanceof Weapon)) return -1;
                else if (!(item1 instanceof Weapon) && item2 instanceof Weapon) return 1;
                else return 0;
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
        
        this.weapon.modifyAttack(this.currentAttack);
        target.defend(this.currentAttack);

        this.currentAttack.perform();
        this.currentAttack = null;

        if (!target.isAlive()) {
            if (target instanceof Boss) this.observers.forEach(observer -> observer.onBossDefeated());
            else this.observers.forEach(observer -> observer.onMobDefeated());
        }
    }

    @Override
    public void die() {}
    
    public void heal(int amount) {
        this.hp += amount;
    }

    public void restoreMana(int amount) {
        this.mana += amount;
    }

    public void lootMob(Mob mob) {
        System.out.println("You gain " + mob.xpDrop + " experience points and " + mob.moneyDrop + " money");
        this.xp += mob.xpDrop;
        this.money += mob.moneyDrop;

        this.addInventoryItem(mob.getDrop());
    }

    public void useItem(ConsumableItem item) {
        item.consume(this);
    }

    public void addXp(int amount) {
        this.xp += amount;
        if (this.xp >= this.requiredXp) this.levelUp();
    }

    public void reqXpUpdate() {
        this.requiredXp += (REQ_XP * lvl);
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

        this.observers.forEach(observer -> observer.onPlayerLevelUp());
    }

    public void addObserver(CharacterObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(CharacterObserver observer) {
        this.observers.remove(observer);
    }
}
