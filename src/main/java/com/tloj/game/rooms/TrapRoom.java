package com.tloj.game.rooms;

import com.tloj.game.utilities.Coordinates;
import com.tloj.game.game.PlayerRoomVisitor;
import com.tloj.game.rooms.roomeffects.RoomEffect;
import com.tloj.game.entities.Character;


public class TrapRoom extends Room {
    private RoomEffect effect;

    public TrapRoom(Coordinates coordinates, RoomEffect effect) {
        super(coordinates);
        this.effect = effect;
    }

    @Override
    public RoomType getType() {
        return RoomType.TRAP_ROOM;
    }

    public void triggerTrap(Character character) {
        this.effect.applyEffect(character);
    }

    @Override
    public void accept(PlayerRoomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public void exit() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'exit'");
    }

    @Override
    public String toString() {
        if(this.isVisited())
            return "\u2566" + " ";
        else
            return "\u00A0" + " ";
    }

}
