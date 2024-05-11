package com.tloj.game.rooms;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tloj.game.game.PlayerRoomVisitor;
import com.tloj.game.utilities.Coordinates;


public class EndRoom extends StartRoom {
    @JsonCreator
    public EndRoom(@JsonProperty("coordinates") Coordinates coordinates) {
        super(coordinates);
    }

    @Override
    public RoomType getType() {
        return RoomType.END_ROOM;
    }

    @Override
    public void accept(PlayerRoomVisitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String toString() {
        return "E";
    }
    
}
