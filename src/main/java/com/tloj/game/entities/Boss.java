package com.tloj.game.entities;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.abilities.BossAbility;
import com.tloj.game.collectables.Item;
import com.tloj.game.game.Attack;
import com.tloj.game.game.PlayerAttack;


public abstract class Boss extends Mob {
    protected BossAbility ability;
    protected Item drop;

    protected Boss(
        int hp,
        int atk,
        int def,
        int diceFaces,
        int lvl,
        int xpDrop,
        int moneyDrop,
        BossAbility ability,
        Item drop,
        Coordinates position
    ) {
        super(hp, atk, def, diceFaces, lvl, xpDrop, moneyDrop, position);
        this.ability = ability;
        this.drop = drop;
    }

    /* Overrides of the CombatEntity method will be needed as the combat dinamycs of the Boss differ from the Mob's */

    @Override
    public void attack(CombatEntity target) {
        target.takeDamage(this.atk);
    }

    @Override
    public void die() {
        
    }

    public void useAbilityAgainst(Character player) {
        
    }

    @Override
    public void defend(Attack attack) {
        super.defend(attack);
        if (!(attack instanceof PlayerAttack)) return;

        if (this.ability == null) return;
        this.ability.use((PlayerAttack) attack);
    }
}
