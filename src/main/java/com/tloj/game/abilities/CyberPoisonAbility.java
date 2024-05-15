package com.tloj.game.abilities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.tloj.game.entities.Mob;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.utilities.ConsoleHandler;


public class CyberPoisonAbility extends MobAbility {
    @JsonCreator
    public CyberPoisonAbility(Mob user) {
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
