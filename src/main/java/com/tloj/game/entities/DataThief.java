package com.tloj.game.entities;


import java.util.ArrayList;

import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;

public class DataThief extends Character {
    
    private static int HP = 16;
    private static int ATTACK = 5;
    private static int DEFENCE = 3;
    private static int MANA = 10;
    private static int MONEY = 0;
    private static ArrayList<Item> items = new ArrayList<Item>();
    //private static passiveAbility ABILITY = new passiveAbility("weapon", 3);
    private static Dice dice = new Dice(6);
    private static Weapon NANO_DIRK = new Weapon(dice, ABILITY);

    public DataThief(Coordinates position, int lvl) {
        super(HP, ATTACK, DEFENCE, MANA, getWeight(items), MONEY, NANO_DIRK, ability, "passiveAbilityValue", position);
    }


}
