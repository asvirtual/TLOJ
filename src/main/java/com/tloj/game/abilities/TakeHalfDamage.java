package com.tloj.game.abilities;

import com.tloj.game.entities.Boss;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.entities.bosses.HalverBoss;
import com.fasterxml.jackson.annotation.JsonCreator;


/**
 * An ability that allows a boss to take half damage (base dmg + weapon dice roll). <br>
 * It is paired with the {@link HalverBoss}.<br>
 * @see Boss 
 */
public class TakeHalfDamage extends BossAbility{
    @JsonCreator
    public TakeHalfDamage(Boss boss){
        super(boss);
    }

    @Override
    public void use(PlayerAttack attack) {
        attack.setTotalDamage(attack.getTotalDamage() / 2);
    }
}
