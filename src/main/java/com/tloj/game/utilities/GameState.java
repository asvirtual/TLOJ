package com.tloj.game.utilities;


public enum GameState {
    MAIN_MENU(1),
    CHOOSING_CHARACTER(2),
    MOVING(3),
    INV_MANAGEMENT (4),
    MERCHANT_SHOPPING (6),
    SMITH_FORGING (6),
    FIGHTING_MOB (0),
    FIGHTING_BOSS (0),
    LOOTING_ROOM (0),
    HEALING_ROOM (5),
    BOSS_DEFEATED (0),
    EXIT(0),
    GAME_OVER(0);

    private int value;

    GameState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static GameState fromInt(int value) {
        for (GameState state : GameState.values()) {
            if (state.getValue() == value) {
                return state;
            }
        }
        return null;
    }

    public GameState nextState() {
        return GameState.fromInt(this.getValue() + 1);
    }

    public GameState previousState() {
        return GameState.fromInt(this.getValue() - 1);
    }
}
