package com.tloj.game.abilities;

import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.entities.Mob;
import com.tloj.game.entities.mobs.MechaRat;
import com.tloj.game.collectables.Item;

import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * An ability that allows the mob to destroy a random item from the player's inventory once per fight with a 20% probability <br>
 * It is paired with the {@link MechaRat}.
 */
public class RatBiteAbility extends MobAbility {
    private boolean abilityUsed;
    
    @JsonCreator
    public RatBiteAbility(Mob user) {
        super(user);
        this.abilityUsed = false;
    }

    @Override
    public boolean use(PlayerAttack attack) {
        if (this.abilityUsed) return this.used = false;
        if (Math.random() > 0.20) return this.used = false;

        this.abilityUsed = true;
        Item destroyedItem = attack.getAttacker().removeRandomInventoryItem();
        if (destroyedItem != null) {
            this.activationMessage = ConsoleHandler.PURPLE + "Jordan was distracted and the rat ate" + destroyedItem + ConsoleHandler.RESET;
        }
        
        return this.used = true;
    }
}