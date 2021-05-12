package processkings.state;

import lombok.Data;

@Data
public class Player {
    private Position position;

    public Player(){
        System.out.println("Missing parameters.");
    }
    public Player(Position position){
        this.position = position;
    }
}
