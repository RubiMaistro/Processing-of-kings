package processkings.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TableStateTest {

    public TableState tableState;

    @Test
    void testPlayerMoveToPosition(){
        Position position1 = new Position(4,4);
        Position position2 = new Position(3,1);

        tableState = new TableState(position1,position2);
        tableState.setSavedClickPosition(position1);
        tableState.move(3,3);

        assertEquals(3,tableState.getOwnPosition().row());
        assertEquals(3,tableState.getOwnPosition().col());
    }

    @Test
    void testGetOwnValue(){
        Position position1 = new Position(4,4);
        Position position2 = new Position(3,1);

        tableState = new TableState(position1,position2);
        assertEquals(2,tableState.getOwnValue());
    }

    /*
    @Test
    void testSetDefaultPositionIsDefaultPosition(){
        Position position1 = new Position(5,5);
        Position position2 = new Position(6,7);
        tableState = new TableState(position1,position2);

        assertTrue(tableState.isPlayer(5,5));
        assertFalse(tableState.isPlayer(5,4));

        assertTrue(tableState.isPlayer(6,7));
        assertFalse(tableState.isPlayer(6,4));
    }
     */


}
