import java.util.ArrayList;
import java.util.List;

public class Board {

    private Tile[][] gameBoard;
    private int[] columnCounts;
    private int[] rowCounts;
    private List<CoordinatePair> treeLocations;
    private List<CoordinatePair> tentLocations;

    Board(){
        gameBoard = new Tile[0][0];
        columnCounts = new int[0];
        rowCounts = new int[0];
        treeLocations = new ArrayList<>();
        tentLocations = new ArrayList<>();
    }

    /**
     *  Constructor that accepts int[][] argument for the board
     */
    public Board(int[][] gameBoard, int[]columnCounts, int[] rowCounts){
        this(convertIntsToTiles(gameBoard), columnCounts, rowCounts);
    }

    public Board(Tile[][] gameBoard, int[] columnCounts, int[] rowCounts){
        this.gameBoard = gameBoard;
        this.columnCounts = columnCounts;
        this.rowCounts = rowCounts;

        //CoordinatePair array that holds coordinates of each tree
        treeLocations = new ArrayList<>();

        //Puts the locations of each tree into the array
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                if (gameBoard[i][j] == Tile.TREE){
                    treeLocations.add(new CoordinatePair(i, j));
                }
            }
        }
        tentLocations = new ArrayList<>();
    }

    /**
     * Converts an array of ints to Tile enums for simple board creation input
     */
    private static Tile[][] convertIntsToTiles(int[][] gameBoard){
        Tile[][] convertedBoard = new Tile[gameBoard.length][gameBoard[0].length];
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard[0].length; j++) {
                convertedBoard[i][j] = BoardManager.intToTile(gameBoard[i][j]);
            }
        }
        return convertedBoard;
    }

    /**
     * Changes the tiles state when the player taps/clicks on it
     * @param x horizontal
     * @param y vertical
     */
    void cycleTile(int x, int y) {
        switch (gameBoard[x][y]) {
            case TREE:
                break;
            case EMPTY:
                gameBoard[x][y] = Tile.GRASS;
                break;
            case GRASS:
                gameBoard[x][y] = Tile.TENT;
                tentLocations.add(new CoordinatePair(x,y));
                break;
            case TENT:
                gameBoard[x][y] = Tile.EMPTY;
                tentLocations.remove(new CoordinatePair(x,y));
                break;
        }
    }

    /**
     * @see Board#cycleTile(int, int)
     * @param tile coordinate of tile
     */
    void cycleTile (CoordinatePair tile){
        cycleTile(tile.getX(), tile.getY());
    }
    //Generally, when working with multiple pairs of x and y, the CoordinatePair object is
    //used. Otherwise, where possible, plain x and y is passed

    List<CoordinatePair> getTentCoordinates() {
        return tentLocations;
    }

    List<CoordinatePair> getTreeCoordinates() {
        return treeLocations;
    }

    CoordinatePair[] getTentCoordinatesAsArray(){
        return (CoordinatePair[]) tentLocations.toArray();
    }

    CoordinatePair[] getTreeCoordinatesAsArray(){
        return (CoordinatePair[]) treeLocations.toArray();
    }

    Tile[][] getBoard() {
        return gameBoard;
    }

    int[] getColumnArray() {
        return columnCounts;
    }

    int[] getRowArray() {
        return rowCounts;
    }

    int getBoardSize() {
        return gameBoard.length;
    }

    Tile getTile(int x, int y){
        return gameBoard[x][y];
    }

    Tile getTile(CoordinatePair location) {
        return getTile(location.getX(), location.getY());
    }

    int getTileAsInt(int x, int y){
        return BoardManager.tileToInt(getTile(x,y));
    }

    int getTileAsInt(CoordinatePair location){
        return getTileAsInt(location.getX(), location.getY());
    }

    int getColumnIndex(int x){
        return columnCounts[x];
    }

    int getRowIndex(int x){
        return rowCounts[x];
    }

    int getTentCount(){
        return tentLocations.size();
    }

    int getTreeCount(){
        return treeLocations.size();
    }

}
