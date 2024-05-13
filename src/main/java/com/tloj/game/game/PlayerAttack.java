package com.tloj.game.game;

import com.tloj.game.collectables.Weapon;
import com.tloj.game.entities.Character;
import com.tloj.game.entities.Mob;
import com.tloj.game.utilities.ConsoleColors;
import com.tloj.game.utilities.Dice;


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
    private int bonusDamage;

    /**
     * Constructs a PlayerAttack object with the given attacker and target.
     *
     * @param attacker The character performing the attack.
     * @param target   The target of the attack (can be null if there's no specific target).
     */
    public PlayerAttack(Character attacker, Mob target) {
        super(attacker, target);
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
     * Retrieves the bonus damage of the player.
     *
     * @return The bonus damage.
     */
    public int getBonusDamage() {
        return this.bonusDamage;
    }

    /**
     * Sets the weapon roll of the player.
     *
     * @param weaponRoll The weapon roll to set.
     */
    public void setWeaponRoll(int weaponRoll) {
        this.weaponRoll = weaponRoll;
        this.setTotalDamage();
    }

    /**
     * Sets the bonus damage of the player.
     *
     * @param bonusDamage The bonus damage to set.
     */
    public void setBonusDamage(int bonusDamage) {
        this.bonusDamage = bonusDamage;
        this.setTotalDamage();
    }

    /**
     * Calculates the total damage of the attack.
     */
    @Override
    public void setTotalDamage() {
        this.totalDamage = this.baseDamage + this.weaponRoll + this.bonusDamage - this.targetDef;
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

    /**
     * Performs the attack and prints out the result.
     */
    @Override
    public void perform() {
        super.perform();
        
        Controller.printSideBySideText(
            this.attacker.getASCII(), 
            this.getAttacker().getPrettifiedStatus() + "\n\n" + this.getTarget().getPrettifiedStatus() + "\n\n\n" +
            "Jordan rolled " + this.getWeaponRoll() + ":\n\n" + Dice.getASCII(this.getWeaponRoll())
        );

        this.setBonusDamage(0);

        System.out.println("\nYou inflicted " +  ConsoleColors.RED_BRIGHT + (this.totalDamage >= 0 ? this.totalDamage : 0) + " damage" + ConsoleColors.RESET + " to " + this.target + "!");
        if (this.target.getHP() > 0) System.out.println(this.target + " has " + ConsoleColors.RED + this.target.getHP() + " HP" + ConsoleColors.RESET + " left!");

        Controller.awaitEnter();
    }
}
