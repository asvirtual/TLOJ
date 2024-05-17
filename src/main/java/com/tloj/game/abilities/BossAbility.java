package com.tloj.game.abilities;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tloj.game.effects.WeaponEffect;
import com.tloj.game.entities.Boss;


/**
 * Abstract class that represents an ability that a boss can use. <br>
 * As with the {@link WeaponEffect} class, this class applies an adaptation of the Strategy pattern to the boss abilities, allowing for easy addition of new abilities. <br>
 * This class is meant to be extended by specific boss abilities, guaranteeing modularity. <br>
 * @see Boss
 */
// Needed to serialize/deserialize subclasses of BossAbility, by including the class name in the JSON
@JsonTypeInfo(
  use = JsonTypeInfo.Id.CLASS, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "@class")
public abstract class BossAbility extends MobAbility {
    protected BossAbility(Boss boss) {
        super(boss);
    }

    protected BossAbility(Boss boss, String activationMessage) {
        super(boss, activationMessage);
    }
}
