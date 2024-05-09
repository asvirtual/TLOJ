package com.tloj.game.utilities;


public enum GameState {
    MAIN_MENU(1),
    CHOOSING_CHARACTER(2),
    MOVING(3),
    INV_MANAGEMENT (4),
    MERCHANT_SHOPPING (6),
    SMITH_FORGING (6),
    FIGHTING_MOB (5),
    FIGHTING_BOSS (5),
    LOOTING_ROOM (5),
    HEALING_ROOM (5),
    BOSS_DEFEATED (7),
    EXIT(8),
    GAME_OVER(9);

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

    public void nextState() {
        this.value = this.getValue() + 1;
    }

    public void previousState() {
        if (this.getValue() == 5) {
            this.value = this.getValue() - 2;
        } else {
            this.value = this.getValue() - 1;
        }
    }
}
