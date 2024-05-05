package com.tloj.game.entities;

public interface CombatEntity {
    void attack(CombatEntity target);
    void defend();
    void takeDamage(int damage);
    void die();
}
