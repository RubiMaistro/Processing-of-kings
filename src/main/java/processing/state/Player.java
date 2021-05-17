package processing.state;

import lombok.Data;

/**
 * Class representing the players.
 */
@Data
public class Player {
    private Position position;
    private String userName;

    /**
     * Creates a {@code Player} object that indicate the missing parameters.
     */
    public Player(){
        System.out.println("Missing parameters.");
    }

    /**
     * Creates a {@code Player} object for the {@code TableState} object.
     * @param position is a {@code Position} object representing the Player's position on {@code TableState} object.
     */
    public Player(Position position){
        this.position = position;
    }
}
