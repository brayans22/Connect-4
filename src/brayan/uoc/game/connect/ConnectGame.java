package brayan.uoc.game.connect;

public class ConnectGame {

    // CONSTANTS
    public static final int MAX_ROWS = 6;
    public static final int MAX_COLUMNS = 7;
    public static final int TOTAL_PIECES_TO_WIN = 4;
    public static final char EMPTY_SPACE = ' ';
    public static final String MESSAGE_INVALID_OR_FULL = "This column is invalid or full.";

    // PRIVATE METHODS
    private static void selectDropPieceCase(char[][] board, boolean isInserted, char color) {
        /*
            If a piece is inserted, print the board  with a winning message (if it found a winner).
            If it is not inserted, print a message with the status invalid or full.
        */
        if (!isInserted){
            System.out.println(MESSAGE_INVALID_OR_FULL);
        } else {
            printBoard(board);
            if (checkWin(board, color)) {
                System.out.println("Color " + color + " wins!");
            }
        }
    }

    private static boolean isInRange(int row, int col) {
        return row >= 0 && row < MAX_ROWS && col >= 0 && col < MAX_COLUMNS;
    }

    private static boolean checkToInsertPiece(char[][] board, int row, int column, char color) {
        boolean isInserted = false;

        if (board[row][column] == EMPTY_SPACE) {
            board[row][column] = color;
            isInserted = true;
        }
        return isInserted;
    }

    public static boolean hasFourthPieces(char[][] board, int row, int col, int rowIncrement,
                                          int colIncrement, char color) {
        int countPieces = 0, newRow = row, newCol = col;

        // Check fourth pieces in any direction base on rowIncrement and colIncrement.
        while (isInRange(newRow, newCol) && countPieces < TOTAL_PIECES_TO_WIN
                && board[newRow][newCol] == color) {
            countPieces++;
            newRow = row + (countPieces * rowIncrement);
            newCol = col + (countPieces * colIncrement);
        }
        return countPieces == TOTAL_PIECES_TO_WIN;
    }

    private static boolean checkWinnerInLine(char[][] board, char color, boolean isVertical) {
        int row = 0, col = 0;
        boolean hasWinner = false;

        while (row < MAX_ROWS && !hasWinner) {
            while (col < MAX_COLUMNS && !hasWinner) {
                if (isVertical) {
                    // Check fourth pieces in vertical line
                    hasWinner = hasFourthPieces(board, row, col, 1, 0, color);
                } else {
                    // Check fourth pieces in horizontal line
                    hasWinner = hasFourthPieces(board, row, col, 0, 1, color);
                }
                col++;
            }
            row++;
            col = 0;
        }
        return hasWinner;
    }

    // PUBLIC METHODS
    public static boolean isValidColumn(char[][] board, int column) {
        int row = MAX_ROWS - 1;
        boolean isValid = false;

        if (column >= 0 && column < MAX_COLUMNS) {
            // Check an empty space starting in the last row.
            while (row >= 0 && !isValid) {
                if (board[row][column] == EMPTY_SPACE) {
                    isValid = true;
                }
                row--;
            }
        }
        return isValid;
    }

    public static void printBoard(char[][] board) {
        for (int row = 0; row < MAX_ROWS; row++) {
            for (int col = 0; col < MAX_COLUMNS; col++) {
                System.out.print("|" + board[row][col]);
            }
            System.out.println("|");
        }
    }

    public static boolean checkWinHorizontal(char[][] board, char color) {
        return checkWinnerInLine(board, color, false);
    }

    public static boolean checkWinVertical(char[][] board, char color) {
        return checkWinnerInLine(board, color, true);
    }

    public static boolean checkWinDiagonal(char[][] board, char color) {
        int row = 0, col = 0;
        boolean hasWinner = false;

        while (row < MAX_ROWS && !hasWinner) {
            while (col < MAX_COLUMNS && !hasWinner) {
                // Check winner in both diagonals (main and secondary).
                if (hasFourthPieces(board, row, col, -1, 1, color) ||
                    hasFourthPieces( board, row, col, 1, 1, color)) {
                    hasWinner = true;
                }
                col++;
            }
            col = 0;
            row++;
        }
        return hasWinner;
    }

    public static boolean checkWin(char[][] board, char color) {
        return (checkWinDiagonal(board, color) || checkWinHorizontal(board, color)
                || checkWinVertical(board, color));
    }

    public static char[][] dropPiece(char[][] board, int column, char color) {
        int i = MAX_ROWS - 1;
        boolean isInserted = false;

        if (isValidColumn(board, column)) {
            // Check if it is possible insert a piece starting in the last row.
            while (i >= 0 && !isInserted) {
                isInserted = checkToInsertPiece(board, i, column, color);
                i--;
            }
        }
        selectDropPieceCase(board, isInserted, color);
        return board;
    }

}
