package com.tloj.game.game;


/**
 * Represents the different states of the game<br>
 * The game can be in one of these states at any given time<br>
 * The state of the game determines what the player can do and what the game will do next<br>
 */

public enum GameState {
    MAIN_MENU,
    CHOOSING_CHARACTER,
    MOVING,
    MERCHANT_SHOPPING,
    SMITH_FORGING,
    FIGHTING_MOB,
    FIGHTING_BOSS,
    LOOTING_ROOM,
    HEALING_ROOM,
    BOSS_DEFEATED,
    EXIT,
    GAME_OVER,
    WIN
}
