package processkings.results;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.ZonedDateTime;

/**
 * Class representing the result of a game played by a specific player.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameResult {


    private Long id;

    /**
     * The name of the player.
     */
    private String player;

    /**
     * Indicates whether the player has solved the puzzle.
     */
    private boolean solved;

    /**
     * The number of steps made by the player.
     */
    private int steps;

    /**
     * The duration of the game.
     */
    private Duration duration;

    /**
     * The timestamp when the result was saved.
     */
    private ZonedDateTime created;

    protected void onPersist() {
        created = ZonedDateTime.now();
    }

}
