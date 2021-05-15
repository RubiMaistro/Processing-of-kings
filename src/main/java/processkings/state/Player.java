package processkings.state;

import lombok.Data;

@Data
public class Player {
    private Position position;
    private String userName;

    public Player(){
        System.out.println("Missing parameters.");
    }
    public Player(Position position){
        this.position = position;
    }
}
