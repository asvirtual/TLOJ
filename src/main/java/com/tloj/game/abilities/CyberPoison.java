package com.tloj.game.abilities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import com.tloj.game.entities.Mob;
import com.tloj.game.entities.mobs.JunkSlime;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.ConsoleHandler;


/**
 * An ability that poisons the attacker with a 45% probability, by inflicting to the player an amount of damage equals to the mob's level<br>
 * It is paired with the {@link JunkSlime}.
 */
public class CyberPoison extends MobAbility {
    @JsonCreator
    public CyberPoison(@JsonProperty("user") Mob user) {
        super(user);
        this.activationMessage = ConsoleHandler.PURPLE + "Oh no! Jordan was poisoned and took " + user.getLvl() + " damage!" + ConsoleHandler.RESET;
    }

    @Override
    public boolean use(PlayerAttack attack) {
        if (!this.user.evaluateProbability(0.45)) return this.used = false;
        
        attack.getAttacker().takeDamage(this.user.getLvl());
        return this.used = true;
    }
}
