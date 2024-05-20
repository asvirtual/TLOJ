package com.tloj.game.rooms.roomeffects;

import static org.junit.jupiter.api.Assertions.*;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import com.tloj.game.collectables.weapons.LaserBlade;
import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.Controller;
import com.tloj.game.rooms.Room;
import com.tloj.game.game.Game;
import com.tloj.game.game.Level;
import com.tloj.game.rooms.TrapRoom;
import com.tloj.game.utilities.Dice;
import com.tloj.game.utilities.Coordinates;

public class InflictDamageTest {
    
    @Test
    public void applyEffectTest(){
        
        ArrayList<ArrayList<Room>> floor = new ArrayList<>();
        ArrayList<Room> rooms = new ArrayList<>();
        ArrayList<Level> levels = new ArrayList<>();

        Coordinates coordinates = new Coordinates(0, 0);
        TrapRoom mockRoom = new TrapRoom(coordinates, new InflictDamage());
        
        rooms.add(mockRoom);
        floor.add(rooms);
        
        Level level = new Level(1, floor);
        levels.add(level);
        

        BasePlayer mockCharacter = new BasePlayer(20, 3, 3, 10, 0, 1, 5, 10, level, mockRoom, new LaserBlade(), null, coordinates);
        
        Game mockGame = new Game(1, level, mockCharacter, levels, 2, 0, 0);
        Controller.getInstance().setGame(mockGame);
        
        int startHp = mockCharacter.getHp();
        
        
        do{
            mockRoom.triggerTrap(mockCharacter);
            int endHp = mockCharacter.getHp();
            
            if(endHp != startHp){
                assertEquals(startHp - InflictDamage.DAMAGE, endHp);
                return;
            }
        }while(true);
    }
}
