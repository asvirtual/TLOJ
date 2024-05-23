package com.tloj.game.game;

import com.tloj.game.abilities.MobAbility;
import com.tloj.game.collectables.Weapon;
import com.tloj.game.effects.WeaponEffect;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.Mob;
import com.tloj.game.utilities.ConsoleHandler;


/**
 * Represents an attack performed by the player. <br>
 * The player can attack mobs in the game. <br>
 * This class extends the abstract class Attack and adds the player's weapon roll and bonus damage to the attack. <br>
 * This design respects modularity and scalability, as the action of the attack is its own class that encapsulates different data like <br>
 * @see Attack
 * @see Dice
 * @see Weapon
 */

public class PlayerAttack extends Attack {
    private int weaponRoll;
    private MobAbility targetAbility;
    private WeaponEffect weaponEffect;
    
    /**
     * Constructs a PlayerAttack object with the given attacker and target.
     *
     * @param attacker The character performing the attack.
     * @param target   The target of the attack (can be null if there's no specific target).
     */
    public PlayerAttack(Character attacker, Mob target) {
        super(attacker, target);
        this.targetAbility = target.getAbility();
    }

    /**
     * Constructs a PlayerAttack object with the given attacker.
     *
     * @param attacker The character performing the attack.
     */
    public PlayerAttack(Character attacker) {
        super(attacker, null);
    }

    /**
     * Retrieves the weapon roll of the player.
     *
     * @return The weapon roll.
     */
    public int getWeaponRoll() {
        return this.weaponRoll;
    }

    /**
     * Sets the weapon roll of the player.
     *
     * @param weaponRoll The weapon roll to set.
     */
    public void setWeaponRoll(int weaponRoll) {
        this.weaponRoll = weaponRoll;
        this.setTotalAttack();
    }

    /**
     * Calculates the total attack of the attack.
     */
    @Override
    public void setTotalAttack() {
        this.totalAttack = this.baseAttack + this.weaponRoll;
    }

    @Override
    public int getTotalDamage() {
        if (this.totalAttack != -1) 
            return this.totalAttack - this.targetDef > 0 ? this.totalAttack - this.targetDef : 0;

        int totalDamage = this.baseAttack + this.weaponRoll - this.targetDef;
        return totalDamage > 0 ? totalDamage : 0;
    }

    /**
     * Retrieves the attacker of the attack.
     *
     * @return The attacker character.
     */
    @Override
    public Character getAttacker() {
        return (Character) this.attacker;
    }

    /**
     * Retrieves the target of the attack.
     *
     * @return The target mob.
     */
    @Override 
    public Mob getTarget() {
        return (Mob) this.target;
    }

    public void setTargetAbility(MobAbility targetAbility) {
        this.targetAbility = targetAbility;
    }

    public void setWeaponEffect(WeaponEffect weaponEffect) {
        this.weaponEffect = weaponEffect;
    }

    public void resetStats() {
        this.baseAttack = this.attacker.getCurrentFightAtk();
        this.targetDef = this.target.getCurrentFightDef();
        this.weaponRoll = 0;
        this.totalAttack = 0;
        this.getAttacker().setUsedItem(false);
    }

    /**
     * Performs the attack and prints out the result.
     */
    @Override
    public void perform() {
        super.perform();
        
        Controller.printSideBySideText(
            this.attacker.getASCII(), 
            this.getAttacker().getPrettifiedStatus() + "\n\n" + this.getTarget().getPrettifiedStatus() +
            (this.weaponEffect != null && this.weaponEffect.wasUsed() ? "\n" + this.weaponEffect.getActivationMessage() : "") + 
            (this.targetAbility != null && this.targetAbility.wasUsed() ? "\n" + this.targetAbility.getActivationMessage() : "") + 
            "\n\n" + 
            (this.getWeaponRoll() != 0 ? 
                "Jordan rolled " + this.getWeaponRoll() + ":\n\n" + Dice.getASCII(this.getWeaponRoll()) : 
                "Oh no! Jordan's roll was disabled!\n\n" + Dice.getASCII(0)) 
        );

        ConsoleHandler.println("\nYou inflicted " +  ConsoleHandler.RED_BRIGHT + this.getTotalDamage() + " damage" + ConsoleHandler.RESET + " to " + this.target + "!");
        if (this.target.getHp() > 0) ConsoleHandler.println(this.target + " has " + ConsoleHandler.RED + this.target.getHp() + " HP" + ConsoleHandler.RESET + " left!");

        Controller.awaitEnter();
        this.resetStats();
    }
}
