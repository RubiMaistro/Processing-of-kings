package processing.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TableStateTest {

    public TableState tableState;

    @Test
    void testGetOwnValue(){
        Position position1 = new Position(4,4);
        Position position2 = new Position(3,1);

        tableState = new TableState(position1,position2);
        assertEquals(2,tableState.getOwnValue());
    }

    @Test
    void testCanMoveToPositon(){
        Position position1 = new Position(2,4);
        Position position2 = new Position(3,1);

        tableState = new TableState(position1,position2);

        // Down
        assertTrue(tableState.canMove(3,4));
        // Left
        assertTrue(tableState.canMove(2,3));
        // Down-Right
        assertTrue(tableState.canMove(3,5));
        // Up-Left
        assertTrue(tableState.canMove(1,3));

        // Down Down
        assertFalse(tableState.canMove(4,4));
        // Up-Right Right
        assertFalse(tableState.canMove(1,6));

    }

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
    void testExchangePlayers(){
        Position position1 = new Position(4,4);
        Position position2 = new Position(3,1);

        tableState = new TableState(position1,position2);

        assertTrue(tableState.getOwnPosition().row() == 4 && tableState.getOwnPosition().col() == 4);
        tableState.changeNextPlayer();
        assertTrue(tableState.getOwnPosition().row() == 3 && tableState.getOwnPosition().col() == 1);
    }

    @Test
    void testToBeInPositionAPlayer(){
        Position position1 = new Position(2,4);
        Position position2 = new Position(3,1);

        tableState = new TableState(position1,position2);
        assertTrue(tableState.isPlayer(2,4));
        assertTrue(tableState.isPlayer(3,1));
    }

    @Test
    void testIsSolved(){
        Position position1 = new Position(1,1);
        Position position2 = new Position(3,1);

        tableState = new TableState(position1,position2);

        assertFalse(tableState.isSolved());

        Position def1 = new Position(2,1);
        Position def2 = new Position(2,2);
        Position def3 = new Position(1,2);

        tableState.addNewDefaultPosition(def1);
        tableState.addNewDefaultPosition(def2);
        tableState.addNewDefaultPosition(def3);

        assertTrue(tableState.isSolved());
    }

}
