import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //-1 is tree
        //0 is empty
        //1 is grass
        //2 is tent
        //TODO Investigate blank space at end of file save for columns/rows
        //TODO Wrap file creation/load functions perhaps?
        //TODO CLEAN spaced prints

        Board gameBoard;

        Scanner input = new Scanner(System.in);

        System.out.println("1. Select Board");
        System.out.println("2. Generate Random Board");
        int choice = input.nextInt();
        int choice2;

        switch (choice) {
            case 1:
                //TODO
                //Print board selections
                System.out.println("Which board do you want?");
                choice = input.nextInt();
                gameBoard = loadBoard(choice);
                break;
            case 2:
                System.out.println("Enter size");
                int size = input.nextInt();
                gameBoard = BoardManager.generateBoard(size);
                break;

            default:
                gameBoard = new Board(new Tile[0][0], new int[0], new int[0]);
                break;
        }
        textDisplay(gameBoard);

        // Rudimentary game loop
        while(!BoardManager.checkWin(gameBoard)) {
            choice = input.nextInt();
            choice2 = input.nextInt();
            gameBoard.cycleTile(choice, choice2);
            textDisplay(gameBoard);
        }
        System.out.println("You win!");

        //END OF MAIN
    }
    
    public static void textDisplay(Board board){
        int[] columnCounts = board.getColumnArray();
        int[] rowCounts = board.getRowArray();
        int maxColWidth = getLongestWidth(columnCounts);
        int maxRowWidth = getLongestWidth(rowCounts);
        int widest = Math.max(maxColWidth, maxRowWidth);

        // Move enough to the right to make space for the row counts
        for (int i = 0; i < widest; i++) {
            System.out.print(" ");
        }
        // One more to bridge the empty space
        System.out.print(" ");

        // Top row
        for (int i = 0; i < columnCounts.length; i++) {
            if(widest == 1){
                System.out.print(columnCounts[i]);
                if(i != columnCounts.length - 1){
                    System.out.print(" ");
                }
            }
            else{
                int currentItemWidth = String.valueOf(columnCounts[i]).length();
                int diff = widest - currentItemWidth;
                System.out.print(columnCounts[i]);
                for (int j = 0; j <= diff; j++) {
                    System.out.print(" ");
                }
            }
        }
        // Down a line
        System.out.println();

        // For the board
        for (int i = 0; i < board.getBoardSize(); i++) {

            // Print leftmost row counter
            if(widest == 1) {
                System.out.print(rowCounts[i]);
                System.out.print(" ");
            }
            else{
                int currentItemWidth = String.valueOf(rowCounts[i]).length();
                System.out.print(rowCounts[i]);
                int diff = widest - currentItemWidth;
                for (int j = 0; j <= diff; j++) {
                    System.out.print(" ");
                }
            }
            // Print tile itself
            for (int j = 0; j < board.getBoardSize(); j++) {
                System.out.print(printCharConversion(board.getTile(i, j)));
                int charWidth = 1;
                int diff = widest - charWidth;
                if(j != board.getBoardSize() -1 ){
                    System.out.print(" ");
                    for (int k = 0; k < diff; k++) {
                        System.out.print(" ");
                    }
                }
            }
            // Go down for next line
            System.out.println();
        }
        
    }

    private static int getLongestWidth(int[] inputArray){
        int longest = 0;
        for (int value : inputArray) {
            if (String.valueOf(value).length() > longest) {
                longest = String.valueOf(value).length();
            }
        }
        return longest;
    }

    private static char printCharConversion(Tile input){
        switch (input){
            case TREE:
                return 'T';
            case EMPTY:
                return 'E';
            case GRASS:
                return 'G';
            case TENT:
                return 'A';
            default:
                throw new IllegalArgumentException("Illegal Tile to display char conversion!");
        }
    }

    private static Board loadBoard(int selection) {
        switch (selection) {
            case 1:
                int[][] tempBoard2 = {{1, 1, -1, 1, 2},{1, 1, 2, 1, -1},{1, 1, 1, -1, 2},
                        {1, 2, -1, 1, 1},{1, 1, -1, 2, 1}};
                int[] tempHorizontal2 = {0, 1, 1, 1, 2};
                int[] tempVertical2 = {1, 1, 1, 1, 1};
                return new Board(tempBoard2, tempHorizontal2, tempVertical2);
        }
        return new Board(new int[0][0], new int[0], new int[0]);
    }

}
