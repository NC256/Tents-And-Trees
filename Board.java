import java.util.ArrayList;
import java.util.List;

public class Board {

    //-1 is tree
    //0 is empty
    //1 is grass
    //2 is tent
    private int[][] gameBoard;
    private int[] columnCounts;
    private int[] rowCounts;
    private List<CoordinatePair> treeLocations;
    private List<CoordinatePair> tentLocations;

    Board(){
        gameBoard = new int[0][0];
        columnCounts = new int[0];
        rowCounts = new int[0];
        treeLocations = new ArrayList<>();
        tentLocations = new ArrayList<>();
    }

    Board(int[][] gameBoard, int[] columnCounts, int[] rowCounts){
        this.gameBoard = gameBoard;
        this.columnCounts = columnCounts;
        this.rowCounts = rowCounts;

        //CoordinatePair array that holds coordinates of each tree
        treeLocations = new ArrayList<>();

        //Puts the locations of each tree into the array
        for (int i = 0; i < gameBoard.length; i++) {
            for (int j = 0; j < gameBoard.length; j++) {
                if (gameBoard[i][j] == -1){
                    treeLocations.add(new CoordinatePair(i, j));
                }
            }
        }
        tentLocations = new ArrayList<>();
    }

    /**
     * Changes the tiles state when the player taps/clicks on it
     * @param x horizontal
     * @param y vertical
     */
    void cycleTile (int x, int y){

        switch (gameBoard[x][y]){
            case 0:
                gameBoard[x][y]++;
                break;
            case 1:
                gameBoard[x][y]++;
                tentLocations.add(new CoordinatePair(x,y));
                break;
            case 2:
                tentLocations.remove(new CoordinatePair(x,y));
                gameBoard[x][y] = 0;
                break;
        }
    }

    /**
     * @see Board#cycleTile(int, int)
     * @param tile coordinate of tile
     */
    void cycleTile (CoordinatePair tile){

        switch (gameBoard[tile.getX()][tile.getY()]){
            case 0:
                gameBoard[tile.getX()][tile.getY()]++;
                break;
            case 1:
                gameBoard[tile.getX()][tile.getY()]++;
                tentLocations.add(new CoordinatePair(tile.getX(),tile.getY()));
                break;
            case 2:
                tentLocations.remove(new CoordinatePair(tile.getX(),tile.getY()));
                gameBoard[tile.getX()][tile.getY()] = 0;
                break;
        }
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

    int[][] getBoard() {
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

    int getTile(int x, int y){
        return gameBoard[x][y];
    }

    int getTile(CoordinatePair location) {
        return gameBoard[location.getX()][location.getY()];
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
