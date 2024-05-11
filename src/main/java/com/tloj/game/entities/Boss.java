package com.tloj.game.entities;

import com.tloj.game.utilities.Coordinates;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tloj.game.abilities.BossAbility;
import com.tloj.game.collectables.Item;
import com.tloj.game.game.Attack;
import com.tloj.game.game.PlayerAttack;


// Needed to serialize/deserialize subclasses of Character, by including the class name in the JSON
@JsonTypeInfo(
  use = JsonTypeInfo.Id.CLASS, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "@class")
  
public abstract class Boss extends Mob {
    public static final int SCORE_DROP = 50;
    public static final int BOSS_LVL = 1;
    
    protected BossAbility ability;

    protected Boss(
        int hp,
        int atk,
        int def,
        int diceFaces,
        int xpDrop,
        int moneyDrop,
        Coordinates position
    ) {
        super(hp, atk, def, diceFaces, BOSS_LVL, xpDrop, moneyDrop, position);
    }

    @Override
    public void defend(Attack attack) {
        super.defend(attack);
        if (!(attack instanceof PlayerAttack)) return;

        if (this.ability == null) return;
        this.ability.use((PlayerAttack) attack);
    }

    @Override
    public Item getDrop() {
        return this.drop;
    }

    @Override
    public String getCombatASCII() {
        return "";
    }
}
