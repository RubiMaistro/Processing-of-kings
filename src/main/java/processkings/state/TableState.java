package processkings.state;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Reprezents the state of table
 */
@Data
public class TableState implements Cloneable{

    /**
     * The count of the rows.
     */
    public static final int BOARD_SIZE_ROWS = 6;

    /**
     * The count of the cols.
     */
    public static final int BOARD_SIZE_COLS = 8;

    /**
     * The row of the empty space.
     */
    @Setter(AccessLevel.NONE)
    private int emptyRow;

    /**
     * The column of the empty space.
     */
    @Setter(AccessLevel.NONE)
    private int emptyCol;

    /**
     * Initializing the board table.
     */
    public static int[][] INITIAL_TABLE = {
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0}
    };

    private Position ownPosition;
    private Position enemyPosition;

    private static int[][] BoardTable;
    private ArrayList<Position> DefaultPositions;
    private ArrayList<Direction> moveList;

    private Position savedClickPosition;
    private Position defaultClickPosition;

    /**
     * Creates a {@code TableState} object that indicate the missing parameters.
     */
    public TableState(){
        System.out.println("Missing parameters.");
        initialMoveList();
    }

    /**
     * Creates a {@code TableState} object initializing the positions of the pieces with the positions specified.
     */
    public TableState(Position your, Position enemy){
        checkPositions(your);
        checkPositions(enemy);
        this.ownPosition = your.clone();
        this.enemyPosition = enemy.clone();
        initialState();
        System.out.println("Constructor.");
    }

    private void initialState(){
        BoardTable = INITIAL_TABLE;
        DefaultPositions = new ArrayList<Position>();
        moveList = new ArrayList<Direction>();
        setDefaultPositions();
        setValuesInBoardTable();
        setInitialTable();
        initialMoveList();
    }

    /**
     *
     * @param row
     * @param col
     */
    public void move(int row, int col){
        Direction direction = Direction.of( row - savedClickPosition.row(),col -savedClickPosition.col());

        // Change position
        ownPosition.setTarget(direction);

        // Move value
        var value = getValueFromBoardTable(savedClickPosition.row(),savedClickPosition.col());
        BoardTable[ownPosition.row()][ownPosition.col()] = value;
        BoardTable[savedClickPosition.row()][savedClickPosition.col()] = 0;
    }

    public void addNewDefaultPosition(Position position){
        DefaultPositions.add(position);
        setValuesInBoardTable();
    }

    public boolean isSolved(){
        int countEmptyPositions = 0;
        for (int i = 0; i<BOARD_SIZE_ROWS; i++) {
            for(int j = 0; j<BOARD_SIZE_COLS; j++) {
                if(isDistanceOne(new Position(i,j)) && BoardTable[i][j] == 0) {
                    countEmptyPositions++;
                }
            }
        }
        return countEmptyPositions == 0;
    }

    public boolean isEqualOwnPosition(int i, int j){
        return this.ownPosition.row() == i && this.ownPosition.col() == j;
    }

    public boolean isPlayer(int row, int col){
        if(ownPosition.row() == row && ownPosition.col() == col || enemyPosition.row() == row && enemyPosition.col() == col)
            return true;
        else
            return false;
    }

    public boolean canMove(int i, int j){
        Position position = new Position(i,j);
        return isEmpty(position)
                && isOnBoard(position)
                && isHaveEightNeighbor(position)
                && isDistanceOne(position);
    }


    public int getValueFromBoardTable(int i, int j){
        return BoardTable[i][j];
    }

    public int getOwnValue(){
        return BoardTable[ownPosition.row()][ownPosition.col()];
    }

    /**
     * Step to next player
     * Exchange the player's
     */
    public void changeNextPlayer(){
        Position p = ownPosition;
        ownPosition = enemyPosition;
        enemyPosition = p;

        if(isInDefaultTable(ownPosition.row(),ownPosition.col())){
            BoardTable[ownPosition.row()][ownPosition.col()] = 3;
        }
    }

    private void checkPositions(Position position) {
        if (!isOnBoard(position) && !isOnBoard(enemyPosition))
            throw new IllegalArgumentException();
        if (position.equals(enemyPosition)) {
            throw new IllegalArgumentException();
        }
    }

    public void setInitialTable(){
        for(int row = 0; row < BOARD_SIZE_ROWS; row++) {
            for (int col = 0; col < BOARD_SIZE_COLS; col++) {
                if (isInDefaultTable(row,col))
                    BoardTable[row][col] = 1;
                if (ownPosition.row() == row && ownPosition.col() == col)
                    BoardTable[row][col] = 2;
                else if (enemyPosition.row() == row && enemyPosition.col() == col)
                    BoardTable[row][col] = 3;
            }
        }
    }

    private void setValuesInBoardTable(){
        for(int row = 0; row < BOARD_SIZE_ROWS; row++) {
            for (int col = 0; col < BOARD_SIZE_COLS; col++) {
                if (isInDefaultTable(row,col))
                    BoardTable[row][col] = 1;
            }
        }
    }

    /**
     * Set the positions where can not move.
     * Because this positions are not have eight neighbor.
     */
    public void setDefaultPositions(){
        for(int row = 0; row < BOARD_SIZE_ROWS; row++) {
            for (int col = 0; col < BOARD_SIZE_COLS; col++) {
                Position position = new Position(row,col);
                if (!isHaveEightNeighbor(position) ){
                    this.DefaultPositions.add(position);
                }
            }
        }
    }

    private boolean isInDefaultTable(int row, int col){
        return DefaultPositions.stream()
                .anyMatch((element) -> element.row() == row && element.col() == col);
    }

    private boolean isOnBoard(Position position) {
        return position.row() >= 0 && position.row() < BOARD_SIZE_ROWS &&
                position.col() >= 0 && position.col() < BOARD_SIZE_COLS;
    }

    private boolean isHaveEightNeighbor(Position position){
        return isOnBoard(position.getTarget(Direction.UP))
                && isOnBoard(position.getTarget(Direction.RIGHT))
                && isOnBoard(position.getTarget(Direction.DOWN))
                && isOnBoard(position.getTarget(Direction.LEFT))
                && isOnBoard(position.getTarget(Direction.UP_LEFT))
                && isOnBoard(position.getTarget(Direction.UP_RIGHT))
                && isOnBoard(position.getTarget(Direction.DOWN_LEFT))
                && isOnBoard(position.getTarget(Direction.DOWN_RIGHT));
    }

    private void initialMoveList(){
        moveList.add(Direction.UP);
        moveList.add(Direction.RIGHT);
        moveList.add(Direction.DOWN);
        moveList.add(Direction.LEFT);
        moveList.add(Direction.UP_LEFT);
        moveList.add(Direction.UP_RIGHT);
        moveList.add(Direction.DOWN_LEFT);
        moveList.add(Direction.DOWN_RIGHT);
    }

    private boolean isDistanceOne(Position position){
        return isHaveEightNeighbor(position)
                && moveList.stream()
                    .anyMatch((direction) -> ownPosition.getTarget(direction).row() == position.row()
                            && ownPosition.getTarget(direction).col() == position.col());
    }



    private boolean isEmpty(Position position) {
        return BoardTable[position.row()][position.col()] == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (! (o instanceof TableState)) {
            return false;
        }
        return o.getClass().equals(TableState.class);
    }


    public static void main(String[] args) {
        Position your = new Position(2, 0);
        Position enemy = new Position(3, 7);
        TableState tableState = new TableState(your , enemy);

        Direction direction = Direction.of(1,0);

        for (int i = 0; i < BOARD_SIZE_ROWS; i++) {
            for (int j = 0; j < BOARD_SIZE_COLS; j++)
                System.out.print(tableState.getValueFromBoardTable(i, j) + " ");
            System.out.println();
        }

        System.out.println(tableState.isInDefaultTable(0,4));

        tableState.setSavedClickPosition(new Position(2,0));
        //tableState.move(2,1);



        Position a = new Position(0,0);
        Position b = new Position(1,1);

        System.out.println(a.row() + " " + a.col());
        System.out.println(b.row() + " " + b.col());

        Position p = a;
        a = b;
        b = p;

        System.out.println("Change");
        System.out.println(a.row() + " " + a.col());
        System.out.println(b.row() + " " + b.col());


    }

}
