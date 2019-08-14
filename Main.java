import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //-1 is tree
        //0 is empty
        //1 is grass
        //2 is tent
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
                gameBoard = new Board(new int[0][0], new int[0], new int[0]);
                break;
        }
        displayBoard(gameBoard);
        while(!BoardManager.checkWin(gameBoard)) {
            choice = input.nextInt();
            choice2 = input.nextInt();
            gameBoard.cycleTile(choice, choice2);
            displayBoard(gameBoard);
            System.out.println(BoardManager.checkWin(gameBoard));
        }
        System.out.println("You win!");
    }


    //Text-based display, WIP
    public static void displayBoard(Board board) {

        String spacing = getBoardSpacing(board);

        System.out.print(" " + spacing);
        for (int i = 0; i < board.getBoardSize(); i++) {
            System.out.print(board.getColumnIndex(i));
            System.out.print(spacing);
        }
        System.out.println();
        for (int i = 0; i < board.getBoardSize(); i++) {
            System.out.print(board.getRowIndex(i));
            System.out.print(spacing);
            for (int j = 0; j < board.getBoardSize(); j++) {
                switch (board.getTile(i, j)){
                    case -1:
                        System.out.print("T");
                        break;
                    case 0:
                        System.out.print("E");
                        break;
                    case 1:
                        System.out.print("G");
                        break;
                    case 2:
                        System.out.print("A");
                        break;
                    default:
                        System.out.print(board.getTile(i, j));
                }
                System.out.print(spacing);
            }
            System.out.println();
        }
    }

    private static String getBoardSpacing(Board gameBoard){
        int maxSoFar = 0;
        for (int i = 0; i < gameBoard.getBoardSize(); i++){
            String length = String.valueOf(gameBoard.getRowIndex(i));
            if (length.length() > maxSoFar){
                maxSoFar = length.length();
            }
        }

        String spaces = "";
        for (int i = 0; i < maxSoFar; i++) {
            spaces += " ";
        }
        return spaces;
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
