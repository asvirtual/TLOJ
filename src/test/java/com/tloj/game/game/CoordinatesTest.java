package com.tloj.game.game;

import org.junit.jupiter.api.Test;

public class CoordinatesTest {

    @Test
    void equalsTest(){
        Coordinates coordinates1 = new Coordinates(1, 1);
        Coordinates coordinates2 = new Coordinates(1, 1);
        Coordinates coordinates3 = new Coordinates(1, 2);
        
        assert(coordinates1.equals(coordinates2));
        assert(!coordinates1.equals(coordinates3));
    }

    @Test 
    void getAdjacentTest(){
       
    Coordinates centralCoordinates = new Coordinates(1, 1);

    Coordinates northCoordinates = centralCoordinates.getAdjacent(Coordinates.Direction.NORTH);
    Coordinates southCoordinates = centralCoordinates.getAdjacent(Coordinates.Direction.SOUTH);
    Coordinates westCoordinates = centralCoordinates.getAdjacent(Coordinates.Direction.WEST);
    Coordinates eastCoordinates = centralCoordinates.getAdjacent(Coordinates.Direction.EAST);

    assert(northCoordinates.equals(new Coordinates(1, 0)));
    assert(southCoordinates.equals(new Coordinates(1, 2)));
    assert(westCoordinates.equals(new Coordinates(0, 1)));
    assert(eastCoordinates.equals(new Coordinates(2, 1)));

}
    
}
