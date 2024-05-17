package com.tloj.game.abilities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.entities.Mob;
import com.tloj.game.entities.mobs.JunkSlime;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.ConsoleHandler;


/**
 * An ability that poisons the attacker with a 45% probability. The player then takes the mob's level as damage per turn during fight <br>
 * It is paired with the {@link JunkSlime}.
 */
public class CyberPoisonAbility extends MobAbility {
    @JsonCreator
    public CyberPoisonAbility(@JsonProperty("user") Mob user) {
        super(user);
        this.activationMessage = ConsoleHandler.PURPLE + "Oh no! Jordan was poisoned and took " + user.getLvl() + " damage!" + ConsoleHandler.RESET;
    }

    @Override
    public boolean use(PlayerAttack attack) {
        if (Math.random() > 0.45) return this.used = false;
        
        attack.getAttacker().takeDamage(this.user.getLvl());
        return this.used = true;
    }
}
