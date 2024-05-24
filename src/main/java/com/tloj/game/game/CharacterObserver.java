package com.tloj.game.game;

import com.tloj.game.entities.Boss;
import com.tloj.game.entities.Mob;


/**
 * An interface that represents an observer of the game's characters.<br>
 * A class extending this interface can register itself as an observer and receive notifications from the Character object<br>
 */
public interface CharacterObserver {
    public void onMobDefeated(Mob mob);
    public void onBossDefeated(Boss boss);
    public void onPlayerDefeated();
}
