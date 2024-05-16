package com.tloj.game.abilities;

import com.tloj.game.utilities.ConsoleHandler;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.entities.Mob;
import com.tloj.game.entities.mobs.CyberGoblin;
import com.tloj.game.utilities.Dice;

import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * An ability that allows the mob to steal some money once per fight with a 25% probability <br>
 * It is paired with the {@link CyerbGoblin}.
 */
public class GoblinStealAbility extends MobAbility {
    private static final int DICE_FACES = 3;
    private boolean abilityUsed;

    @JsonCreator
    public GoblinStealAbility(Mob user) {
        super(user);
        this.abilityUsed = false;
    }

    @Override
    public boolean use(PlayerAttack attack) {
        Dice stealingDice = new Dice(DICE_FACES);
        if (this.abilityUsed) return this.used = false;
        if (Math.random() > 0.25) return this.used = false;

        this.abilityUsed = true;
        int stolenMoney = stealingDice.roll() + 3;
        attack.getAttacker().pay(stolenMoney);
        
        this.activationMessage = ConsoleHandler.PURPLE + "Jordan was distracted and the goblin stole " + stolenMoney + " BTC!" + ConsoleHandler.RESET;
        
        return this.used = true;
    }
}