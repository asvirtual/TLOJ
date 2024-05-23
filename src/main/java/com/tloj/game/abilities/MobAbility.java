 package com.tloj.game.abilities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.tloj.game.entities.Mob;
import com.tloj.game.game.PlayerAttack;


/**
 * Abstract class representing an ability that a mob can use during combat. <br>
 * @see Mob
 */

// Needed to serialize/deserialize subclasses of MobAbility, by including the class name in the JSON
@JsonTypeInfo(
  use = JsonTypeInfo.Id.CLASS, 
  include = JsonTypeInfo.As.PROPERTY, 
  property = "@class")
  
public abstract class MobAbility {
    @JsonProperty("user")
    @JsonManagedReference
    protected Mob user;
    protected String activationMessage;
    protected boolean used = false;

    protected MobAbility(Mob user) {
        this.user = user;
        this.activationMessage = "";
    }

    protected MobAbility(Mob user, String activationMessage) {
        this.user = user;
        this.activationMessage = activationMessage;
    }

    @JsonIgnore
    public String getActivationMessage() {
        return this.activationMessage;
    }

    public boolean wasUsed() {
        return this.used;
    }

    @JsonIgnore
    public String getName()  {
        return String.join(" ", this.getClass().getSimpleName().split("(?=[A-Z])"));
    }
    
    public abstract boolean use(PlayerAttack attack);    
}
