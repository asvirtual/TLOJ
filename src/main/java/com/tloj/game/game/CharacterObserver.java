package com.tloj.game.game;

import com.tloj.game.entities.Boss;
import com.tloj.game.entities.Mob;


/*
 * An interface that represents an observer of the game's characters.<br>
 * This interface is used to notify the game's characters of events that happen in the game.<br>
 */

public interface CharacterObserver {
    public void onMobDefeated(Mob mob);
    public void onBossDefeated(Boss boss);
    public void onPlayerDefeated();
}
