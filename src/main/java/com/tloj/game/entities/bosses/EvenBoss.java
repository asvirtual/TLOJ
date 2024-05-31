package com.tloj.game.entities.bosses;

import com.tloj.game.abilities.DodgeEvenRoll;
import com.tloj.game.collectables.items.WeaponShard;
import com.tloj.game.entities.Boss;
import com.tloj.game.game.Coordinates;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.abilities.BossAbility;
import com.tloj.game.utilities.Constants;


/**
 * Represents the EvenBoss, called Cyfartal, boss entity in the game.<br>
 * The EvenBoss is the first boss of the game. It has a special ability that nullifies received damage is weapon roll is even<br>
 * It has {@value #HP} health points, {@value #ATTACK} attack points, {@value #DEFENSE} defense points, {@value #DICE_FACES} dice faces, {@value #XP_DROP} experience points drop, {@value #MONEY_DROP} money drop.
 * This boss is paired with the {@link DodgeEvenRoll} ability and the {@link WeaponShard} drop.    
 * @see Boss
 * @see FlyingBoss
 * @see HalverBoss
 * @see BossAbility
 * @see VampireDodge
 * @see HalveDamage
 * @see WeaponShard
 */
public class EvenBoss extends Boss {
    public static final int HP = 50;
    public static final int ATTACK = 6;
    public static final int DEFENSE = 2;
    public static final int DICE_FACES = 10;
    public static final int XP_DROP = 30;
    public static final int MONEY_DROP = 20;
    
    @JsonCreator
    public EvenBoss(
        @JsonProperty("position")
        Coordinates position
    ) {
        super(HP, ATTACK, DEFENSE, DICE_FACES, XP_DROP, MONEY_DROP,position);
        this.ability = new DodgeEvenRoll(this);
        this.drop = new WeaponShard();
    }

    @Override
    public String toString() {
        return "Cyfartal, Even Lord of the Dice";
    }

    @Override
    public String getASCII() {
        return Constants.EVENBOSS;
    }
}
