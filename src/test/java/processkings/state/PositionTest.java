package processkings.state;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PositionTest {

    @Test
    void testGetRowAndCol(){
        assertEquals(new Position(1,0).row(),1);
        assertEquals(new Position(0,2).col(),2);
    }

    @Test
    void testGetTarget(){
        Position position = new Position(3,3);
        assertEquals(position.row(), position
                .getTarget(Direction.DOWN)
                .getTarget(Direction.UP)
                .row());
        assertEquals(position.col(), position
                .getTarget(Direction.DOWN)
                .getTarget(Direction.UP)
                .col());
        assertEquals(position.row(), position
                .getTarget(Direction.LEFT)
                .getTarget(Direction.RIGHT)
                .row());
        assertEquals(position.col(), position
                .getTarget(Direction.LEFT)
                .getTarget(Direction.RIGHT)
                .col());
    }


}
