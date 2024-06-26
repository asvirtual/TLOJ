package com.tloj.game.abilities;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.tloj.game.entities.Boss;


/**
 * Abstract class that represents an ability that a boss can use. <br>
 * {@inheritDoc} <br>
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
