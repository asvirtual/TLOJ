package com.tloj.game.abilities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tloj.game.entities.Mob;
import com.tloj.game.game.PlayerAttack;


public abstract class MobAbility {
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
