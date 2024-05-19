package com.tloj.game.entities.mobs;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import com.tloj.game.entities.characters.BasePlayer;
import com.tloj.game.game.PlayerAttack;
import com.tloj.game.collectables.items.HealthPotion;
import com.tloj.game.entities.Character;
import com.tloj.game.collectables.Item;

import com.tloj.game.utilities.Coordinates;


public class MechaRatTest {
    @Test
     void testConstructorLevelTwo() {
        MechaRat mechaRat = new MechaRat(new Coordinates(0, 0), 2);
        
        assertEquals(16, mechaRat.getHp());
        assertEquals(6, mechaRat.getAtk());
        assertEquals(4, mechaRat.getDef());
        assertEquals(6, mechaRat.getCurrentFightAtk());
        assertEquals(4, mechaRat.getCurrentFightDef());
        assertEquals(8, mechaRat.getDiceFaces());
        assertEquals(10, mechaRat.dropXp());
        assertEquals(2, mechaRat.getMoneyDrop());
    }

    @Test
    void testConstructorLevelThree() {
        MechaRat mechaRat = new MechaRat(new Coordinates(0, 0), 3);
        
        assertEquals(24, mechaRat.getHp());
        assertEquals(12, mechaRat.getAtk());
        assertEquals(5, mechaRat.getDef());
        assertEquals(12, mechaRat.getCurrentFightAtk());
        assertEquals(5, mechaRat.getCurrentFightDef());
        assertEquals(8, mechaRat.getDiceFaces());
        assertEquals(15, mechaRat.dropXp());
        assertEquals(2, mechaRat.getMoneyDrop());
    }

    @Test
    void testConstructorLevelGreaterThanThree() {
        MechaRat mechaRat = new MechaRat(new Coordinates(0, 0), 4);
        
        assertEquals(32, mechaRat.getHp());
        assertEquals(15, mechaRat.getAtk());
        assertEquals(6, mechaRat.getDef());
        assertEquals(15, mechaRat.getCurrentFightAtk());
        assertEquals(6, mechaRat.getCurrentFightDef());
        assertEquals(8, mechaRat.getDiceFaces());
        assertEquals(20, mechaRat.dropXp());
        assertEquals(2, mechaRat.getMoneyDrop());
    }    

    @Test
    void testSkillUsed() {
        MechaRat mechaRat = new MechaRat(new Coordinates(0, 0), 1);
        ArrayList <Item> inventory = new ArrayList<Item>();
        inventory.add(new HealthPotion());
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, null, inventory, null);

        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mechaRat);
        mechaRat.defend(mockPlayerAttack);
        if (mechaRat.getAbility().wasUsed()) {
            assertEquals("", mockCharacter.getInventory());
        }
    }

    @Test
    void testSkillNotUsed() {
        MechaRat mechaRat = new MechaRat(new Coordinates(0, 0), 1);
        ArrayList <Item> inventory = new ArrayList<Item>();
        inventory.add(new HealthPotion());
        Character mockCharacter = new BasePlayer(20, 4, 4, 10, 0, 1, 5, 10, null, null, null, inventory, null);

        PlayerAttack mockPlayerAttack = new PlayerAttack(mockCharacter, mechaRat);
        mechaRat.defend(mockPlayerAttack);
        do{
            
            if (!mechaRat.getAbility().wasUsed()) assertEquals(new HealthPotion(), mockCharacter.searchInventoryItem(new HealthPotion()));
            else{
                mechaRat = new MechaRat(new Coordinates(0, 0), 1);
                mockPlayerAttack = new PlayerAttack(mockCharacter, mechaRat);
                mechaRat.defend(mockPlayerAttack);
            }
        }while(!mechaRat.getAbility().wasUsed());
    }
}