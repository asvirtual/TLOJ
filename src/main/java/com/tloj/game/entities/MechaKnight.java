package com.tloj.game.entities;

import java.util.ArrayList;

import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;

public class MechaKnight extends Character {
    
    private static int HP = 30;
    private static int ATTACK = 2;
    private static int DEFENCE = 6;
    private static int MANA = 5;
    private static int MONEY = 15;
    private static ArrayList<Item> items = new ArrayList<Item>();
    //private static passiveAbility ABILITY = new passiveAbility("weapon", 3);
    private static Dice dice = new Dice(20);
    private static Weapon PLASMA_GREATSWORD = new Weapon(dice, ABILITY);

    public MechaKnight(Coordinates position, int lvl) {
        super(HP, ATTACK, DEFENCE, MANA, getWeight(items), MONEY, PLASMA_GREATSWORD, ability, "passiveAbilityValue", position);
    }


}
