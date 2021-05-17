package processing.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Class representing the result of a game played by a specific player.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameResult {


    /**
     * The name of the player.
     */
    private String name;

    /**
     * The number of steps made by the player.
     */
    private int steps;

    /**
     * Indicates whether the player has win the game.
     */
    private int solved;

    /**
     * The timestamp when the result was saved.
     */
    private Date created;

}
