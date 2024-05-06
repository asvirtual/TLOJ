package com.tloj.game.entities;

import java.util.ArrayList;

import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;

public class Cheater extends Character {
    
    private static int HP = 15;
    private static int ATTACK = 3;
    private static int DEFENCE = 3;
    private static int MANA = 30;
    private static int MONEY = 10;
    private static ArrayList<Item> items = new ArrayList<Item>();
    //private static passiveAbility ABILITY = new passiveAbility("weapon", 3);
    private static Dice dice = new Dice(10);
    private static Weapon PULSE_STAFF = new Weapon(dice, ABILITY);

    public Cheater(Coordinates position, int lvl) {
        super(HP, ATTACK, DEFENCE, MANA, getWeight(items), MONEY, PULSE_STAFF, ability, "passiveAbilityValue", position);
    }


}
 
