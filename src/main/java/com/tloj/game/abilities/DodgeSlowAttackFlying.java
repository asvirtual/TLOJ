package com.tloj.game.abilities;

import com.tloj.game.entities.Boss;
import com.tloj.game.entities.bosses.FlyingBoss;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.ConsoleHandler;
import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * An ability that allows a boss to dodge player's attack and heal itself of player's weapon roll with a 33% probability. <br>
 * It is paired with the {@link FlyingBoss}.
 */

public class DodgeSlowAttackFlying extends BossAbility {
    @JsonCreator
    public DodgeSlowAttackFlying(Boss boss) {
        super(boss);
    }

    @Override
    public boolean use(PlayerAttack attack) {
        if (Math.random() > 0.333) return this.used = false;

        this.boss.heal(attack.getWeaponRoll());
        attack.setTotalAttack(0);

        this.activationMessage = 
            ConsoleHandler.PURPLE + "Oh no! " + String.join(" ", this.getClass().getSimpleName().split("(?=[A-Z])")) + 
            " dodged your attack and healed itself for " + attack.getWeaponRoll() + " HP!" + ConsoleHandler.RESET;

        return this.used = true;
    }
}
