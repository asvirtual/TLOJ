package com.tloj.game.entities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;

import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.game.CharacterObserver;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;


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

    protected Object ability;
    protected Object passiveAbility;
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
        Object ability,
        Object passiveAbility,
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
        this.weapon.assignTo(this);
        this.ability = ability;
        this.passiveAbility = passiveAbility;
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
        Object ability,
        Object passiveAbility,
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
        this.weapon.assignTo(this);
        this.ability = ability;
        this.passiveAbility = passiveAbility;
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

    public void pay(int amount) {
        this.money -= amount;
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
    }

    public void removeInventoryItem(int index) {
        this.inventory.remove(index);
    }

    public void addInventoryItem(Item item) {
        this.inventory.add(item);
        this.sortInventory();
    }

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
                if (item1 instanceof Weapon && !(item2 instanceof Weapon)) {
                    return -1;
                } else if (!(item1 instanceof Weapon) && item2 instanceof Weapon) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
    }

    @Override
    public void move(Coordinates to) {
        this.position = to;
        // this.observer.update();
    }

    @Override
    public void attack(CombatEntity t) throws IllegalArgumentException {
        if (!(t instanceof Mob)) throw new IllegalArgumentException("Characters can only attack Mobs");

        Mob target = (Mob) t;
        PlayerAttack attack = new PlayerAttack(this, target);
        
        this.weapon.modifyAttack(attack);
        target.defend(attack);

        attack.perform();
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

        if (mob instanceof Boss) {
            Boss boss = (Boss) mob;
            this.addInventoryItem(boss.getDrop());
        }    
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

        // this.observers.forEach(observers -> observers.onLevelUp());
    }

    public void addObserver(CharacterObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(CharacterObserver observer) {
        this.observers.remove(observer);
    }
}
