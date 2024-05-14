package com.tloj.game.collectables.items;

import com.tloj.game.collectables.ConsumableItem;
import com.tloj.game.collectables.PurchasableItem;
import com.tloj.game.entities.Character;
import com.tloj.game.utilities.Constants;
import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * Represents the Consumable Item EMP in the game.<br>
 * The EMP allows the player to skip trap rooms without dice rolls, it can be found or purchased in the game.<br>
 * It weighs {@value #WEIGHT}, with an in game cost of {@value #PRICE}
 * @see GreatHealthPotion
 * @see HealthPotion
 * @see ManaPotion
 * @see GreatManaPotion
 * @see AttackElixir
 * @see DefenseElixir
 * @see WeaponShard
 * @see Ragu
 * @see SpecialKey
 * @see NorthStar
*/
public class Emp extends PurchasableItem implements ConsumableItem {
    private static final double WEIGHT = 0.1;
    private static final int PRICE = 35;
    private static final double DROP_CHANCE = 0.05;
    private static final int DROP_MONEY = PRICE / 2;
    private static final int ID = 8;

    @JsonCreator
    public Emp() {
        super(PRICE, WEIGHT, DROP_MONEY, ID, DROP_CHANCE); 
    }
    
    @Override
    public void consume(Character consumer) {
        consumer.removeInventoryItem(this);
    }    

    @Override
    public String getASCII() {
        return Constants.EMP;
    }

    @Override
    public String describe() {
        return this.getASCII() + "\nA tool that allows you to skip trap rooms without dice rolls";
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Emp;
    }
}

