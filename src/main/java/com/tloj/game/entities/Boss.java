package com.tloj.game.entities;

import com.tloj.game.utilities.ConsoleHandler;

import org.fusesource.jansi.Ansi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tloj.game.collectables.Item;
import com.tloj.game.game.Coordinates;
import com.tloj.game.game.MobAttack;
import com.tloj.game.skills.CharacterSkill;


/**
 * Abstract class to represent the Boss class in the game.
 * Bosses are special Mobs with higher health, attack and defense points and special abilities to challenge the player.
* @see Mob
*/

// Needed to serialize/deserialize subclasses of Character, by including the class name in the JSON
@JsonTypeInfo(
  use = JsonTypeInfo.Id.CLASS, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "@class")
  
public abstract class Boss extends Mob {
    public static final int SCORE_DROP = 50;
    public static final int BOSS_LVL = 1;

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
    public void attack(CombatEntity t) throws IllegalArgumentException {
        if (!(t instanceof Character)) throw new IllegalArgumentException("Mobs can only attack Characters");
        
        Character target = (Character) t;
        MobAttack attack = new MobAttack(this, target);

        ConsoleHandler.clearConsole();
        
        attack.setDiceRoll(this.dice.roll());
        
        CharacterSkill characterSkill = target.getSkill();
        if (characterSkill != null) characterSkill.execute(attack);

        attack.perform();
        
        if (!target.isAlive()) target.notifyDefeat();
    }

    @Override
    public Item getDrop() {
        return this.drop;
    }

    @JsonIgnore
    public String getPrettifiedStatus() {
        return 
            this + ":\n\n" +
            " ⸭ HP: " + Ansi.ansi().fg(Ansi.Color.RED).a(this.getHpBar() + " " + this.hp + "/" + this.maxHp).reset() + "\n";
    }

    @Override
    public String getCombatASCII() {
        return "";
    }
}
