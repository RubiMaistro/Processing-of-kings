package processkings.state;

import lombok.Data;

@Data
public class GameState {

    private Player WHITE = new Player(new Position(3,0));
    private Player BLACK = new Player(new Position(4,7));

    private TableState tableState = new TableState(WHITE.getPosition(),BLACK.getPosition());

    public GameState(){
        while(true){
        }
    }

}


