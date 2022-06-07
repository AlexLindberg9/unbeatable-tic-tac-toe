import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

/**
 * Class that represents a CPU Tic Tac Toe player which takes a defensive
 * approach in order to never lose.
 *
 * @author alexlindberg
 *
 */
public class UnbeatableTicTac {

    // 2D array representing current game board
    private char[][] gameBoard = { {}, };

    // character representing player symbol
    private char playerSym;

    // character representing cpu symbol
    private char cpuSym;

    // set representing open spots on current board
    private Set<Integer> openSpots = new HashSet<Integer>();

    // constructor to set up tic tac toe game
    public UnbeatableTicTac(char playerSym, char cpuSym) {
        this.playerSym = playerSym;
        this.cpuSym = cpuSym;
        this.gameBoard = new char[][] { { ' ', '|', ' ', '|', ' ' },
                { '-', '+', '-', '+', '-' }, { ' ', '|', ' ', '|', ' ' },
                { '-', '+', '-', '+', '-' }, { ' ', '|', ' ', '|', ' ' } };
        for (int i = 1; i < 10; i++) {
            this.openSpots.add(i);
        }
    }

    /*
     * Method that prints board to the output screen
     */
    public void printBoard() {
        for (int i = 0; i < this.gameBoard[0].length; i++) {
            for (int j = 0; j < this.gameBoard.length; j++) {
                System.out.print(this.gameBoard[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    /*
     * Method that copies a given board to the current board
     *
     * @param orig the board to be pasted onto
     *
     * @param toCopy the board to be copied
     */
    public static char[][] copyBoard(char[][] orig, char[][] toCopy) {
        for (int i = 0; i < toCopy[0].length; i++) {
            for (int j = 0; j < toCopy.length; j++) {
                orig[i][j] = toCopy[i][j];
            }
        }
        return orig;
    }

    /*
     * Method that indicated whether user is taking a corner strategy
     *
     * @return int telling cpu where to prioritize its defense
     */
    public int cornerStratDef() {
        if (this.openSpots.size() == 6 && this.gameBoard[0][0] == this.playerSym
                && this.gameBoard[4][4] == this.playerSym) {
            return 8;
        }
        if (this.openSpots.size() == 6 && this.gameBoard[0][4] == this.playerSym
                && this.gameBoard[4][0] == this.playerSym) {
            return 8;
        }
        return -1;
    }

    /*
     * Method that determines whether the user or cpu is one space away from a
     * win
     *
     * @sym the symbol that we want to determine this for
     *
     * @return the space where the play would occur
     */
    public int winnablePlay(char sym) {
        // save original board
        char[][] origBoard = new char[5][5];
        origBoard = copyBoard(origBoard, this.gameBoard);
        // save original spaces
        Set<Integer> origSpaces = new HashSet<Integer>(this.openSpots);
        // test all spaces to see if they lead to a player win
        while (this.openSpots.size() > 0) {
            this.gameBoard = copyBoard(this.gameBoard, origBoard);
            int toTry = this.removeAny();
            this.makeMove(toTry, sym);
            if (this.gameWon(sym)) {
                this.gameBoard = copyBoard(this.gameBoard, origBoard);
                this.openSpots = new HashSet<Integer>(origSpaces);
                return toTry;
            }

        }
        // restore board and spaces
        this.gameBoard = copyBoard(this.gameBoard, origBoard);
        this.openSpots = new HashSet<Integer>(origSpaces);
        return -1;
    }

    /*
     * Method that performs a move on the board
     *
     * @param pos the position to be played in
     *
     * @param sym the symbol to be played
     *
     * @return a String message to be displayed to the output
     */
    public String makeMove(int pos, char sym) {
        boolean result = false;
        switch (pos) {
            case (1):
                if (this.openSpots.contains(1)) {
                    this.gameBoard[0][0] = sym;
                    this.openSpots.remove(1);
                    result = true;

                } else {
                    result = false;

                }
                break;
            case (2):
                if (this.openSpots.contains(2)) {
                    this.gameBoard[0][2] = sym;
                    this.openSpots.remove(2);
                    result = true;
                    break;
                } else {
                    result = false;
                    break;
                }
            case (3):
                if (this.openSpots.contains(3)) {
                    this.gameBoard[0][4] = sym;
                    this.openSpots.remove(3);
                    result = true;
                    break;
                } else {
                    result = false;
                    break;
                }
            case (4):
                if (this.openSpots.contains(4)) {
                    this.gameBoard[2][0] = sym;
                    this.openSpots.remove(4);
                    result = true;
                    break;
                } else {
                    result = false;
                    break;
                }
            case (5):
                if (this.openSpots.contains(5)) {
                    this.gameBoard[2][2] = sym;
                    this.openSpots.remove(5);
                    result = true;
                    break;
                } else {
                    result = false;
                    break;
                }
            case (6):
                if (this.openSpots.contains(6)) {
                    this.gameBoard[2][4] = sym;
                    this.openSpots.remove(6);
                    result = true;
                    break;
                } else {
                    result = false;
                    break;
                }
            case (7):
                if (this.openSpots.contains(7)) {
                    this.gameBoard[4][0] = sym;
                    this.openSpots.remove(7);
                    result = true;
                    break;
                } else {
                    result = false;
                    break;
                }
            case (8):
                if (this.openSpots.contains(8)) {
                    this.gameBoard[4][2] = sym;
                    this.openSpots.remove(8);
                    result = true;
                    break;
                } else {
                    result = false;
                    break;
                }
            case (9):
                if (this.openSpots.contains(9)) {
                    this.gameBoard[4][4] = sym;
                    this.openSpots.remove(9);
                    result = true;
                    break;
                } else {
                    result = false;
                    break;
                }
            default:
                result = false;
                break;

        }
        if (result) {
            return ("Move performed!");
        } else {
            return ("Sorry can't go here. Try again!");
        }
    }

    /*
     * Method that determines if the game has been won
     *
     * @param sym the symbol is analyzed
     *
     * @return true if game won for sym, false if game not won
     */
    public boolean gameWon(char sym) {

        //check horizontal
        if (this.gameBoard[0][0] == sym && this.gameBoard[0][2] == sym
                && this.gameBoard[0][4] == sym) {
            return true;
        }
        if (this.gameBoard[2][0] == sym && this.gameBoard[2][2] == sym
                && this.gameBoard[2][4] == sym) {
            return true;
        }
        if (this.gameBoard[4][0] == sym && this.gameBoard[4][2] == sym
                && this.gameBoard[4][4] == sym) {
            return true;
        }

        //check vertical
        if (this.gameBoard[0][0] == sym && this.gameBoard[2][0] == sym
                && this.gameBoard[4][0] == sym) {
            return true;
        }
        if (this.gameBoard[0][2] == sym && this.gameBoard[2][2] == sym
                && this.gameBoard[4][2] == sym) {
            return true;
        }
        if (this.gameBoard[0][4] == sym && this.gameBoard[2][4] == sym
                && this.gameBoard[4][4] == sym) {
            return true;
        }

        //check diagonal
        if (this.gameBoard[0][0] == sym && this.gameBoard[2][2] == sym
                && this.gameBoard[4][4] == sym) {
            return true;
        }
        if (this.gameBoard[0][4] == sym && this.gameBoard[2][2] == sym
                && this.gameBoard[4][0] == sym) {
            return true;
        }

        return false;
    }

    /*
     * Method that returns a random space from openSpots set
     *
     * @return integer representing this spot
     */
    public int removeAny() {
        Random ran = new Random();
        int toPlay = ran.nextInt(this.openSpots.size()) + 1;
        int idx = 1;
        for (Integer i : this.openSpots) {
            if (idx == toPlay) {
                return i;
            }
            idx++;
        }
        // never runs
        return -1;

    }

    /*
     * Method that simulates 10000 trials for playing at a certain space on the
     * board
     *
     * @param pos the position we are simulating
     *
     * @return percentage representing percen of time cpu wins when playing here
     * first
     */
    public double winProb(int pos) {
        // save original before testing win prob
        char[][] origBoard = new char[5][5];
        origBoard = copyBoard(origBoard, this.gameBoard);

        // save original open spaces
        Set<Integer> origSpaces = new HashSet<Integer>(this.openSpots);

        // make first move
        this.makeMove(pos, this.cpuSym);

        if (this.gameWon(this.cpuSym)) {
            this.gameBoard = copyBoard(this.gameBoard, origBoard);
            this.openSpots = new HashSet<Integer>(origSpaces);
            return pos;
        }

        // save board and spaces after first move
        char[][] preSimBoard = new char[5][5];
        Set<Integer> preSimSpots = new HashSet<Integer>(this.openSpots);
        preSimBoard = copyBoard(preSimBoard, this.gameBoard);

        // simulate random selections between player and cpu
        // calculate win percentage on 10000 trials
        double wins = 0;
        for (int i = 0; i < 100000; i++) {
            this.gameBoard = copyBoard(this.gameBoard, preSimBoard);
            this.openSpots = new HashSet<Integer>(preSimSpots);
            boolean gameOver = false;
            int turn = 0;
            while (gameOver == false && this.openSpots.size() > 0) {
                // player turn
                if (turn % 2 == 0) {
                    this.makeMove(this.removeAny(), this.playerSym);
                    turn++;
                    if (this.gameWon(this.playerSym)) {
                        wins -= 5;
                        gameOver = true;
                    }
                }
                // cpu turn
                else {
                    this.makeMove(this.removeAny(), this.cpuSym);
                    turn++;
                    if (this.gameWon(this.cpuSym)) {
                        wins++;
                        gameOver = true;

                    }
                }
            }

        }
        // restore board and spots to original
        this.gameBoard = copyBoard(this.gameBoard, origBoard);
        this.openSpots = new HashSet<Integer>(origSpaces);
        return (wins / 100000.0);

    }

    /*
     * Method that tests winProb on all available spaces for the cpu and finds
     * the spot with the highest expected win percentage
     *
     * @return position with best win prob
     */
    public int findBestPos() {
        if (this.cornerStratDef() > 0) {
            return this.cornerStratDef();
        }
        double maxProb = -100.0;
        int spot = -1;
        List<Integer> spots = new ArrayList<Integer>(this.openSpots);
        for (int i = 0; i < spots.size(); i++) {
            double currProb = this.winProb(spots.get(i));
            if (currProb > maxProb) {
                maxProb = currProb;
                spot = spots.get(i);
            }
        }
        return spot;
    }

    /*
     * Main method that takes the user through a game of TicTacToe
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Which character do you want to use?\n");
        char userSym = sc.next().charAt(0);
        System.out.println("Which character do you want the CPU to use?");
        char cpuSym = sc.next().charAt(0);
        UnbeatableTicTac game = new UnbeatableTicTac(userSym, cpuSym);
        game.printBoard();
        String error = "Sorry can't go here. Try again!";
        boolean gameOver = false;
        while (gameOver == false && game.openSpots.size() != 0) {
            String message = "Sorry can't go here. Try again!";
            while (message.equals(error)) {
                System.out.println("Where would you like to play?");
                int userPlay = sc.nextInt();
                message = game.makeMove(userPlay, game.playerSym);
                System.out.println(message);
            }
            game.printBoard();
            if (game.gameWon(game.playerSym)) {
                System.out.println("You win!");
                gameOver = true;
                break;
            }
            int cpuPlay = game.findBestPos();
            game.makeMove(cpuPlay, game.cpuSym);
            game.printBoard();
            if (game.gameWon(game.cpuSym)) {
                System.out.println("CPU wins!");
                gameOver = true;
                break;
            }

        }
        if (game.gameWon(game.cpuSym) == false
                && game.gameWon(game.playerSym) == false) {
            System.out.println("Draw!");
        }

    }
}
