package processkings.state;

import javafx.geometry.Pos;
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
    public static final int[][] INITIAL_TABLE = {
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0}
    };

    private Position ownPosition;
    private Position enemyPosition;

    private static int[][] BoardTable = INITIAL_TABLE;
    private ArrayList<Position> DefaultPositions = new ArrayList<Position>();

    private Position savedClickPosition = null;
    private Position defaultClickPosition = null;

    private boolean wasMoving = false;

    /**
     * Creates a {@code PuzzleState} object that corresponds to the original initial state of the puzzle.
     */
    public TableState(){
        System.out.println("Missing parameters.");
    }

    /**
     * Creates a {@code PuzzleState} object initializing the positions of the pieces with the positions specified.
     * The constructor expects an array of four {@code Position} objects or four {@code Position} objects.
     */
    public TableState(Position your, Position enemy){
        checkPositions(your);
        checkPositions(enemy);
        this.ownPosition = your.clone();
        this.enemyPosition = enemy.clone();
        setDefault_positions();
        setValuesInBoardTable();
    }

    public boolean canMove(int i, int j){
        Position position = new Position(i,j);
        return isEmpty(position) && isOnBoard(position) && haveEightNeighbor(position) && isDistanceOneFromPosition(position);
    }

    public void move(Position Player, Position Target, Position Default){
        Direction direction = Direction.of(Target.row() - Player.row(),Target.col() - Player.col());

        this.BoardTable[Target.row()][Target.col()] = BoardTable[Player.row()][Player.col()];
        BoardTable[Player.row()][Player.col()] = 0;


        // Change position
        ownPosition.setTarget(direction);
        System.out.println("\nset");

        // Move value
        this.BoardTable[ownPosition.row()][ownPosition.col()] = getValueFromBoardTable(savedClickPosition.row(),savedClickPosition.col());
        this.BoardTable[savedClickPosition.row()][savedClickPosition.col()] = 0;

        // Set true because it was moving
        this.wasMoving = true;

        // Add to the Default Table a new default position
        if(defaultClickPosition != null){
            addNewDefaultPosition(defaultClickPosition);
            System.out.println("\naddDEF");
        }

    }

    private Direction getDirectionToMove(int row, int col){
        if (! canMove(row, col)) {
            throw new IllegalArgumentException();
        }
        return Direction.of(emptyRow - row, emptyCol - col);
    }

    public boolean isSolved(){
        boolean bool = true;
        for (int i = 0; i<BOARD_SIZE_ROWS; i++) {
            for(int j = 0; j<BOARD_SIZE_COLS; j++) {
                if(BoardTable[i][j] == 0 && isDistanceOneFromPosition(new Position(i,j))) {
                    bool = false;
                }
            }
        }
        return bool;
    }

    public int getValueFromBoardTable(int i, int j){
        return this.BoardTable[i][j];
    }

    private void checkPositions(Position position) {
        if (!isOnBoard(position) && !isOnBoard(enemyPosition))
            throw new IllegalArgumentException();

        if (position.equals(enemyPosition)) {
            throw new IllegalArgumentException();
        }
    }

    public void setBoardTableValue(int i, int j, int v){
        BoardTable[i][j] = v;
    }

    public int getBoardTableValue(int i, int j){
        return BoardTable[i][j];
    }

    private void setValuesInBoardTable(){
        for(int row = 0; row < BOARD_SIZE_ROWS; row++) {
            for (int col = 0; col < BOARD_SIZE_COLS; col++) {
                if (isInDefaultTable(row,col))
                    this.BoardTable[row][col] = 1;
                if (ownPosition.row() == row && ownPosition.col() == col)
                    this.BoardTable[row][col] = 2;
                else if (enemyPosition.row() == row && enemyPosition.col() == col)
                    this.BoardTable[row][col] = 3;
            }
        }
    }

    /**
     * Set the positions where can not move.
     * Because this positions are not have eight neighbor.
     */
    private void setDefault_positions(){
        for(int row = 0; row < BOARD_SIZE_ROWS; row++) {
            for (int col = 0; col < BOARD_SIZE_COLS; col++) {
                Position position = new Position(row,col);
                if (row == 0 || row == BOARD_SIZE_ROWS - 1 || col == 0 || col == BOARD_SIZE_COLS - 1)
                    this.DefaultPositions.add(position);
            }
        }
    }

    public boolean getWasMoving(){
        return this.wasMoving;
    }

    private boolean isInDefaultTable(int row, int col){
        return DefaultPositions.stream()
                .anyMatch((element) -> element.row() == row && element.col() == col);
    }

    private void addNewDefaultPosition(Position position){
        DefaultPositions.add(position);
    }

    private boolean isOnBoard(Position position) {
        return position.row() >= 0 && position.row() < BOARD_SIZE_ROWS &&
                position.col() >= 0 && position.col() < BOARD_SIZE_COLS;
    }

    private boolean haveEightNeighbor(Position position){
        int countPosition = 0;
        for (int i = 0; i<BOARD_SIZE_ROWS; i++)
            for(int j = 0; j<BOARD_SIZE_COLS; j++)
                if(isDistanceOneFromPosition(new Position(i,j)))
                    countPosition += 1;
        return countPosition == 8;
    }

    private boolean isDistanceOneFromPosition(Position position){
        return Math.abs(position.row() - ownPosition.row()) == 1
                || Math.abs(position.col() - ownPosition.col()) == 1;
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

        for (int i = 0; i < BOARD_SIZE_ROWS; i++) {
            for (int j = 0; j < BOARD_SIZE_COLS; j++)
                System.out.print(tableState.getValueFromBoardTable(i, j) + " ");
            System.out.println();
        }

        System.out.println(tableState.isInDefaultTable(0,4));


            for (int j = 0; j < 15; j++)
                System.out.print("(" +tableState.DefaultPositions.get(j).row() + "," + tableState.DefaultPositions.get(j).col() + ") ");
            System.out.println();


    }

}
