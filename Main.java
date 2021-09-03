import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static int[][] winningSquares = new int[4][2];
	public static int boardSize = 7;
	public static String[][] board = new String[boardSize][boardSize];
	public static boolean gameEnd = false;
	
	public static void checkWin(String[] line) {
		if (checkAllEquals(line)) {	// if conditions are satisfied for a win 
			for (int j1 = 1; j1 <= boardSize; j1++) {
				System.out.print("  " + j1 + " "); // numbers on top of the board 
			}
			System.out.println();
			
			// prints board but with emphasis on the 4 in a row
			for (int j1 = 0; j1 < boardSize; j1++) {
				for (int k1 = 0; k1 < boardSize; k1++) {
					System.out.print("+---");
				}
				System.out.println("+");
				
				for (int k1 = 0; k1 < boardSize; k1++) {
					int[] coordinate = {j1, k1};
					boolean isWinningSquare = false;
					for (int[] square : winningSquares) {
						if (Arrays.equals(coordinate, square)) {
							isWinningSquare = true;
						}
					}
					
					if (isWinningSquare) {
							System.out.print("|" + board[j1][k1].repeat(3));
					}
					else {
						System.out.print("| " + board[j1][k1] + " ");
					}
				}
				System.out.println("|");
			}
			for (int k1 = 0; k1 < boardSize; k1++) {
				System.out.print("+---");
			}
			System.out.println("+");
			
			gameEnd = true;
		}
	}
	
	public static boolean checkAllEquals(String[] strings) {
		for (String str : strings) {
			if (!str.equals(strings[0])) {
				return false;
			}
		}
		if (strings[0].equals(" ")) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public static void printBoard() {
		for (int j = 1; j <= boardSize; j++) {
			System.out.print("  " + j + " "); // numbers on top of the board 
		}
		System.out.println();
		
		for (int j = 0; j < boardSize; j++) {
			for (int k = 0; k < boardSize; k++) {
				System.out.print("+---");
			}
			System.out.println("+");
			for (int k = 0; k < boardSize; k++) {
				System.out.print("| " + board[j][k] + " ");
			}
			System.out.println("|");
		}
		for (int k1 = 0; k1 < boardSize; k1++) {
			System.out.print("+---");
		}
		System.out.println("+");
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		for (String[] row : board) {
			Arrays.fill(row, " ");
		}
		
		int[] columnHeights = new int[boardSize]; // number of counters in each column, initially set to 0 
		
		// introductory text 
		System.out.println("Welcome to Connect 4! This is a two-player game.");
		System.out.println("Enter the name of the first player: ");
		String player1 = scanner.nextLine();
		System.out.println("Enter the name of the second player: ");
		String player2 = scanner.nextLine();
		String[] playerNames = {player1, player2};
		
		printBoard();
		
		for (int i = 0; !gameEnd; i = (i+1)%2) { // playerNames[i] is the current player 	
			System.out.println("It is " + playerNames[i] + "'s turn.");
			System.out.println("Please enter an integer from 1 to " + boardSize + ": ");
			
			boolean isValidInput = false;
			
			while (!isValidInput) {
				try {
					int columnChoice = Integer.parseInt(scanner.nextLine());
					if (columnChoice < 1 || columnChoice > boardSize) {
						System.out.println("Invalid input! Please enter an integer from 1 to " + boardSize + ": ");
					} else if (columnHeights[columnChoice - 1] < boardSize) {
						columnHeights[columnChoice - 1]++;
						isValidInput = true;
						board[boardSize - columnHeights[columnChoice - 1]][columnChoice - 1] = new String[]{"X", "O"}[i];
					} else {
						System.out.println("That column is full! Choose a different column.");
					}
				} catch (Exception e) {
					System.out.println("Invalid input! Please enter an integer from 1 to " + boardSize + ": ");
				}
			} 
			
			printBoard();
			
			// code to check for 4 in a row 
			// 1) horizontal 
			for (int j = 0; j < boardSize; j++) {
				for (int k = 0; k < boardSize - 3; k++) {
					String[] lineOfFour = new String[4];
					for (int l = 0; l < 4; l++) {
						lineOfFour[l] = board[j][k + l];
						
						winningSquares[l] = new int[] {j, k + l}; // potentially winning squares, stored locally 
						if (l == 3) {
							checkWin(lineOfFour);
						}
					}
				}
			}
			
			// 2) vertical 
			if (!gameEnd) {
				for (int j = 0; j < boardSize - 3; j++) {
					for (int k = 0; k < boardSize; k++) {
						String[] lineOfFour = new String[4];
						for (int l = 0; l < 4; l++) {
							lineOfFour[l] = board[j + l][k];
							winningSquares[l] = new int[] {j + l, k}; // potentially winning squares, stored locally 
							if (l == 3) {
								checkWin(lineOfFour);
							}
						}
					}
				}
			}
			
			// 3) diagonal (top left to bottom right) 
			if (!gameEnd) {
				for (int j = 0; j < boardSize - 3; j++) {
					for (int k = 0; k < boardSize - 3; k++) {
						String[] lineOfFour = new String[4];
						for (int l = 0; l < 4; l++) {
							lineOfFour[l] = board[j + l][k + l];
							winningSquares[l] = new int[] {j + l, k + l}; // potentially winning squares, stored locally 
							if (l == 3) {
								checkWin(lineOfFour);
							}
						}
					}
				}
			}
			
			// 4) diagonal (bottom left to top right) 
			if (!gameEnd) {
				for (int j = 3; j < boardSize; j++) {
					for (int k = 0; k < boardSize - 3; k++) {
						String[] lineOfFour = new String[4];
						for (int l = 0; l < 4; l++) {
							lineOfFour[l] = board[j - l][k + l];
							winningSquares[l] = new int[] {j - l, k + l}; // potentially winning squares, stored locally 
							if (l == 3) {
								checkWin(lineOfFour);
							}
						}
					}
				}
			}
			
			if (gameEnd) {
				System.out.println("FOUR IN A ROW!");
				System.out.println(playerNames[i] + " wins!");
			} else { // check if the board is full 
				int sumHeights = 0;
				for (int height : columnHeights) {
					sumHeights += height;
				}
				if (sumHeights == boardSize * boardSize) {
					gameEnd = true;
					System.out.println("THE GAME IS A DRAW.");
					System.out.println("Thanks for playing!");
				}
			}
		}
		
		scanner.close();
	}
}