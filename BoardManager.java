import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BoardManager {


    //Solve board method walk through:
    //1: Flip all empty tiles that do not directly neighbor a tree to grass tiles
    //2:


    static Board generateBoard(int size) {
        int[][] newBoard = new int[size][size];
        int[] newColumnCounts = new int[size];
        int[] newRowCounts = new int[size];

        //Generate stuff
        //Place a -1, 2 combo
        //Make sure it's valid

        Random myRand = new Random();
        CoordinatePair tempTree;

        for (int i = 0; i < ((size * size)/4); i++) {

            //Pick a random spot on the board
            tempTree = new CoordinatePair(myRand.nextInt(size), myRand.nextInt(size));

            //If it's an empty spot we can move forward
            if (newBoard[tempTree.getX()][tempTree.getY()] == 0) {
                //Return a random valid neighbor, if possible
                CoordinatePair tempTent = getRandomValidTentAdjacent(new Board(newBoard, null, null), tempTree);

                //If a valid neighbor is found, place the tree and the tent on the board
                if (tempTent != null) {
                    newBoard[tempTree.getX()][tempTree.getY()] = -1;
                    newBoard[tempTent.getX()][tempTent.getY()] = 2;
                }
            }
        }

        //Done placing trees and tents
        //Count 2's in each row and column
        //Remove all the 2's at the end

        int tempRowCount;

        //Counting row
        for (int r = 0; r < size; r++) {
            tempRowCount = 0;

            //Find a tent, count it
            for (int c = 0; c < size; c++) {
                if (newBoard[r][c] == 2) {
                    tempRowCount++;
                }
            }
            newRowCounts[r] = tempRowCount;
        }

        int tempColumnCount;

        //Counting for column
        for (int c = 0; c < size; c++) {
            tempColumnCount = 0;

            //Find a tent, count it, and remove it from the board
            for (int r = 0; r < size; r++) {
                if (newBoard[r][c] == 2) {
                    tempColumnCount++;
                    newBoard[r][c] = 0;
                }
            }
            newColumnCounts[c] = tempColumnCount;
        }

        return new Board(newBoard, newColumnCounts, newRowCounts);
    }

    //You win if the following conditions are met:
    //There are no empty tiles
    //Each tree has a unique tent
    //No tent is near another tent
    //Each row and column has the right number of tents
    static boolean checkWin(Board gameBoard) {
        int tempCount = 0;
        int size = gameBoard.getBoardSize();

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                CoordinatePair location = new CoordinatePair(i, j);
                int locationValue = gameBoard.getTile(location);

                //If there are empty tiles, then we are not in a solution state yet
                if (locationValue == 0) {
                    return false;
                }
                //If there is a tent nearby another tent, then we are not in a solution new CoordinatePair(i, j)
                // state yet
                if (locationValue == 2 && hasNeighborOfType(gameBoard, location, 2)) {
                    return false;
                }
                //If there is no tent near a tree, then we are not in a solution state yet
                if (locationValue == -1 && !hasAdjacentOfType(gameBoard, location, 2)) {
                    return false;
                }
            }
        }

        //Checking row tent count matches
        for (int i = 0; i < size; i++) {
            tempCount = 0;
            for (int j = 0; j < size; j++) {
                if (gameBoard.getTile(i, j) == 2) {
                    tempCount++;
                }
            }
            if (tempCount != gameBoard.getRowIndex(i)) {
                return false;
            }
        }

        //Checking column tent count matches
        for (int i = 0; i < size; i++) {
            tempCount = 0;
            for (int j = 0; j < size; j++) {

                if (gameBoard.getTile(j, i) == 2) {
                    tempCount++;
                }
            }
            if (tempCount != gameBoard.getColumnIndex(i)) {
                return false;
            }
        }
        //At this point we know there are an equal number of trees and tents and that
        // each column and row has the correct number
        //We know that each tree has at least 1 tent in at least 1 of the 4 squares
        //We know that each tent does not have another tent near by itself
        //We know that there are no empty tiles, everything is grass or tents

        //We do not know for certain that each tree has a unique tent, but
        //testing may uncover false positives

        //If all above tests are valid then the board is complete
        return true;
    }

    /**
     * If a tent can be generated adjacent to the given location, then a random valid adjacent coordinate is returned
     * @return Random location where a tent can be correctly placed
     */
    private static CoordinatePair getRandomValidTentAdjacent(Board gameBoard, CoordinatePair location) {

        List<CoordinatePair> validTentPlacements = new ArrayList<>();

        //Get all existing adjacent tiles that are empty
        List<CoordinatePair> adjacent = filterCoordinates(gameBoard, getAdjacent(gameBoard, location), 0);

        if (adjacent.size() == 0) {
            return null;
        }

        //If the given coordinate doesn't have a tent neighboring it, then it's a valid location
        for (CoordinatePair c: adjacent) {
            if (!hasNeighborOfType(gameBoard, c, 2)){
                validTentPlacements.add(c);
            }
        }

        if (validTentPlacements.size() == 0) {
            return null;
        }

        return validTentPlacements.get(new Random().nextInt(validTentPlacements.size()));
    }

    /**
     * Returns true if there is at least one neighboring location of the given type
     */
    private static boolean hasNeighborOfType(Board gameBoard, CoordinatePair location, int type) {
        return (filterCoordinates(gameBoard, getNeighbors(gameBoard, location), type).size() != 0);
    }
    /**
     * Returns true if there is at least one adjacent location of the given type
     * @param type -1 for tree, 0 for empty, 1 for grass, 2 for tent
     * @return True if at least one adjacent location of the given type, false otherwise
     */
    private static boolean hasAdjacentOfType(Board gameBoard, CoordinatePair location, int type) {
        return (filterCoordinates(gameBoard, getAdjacent(gameBoard, location), type).size() != 0);
    }

    /**
     * Returns a list of valid adjacent locations surrounding the given location.
     * @param location Location to get adjacent tiles from
     * @return List of adjacent coordinates
     */
    private static List<CoordinatePair> getAdjacent(Board gameBoard, CoordinatePair location){
        List<CoordinatePair> adjacent = new ArrayList<>();
        int x = location.getX();
        int y = location.getY();

        adjacent.add(new CoordinatePair(x, y - 1)); // W - Left
        adjacent.add(new CoordinatePair(x - 1, y)); // N - Top
        adjacent.add(new CoordinatePair(x, y + 1)); // E - Right
        adjacent.add(new CoordinatePair(x + 1, y)); // S - Bottom

        return filterCoordinates(gameBoard, adjacent);
    }

    /**
     * Returns a list of all valid neighbors of the given location.
     * @param location Location to get neighbors of
     * @return List of neighboring coordinates
     */
    private static List<CoordinatePair> getNeighbors(Board gameBoard, CoordinatePair location){
        List<CoordinatePair> neighbors = new ArrayList<>();
        int x = location.getX();
        int y = location.getY();

        neighbors.add(new CoordinatePair(x, y - 1));        // W - Left
        neighbors.add(new CoordinatePair(x - 1, y - 1));  // NW - Top Left
        neighbors.add(new CoordinatePair(x - 1, y));        // N - Top
        neighbors.add(new CoordinatePair(x - 1, y + 1));  // NE - Top Right
        neighbors.add(new CoordinatePair(x, y + 1));        // E - Right
        neighbors.add(new CoordinatePair(x + 1, y + 1));  // SE - Bottom Right
        neighbors.add(new CoordinatePair(x + 1, y));        // S - Bottom
        neighbors.add(new CoordinatePair(x + 1, y - 1));  // SW - Bottom Left

        return filterCoordinates(gameBoard, neighbors);
    }

    /**
     * Returns true if the given coordinates are within bounds of the board
     * @param gameBoard Board to search
     * @param location Location to check
     * @return true if within bounds, false if out of bounds
     */
    private static boolean validLocation(Board gameBoard, CoordinatePair location) {
        int x = location.getX();
        int y = location.getY();
        return (x >= 0 && x < gameBoard.getBoardSize() && y >= 0 && y < gameBoard.getBoardSize());
    }

    /**
     * Returns true if the given location is within bounds and of the the given type
     * @param gameBoard Board to search
     * @param location Location to check
     * @param type -1 for tree, 0 for empty, 1 for grass, 2 for tent
     * @return true if within bounds AND of given type, false if out of bounds OR not of given type
     */
    private static boolean validLocation(Board gameBoard, CoordinatePair location, int type){
        int x = location.getX();
        int y = location.getY();
        return (validLocation(gameBoard, location) && gameBoard.getTile(x, y) == type);
    }

    /**
     * Returns a list of locations that have had all invalid locations removed, may be empty.
     */
    private static List<CoordinatePair> filterCoordinates(Board gameBoard, List<CoordinatePair> list){
        list.removeIf(c -> !validLocation(gameBoard, c));
        return list;
    }

    /**
     * Returns a list of locations that have had all locations that are not #type removed, may be empty.
     * @param type -1 for tree, 0 for empty, 1 for grass, 2 for tent
     */
    private static List<CoordinatePair> filterCoordinates(Board gameBoard, List<CoordinatePair> list, int type){
        list.removeIf(c -> gameBoard.getTile(c.getX(), c.getY()) != type);
        return list;
    }
}