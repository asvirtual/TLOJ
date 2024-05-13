package com.tloj.game.game;

import com.tloj.game.entities.Mob;
import com.tloj.game.entities.Character;
import com.tloj.game.utilities.ConsoleColors;


/**
 * Represents an {@link Attack} performed by a mob. <br>
 * Mobs can attack the player in the game. <br>
 * This class extends the abstract class Attack and adds the mob's dice roll to the attack. <br>
 */
public class MobAttack extends Attack {
    private int diceRoll;

    public MobAttack(Mob attacker, Character target) {
        super(attacker, target);
    }

    public int getDiceRoll() {
        return this.diceRoll;
    }

    public void setDiceRoll(int diceRoll) {
        this.diceRoll = diceRoll;
        this.setTotalDamage();
    }

    @Override
    public void setTotalDamage() {
        this.totalDamage = this.baseDamage + this.diceRoll - this.targetDef;
        if (this.totalDamage < 0) this.totalDamage = 0;
    }

    @Override
    public Mob getAttacker() {
        return (Mob) this.attacker;
    }

    @Override 
    public Character getTarget() {
        return (Character) this.target;
    }

    @Override
    public void perform() {
        System.out.println(this.attacker + " inflicted " + ConsoleColors.RED_BRIGHT + this.totalDamage + " damage" + ConsoleColors.RESET + " to you!");
        super.perform();
    }
}
