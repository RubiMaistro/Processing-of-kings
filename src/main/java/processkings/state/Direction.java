package processkings.state;

public enum Direction {

    UP(-1, 0),
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1),

    UPLEFT(-1,-1),
    UPRIGHT(-1, 1),
    DOWNLEFT(1,-1),
    DOWNRIGHT(1,1);

    private int rowChange;
    private int colChange;

    private Direction(int row, int col) {
        this.rowChange = row;
        this.colChange = col;
    }

    /**
     * Returns the change in the x-coordinate when moving a step in this
     * direction.
     *
     * @return the change in the x-coordinate when moving a step in this
     * direction
     */
    public int getRowChange() {
        return rowChange;
    }

    /**
     * Returns the change in the y-coordinate when moving a step in this
     * direction.
     *
     * @return the change in the y-coordinate when moving a step in this
     * direction
     */
    public int getColChange() {
        return colChange;
    }

    /**
     * Returns the direction that corresponds to the changes in the x-coordinate
     * and the y-coordinate specified.
     *
     * @param dx the change in the x-coordinate
     * @param dy the change in the y-coordinate
     * @return the direction that corresponds to the changes in the x-coordinate
     * and the y-coordinate specified
     */
    public static Direction of(int dx, int dy) {
        for (Direction direction : values()) {
            if (direction.rowChange == dx && direction.colChange == dy) {
                return direction;
            }
        }
        throw new IllegalArgumentException();
    }

}
