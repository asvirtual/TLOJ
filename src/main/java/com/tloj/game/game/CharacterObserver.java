package com.tloj.game.game;

import com.tloj.game.entities.Mob;


public interface CharacterObserver {
    public void onMobDefeated(Mob mob);
    public void onBossDefeated();
    public void onPlayerDefeated();
    // TODO: Decide if these methods should be deleted
    // public void onPlayerLevelUp();
    // public void onPlayerMove();
}
