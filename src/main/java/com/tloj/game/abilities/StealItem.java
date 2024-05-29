package com.tloj.game.abilities;

import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.game.Dice;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.entities.Mob;
import com.tloj.game.entities.mobs.CyberGoblin;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * An ability that allows the mob to steal from 3 to 6 BTCs once per fight with a 25% probability <br>
 * It is paired with the {@link CyberGoblin}.
 */
public class StealItem extends MobAbility {
    private static final int DICE_FACES = 3;
    /** Needed to ensure this ability only activates one per fight */
    private boolean usedInCurrentFight;

    @JsonCreator
    public StealItem(@JsonProperty("user") Mob user) {
        super(user);
        this.usedInCurrentFight = false;
    }

    @Override
    public boolean use(PlayerAttack attack) {
        if (this.usedInCurrentFight) return this.used = false;
        if (!this.user.evaluateProbability(0.25)) return this.used = false;
        
        this.usedInCurrentFight = true;
        Dice stealingDice = new Dice(DICE_FACES);
        int stolenMoney = stealingDice.roll() + 3;
        attack.getAttacker().pay(stolenMoney);
        
        this.activationMessage = ConsoleHandler.PURPLE + "Jordan was distracted and the goblin stole " + stolenMoney + " BTC!" + ConsoleHandler.RESET;
        
        return this.used = true;
    }
}