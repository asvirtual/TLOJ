package com.tloj.game.game;

import com.tloj.game.entities.CombatEntity;


/**
 * An abstract class that represents an attack in the game.<br>
 * Attacks are actions that can be performed by CombatEntities.<br>
 * They can be used to deal damage to other CombatEntities.<br>
 * This design respects modularity and scalability, as the action of the attack is its own class that encapsulates different data like<br>
 * the attacker, target and all different damages/defenses and allows for complex attack mechanics in the game to coexist.<br>
 * @see CombatEntity
 */
public abstract class Attack {
    protected CombatEntity attacker;
    protected CombatEntity target;

    protected int baseDamage;
    protected int targetDef;
    protected int totalDamage;

    /** The action to be performed when the attack hits the target. */
    protected Runnable onHit;

    public Attack(CombatEntity attacker, CombatEntity target) {
        this.attacker = attacker;
        this.target = target;
        this.baseDamage = attacker.getCurrentFightAtk();
    }

    public CombatEntity getAttacker() {
        return this.attacker;
    };
    
    public CombatEntity getTarget() {
        return this.target;
    };

    public void setAttacker(CombatEntity attacker) {
        this.attacker = attacker;
    }

    public void setTarget(CombatEntity target) {
        this.target = target;
    }

    public int getBaseDamage() {
        return this.baseDamage;
    }

    public int getTargetDef() {
        return this.targetDef;
    }

    public int getTotalDamage() {
        return this.totalDamage;
    }

    public void setOnHit(Runnable onHit) {
        this.onHit = onHit;
    }

    public void setTargetDef(int targetDef) {
        this.targetDef = targetDef;
        this.setTotalDamage();
    }

    public void setBaseDamage(int baseDamage) {
        this.baseDamage = baseDamage;
        this.setTotalDamage();
    }

    public void setTotalDamage() {
        this.totalDamage = this.baseDamage - this.targetDef;
    }

    public void setTotalDamage(int totalDamage) {
        this.totalDamage = totalDamage;
    }

    public void perform() {
        if (this.totalDamage >= 0) this.target.takeDamage(this.totalDamage);
        if (this.onHit != null) this.onHit.run();
    };
}
