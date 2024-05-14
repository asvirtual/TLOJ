package com.tloj.game.game;

import com.tloj.game.entities.Mob;
import com.tloj.game.entities.Character;
import com.tloj.game.utilities.ConsoleColors;
import com.tloj.game.utilities.Dice;


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
        super.perform();


        Controller.printSideBySideText(
            this.getAttacker().getCombatASCII().isBlank() ? this.getAttacker().getASCII() : this.getAttacker().getCombatASCII(), 
            this.getAttacker().getPrettifiedStatus() + "\n\n" + this.getTarget().getPrettifiedStatus() + "\n\n" +
            (this.diceRoll != 0 ?
                this.getAttacker() + " rolled " + this.diceRoll + ":\n\n" + Dice.getASCII(this.diceRoll) :
                this.getAttacker() + "'s roll was disabled!\n\n" + Dice.getASCII(0))
        );

        System.out.println("\n" + this.getAttacker() + " attacks you back!");
        System.out.println(this.attacker + " inflicted " + ConsoleColors.RED_BRIGHT + this.totalDamage + " damage" + ConsoleColors.RESET + " to you!");
    }
}
