package com.tloj.game.game;

import com.tloj.game.entities.Mob;
import com.tloj.game.entities.Character;
import com.tloj.game.utilities.ConsoleHandler;
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
        this.setTotalAttack();
    }

    @Override
    public void setTotalAttack() {
        this.totalAttack = this.baseAttack + this.diceRoll;
    }

    @Override
    public int getTotalDamage() {
        if (this.totalAttack != -1) 
            return this.totalAttack - this.targetDef > 0 ? this.totalAttack - this.targetDef : 0;

        int totalDamage = this.baseAttack + this.diceRoll - this.targetDef;
        return totalDamage > 0 ? totalDamage : 0;
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
            (this.diceRoll != 0 && this.totalAttack != 0 ?
                this.getAttacker() + " rolled " + this.diceRoll + ":\n\n" + Dice.getASCII(this.diceRoll) :
                this.getAttacker() + "'s roll was disabled!\n\n" + Dice.getASCII(0))
        );

        ConsoleHandler.println("\n" + this.getAttacker() + " attacks you back!");
        ConsoleHandler.println(this.attacker + " inflicted " + ConsoleHandler.RED_BRIGHT + this.getTotalDamage() + " damage" + ConsoleHandler.RESET + " to you!");
    }
}
