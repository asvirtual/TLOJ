package com.tloj.game.game;

import com.tloj.game.entities.Character;
import com.tloj.game.entities.Mob;


/**
 * Represents an attack performed by the player. <br>
 * The player can attack mobs in the game. <br>
 * This class extends the abstract class Attack and adds the player's weapon roll and bonus damage to the attack. <br>
 */
public class PlayerAttack extends Attack {
    private int weaponRoll;
    private int bonusDamage;

    public PlayerAttack(Character attacker, Mob target) {
        super(attacker, target);
    }

    public PlayerAttack(Character attacker) {
        super(attacker, null);
    }

    public int getWeaponRoll() {
        return this.weaponRoll;
    }

    public int getBonusDamage() {
        return this.bonusDamage;
    }

    public void setWeaponRoll(int weaponRoll) {
        this.weaponRoll = weaponRoll;
        this.setTotalDamage();
    }

    public void setBonusDamage(int bonusDamage) {
        this.bonusDamage = bonusDamage;
    }

    @Override
    public void setTotalDamage() {
        this.totalDamage = this.baseDamage + this.weaponRoll + this.bonusDamage - this.targetDef;
    }

    @Override
    public Character getAttacker() {
        return (Character) this.attacker;
    }

    @Override 
    public Mob getTarget() {
        return (Mob) this.target;
    }
}
