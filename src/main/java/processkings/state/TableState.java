package processkings.state;

import java.util.Set;

/**
 * Reprezents the state of table
 */
public class TableState implements Cloneable{

    /**
     * The count of the rows.
     */
    public static final int BOARD_SIZE_ROWS = 6;

    /**
     * The count of the cols.
     */
    public static final int BOARD_SIZE_COLS = 8;

    private Position position;
    private Position enemy_position;
    private static Set<Position> Default_positions;

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
        this.position = (Position) clone(your);
        this.enemy_position = (Position) clone(enemy);
        setDefault_positions();
    }

    private void addPlusDefautPosition(Position position){ Default_positions.add(position); }

    private void checkPositions(Position position) {
        if (! isOnBoard(position)) { throw new IllegalArgumentException(); }
        if (position.equals(enemy_position)) { throw new IllegalArgumentException(); }
    }

    /**
     * {@return a copy of the position of the piece specified}
     *
     * @param n the number of a piece
     */
    public Position getPosition(int n) {
        return position.clone();
    }

    private boolean canMoveUp(){
        return isEmpty(position.getUp()) && haveEightNeighbor(position.getUp().row(),position.getUp().col());
    }

    private boolean canMoveRight() {
        return isEmpty(position.getRight()) && haveEightNeighbor(position.getRight().row(),position.getRight().col());
    }

    private boolean canMoveDown() {
        return isEmpty(position.getDown()) && haveEightNeighbor(position.getDown().row(),position.getDown().col());
    }

    private boolean canMoveLeft() {
        return isEmpty(position.getLeft()) && haveEightNeighbor(position.getLeft().row(),position.getLeft().col());
    }

    private boolean canMoveUpLeft() {
        return isEmpty(position.getUpLeft()) && haveEightNeighbor(position.getUpLeft().row(),position.getUpLeft().col());
    }

    private boolean canMoveUpRight() {
        return isEmpty(position.getUpRight()) && haveEightNeighbor(position.getUpRight().row(),position.getUpRight().col());
    }

    private boolean canMoveDownLeft() {
        return isEmpty(position.getDownLeft()) && haveEightNeighbor(position.getDownLeft().row(),position.getDownLeft().col());
    }

    private boolean canMoveDownRight() {
        return isEmpty(position.getDownRight()) && haveEightNeighbor(position.getDownRight().row(),position.getDownRight().col());
    }


    public void move(Direction direction) {
        switch (direction) {
            case UP -> moveUp();
            case RIGHT -> moveRight();
            case DOWN -> moveDown();
            case LEFT -> moveLeft();
            case UPLEFT -> moveUpLeft();
            case UPRIGHT -> moveUpRight();
            case DOWNLEFT -> moveDownLeft();
            case DOWNRIGHT -> moveDownRight();
        }
    }

    private void moveUp() {  }

    private void  moveRight() { }

    private void moveDown() { }

    private void moveLeft() { }

    private void moveUpLeft() { }

    private void moveUpRight() { }

    private void moveDownLeft() { }

    private void moveDownRight() { }


    private boolean isOnBoard(Position position) {
        return position.row() >= 0 && position.row() < BOARD_SIZE_ROWS &&
                position.col() >= 0 && position.col() < BOARD_SIZE_COLS;
    }

    private boolean isEmpty(Position position) {
        if (position.equals(this.enemy_position)) {
            return false;
        }
        return true;
    }

    /**
     * Set the positions where can not move.
     * Because this positions are not have eight neighbor.
     */
    private void setDefault_positions(){
        for(int row = 0; row < BOARD_SIZE_ROWS; row++) {
            for (int col = 0; col < BOARD_SIZE_COLS; col++) {
                if (row == 0 || row == BOARD_SIZE_ROWS - 1 || col == 0 || col == BOARD_SIZE_COLS - 1)
                    this.Default_positions.add(new Position(row,col));
            }
        }
    }

    private boolean haveEightNeighbor(int row, int col){
        Position Position = new Position(row,col);
            return Default_positions.stream()
                    .noneMatch((Position position) -> Position.equals(position));
    }

    private Object clone(Position position) {
        Position copy = position;
        return copy;
    }

}
