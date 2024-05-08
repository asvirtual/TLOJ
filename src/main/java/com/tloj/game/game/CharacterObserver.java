package com.tloj.game.game;


public interface CharacterObserver {
    public void onMobDefeated();
    public void onBossDefeated();
    public void onPlayerDefeated();
    public void onPlayerLevelUp();
    // public void onPlayerMove();
}
