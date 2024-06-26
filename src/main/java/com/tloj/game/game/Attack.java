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

    protected int baseAttack;
    protected int targetDef;
    /** The total sum of the atk stats, not to be confused with the total damage, which also takes into account the target's defense */
    protected int totalAttack;

    /** The action to be performed when the attack hits the target. */
    protected Runnable onHit;

    public Attack(CombatEntity attacker, CombatEntity target) {
        this.attacker = attacker;
        this.target = target;
        this.targetDef = target.getCurrentFightDef();
        this.baseAttack = attacker.getCurrentFightAtk();
        this.totalAttack = -1;
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

    public int getBaseAttack() {
        return this.baseAttack;
    }

    public int getTargetDef() {
        return this.targetDef;
    }
    
    public void setOnHit(Runnable onHit) {
        this.onHit = onHit;
    }

    public void setTargetDef(int targetDef) {
        this.targetDef = targetDef;
    }

    public void setBaseAttack(int baseDamage) {
        this.baseAttack = baseDamage;
        this.setTotalAttack();
    }

    public void setTotalAttack() {
        this.totalAttack = this.baseAttack;
    }

    public int getTotalAttack() {
        return this.totalAttack;
    }

    public void setTotalAttack(int totalDamage) {
        this.totalAttack = totalDamage;
    }

    public int getTotalDamage() {
        if (this.totalAttack != -1) 
            return this.totalAttack - this.targetDef > 0 ? this.totalAttack - this.targetDef : 0;
            
        int totalDamage = this.baseAttack - this.targetDef;
        return totalDamage > 0 ? totalDamage : 0;
    }

    /** 
     * Performs the attack, dealing damage to the target and executing the onHit action.
     */
    public void perform() {
        if (this.getTotalDamage() >= 0) this.target.takeDamage(this.getTotalDamage());
        if (this.onHit != null) this.onHit.run();
    };
}
