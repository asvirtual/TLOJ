package com.tloj.game.entities;

import java.util.ArrayList;

import com.tloj.game.collectables.Item;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.utilities.Coordinates;
import com.tloj.game.utilities.Dice;

public class NeoSamurai extends Character {
    
    private static int HP = 20;
    private static int ATTACK = 7;
    private static int DEFENCE = 1;
    private static int MANA = 10;
    private static int MONEY = 15;
    private static ArrayList<Item> items = new ArrayList<Item>();
    //private static passiveAbility ABILITY = new passiveAbility("weapon", 3);
    private static Dice dice = new Dice(10);
    private static Weapon CYBER_KATANA = new Weapon(dice, ABILITY);

    public NeoSamurai(Coordinates position, int lvl) {
        super(HP, ATTACK, DEFENCE, MANA, getWeight(items), MONEY, CYBER_KATANA, ability, "passiveAbilityValue", position);
    }


}

