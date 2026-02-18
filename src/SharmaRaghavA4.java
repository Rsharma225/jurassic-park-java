/*
 * COMP 1010 SECTION [A01]
 * INSTRUCTOR: [Raquel]
 * STUDENT NUMBER: [007984679]
 * STUDENT NAME: [Raghav Shamra]
 * ACTIVITY: [Asignment 4]
 * DATE: [2025-06-02]
 */

 /*
 * ================== TEST CASE SCENARIOS ==================
 *
 *TEST CASE 1: Simple moves
 * Input: "drr"
 * Description:
 * - Player moves down, then right twice.
 * - Tests basic movement and park rendering.
 * Expected Outcome:
 * - Player position updates, steps increase, no zap or item collected.
 *
 * TEST CASE 2: Zap dinosaur after moving
 * Input: "rzz"
 * Description:
 * - Player moves right (lastMove = 'r') and then zaps twice.
 * - Tests zap logic in the direction of last movement.
 * Expected Outcome:
 * - If a dinosaur is in the path, it is stunned. DinoZapped = true.
 *
 * TEST CASE 3: (Key pickup and hatch exits)
 * Input: "ddrrzllu"
 * Description:
 * - Complex movement. If key and hatch are located near, this input should allow pickup and escape.
 * Expected Outcome:
 * - hasKey becomes true, and game ends when hatch is reached with key.
 *
 * TEST CASE 4: Invalid input handling
 * Input: "abc123"
 * Description:
 * - Invalid characters entered.
 * Expected Outcome:
 * - Input rejected with an error message. No steps performed.
 */



import java.util.Scanner;
import java.util.Random;

public class SharmaRaghavA4 {

    // Constants for the characters in the game
    static final char PLAYER = '@';      // Player character
    static final char DINO = 'X';        // Dinosaur character
    static final char KEY = '$';         // Key character
    static final char HATCH = '#';       // Hatch character
    static final char WALL_H = '-';      // Horizontal wall character
    static final char WALL_V = '|';      // Vertical wall character
    static final char EMPTY = ' ';       // Empty space character

    // Park dimensions 
    static int width ;                   // Width of the park
    static int height ;                  // Height of the park

    // Entity coordinates 
    static int playerX, playerY;
    static int dino1X, dino1Y, dino2X, dino2Y;
    static int keyX, keyY;
    static int hatchX, hatchY;

    // Game state variables
    static boolean hasKey = false;
    static boolean dino1Zapped = false;
    static boolean dino2Zapped = false;

    // Input and step counters
    static int steps = 0;
    static int inputs = 0;
    static char lastMove = ' ';

    // Scanner and Random instances for input and random number generation
    static Scanner sc = new Scanner(System.in);
    static Random rand = new Random();

    // Main method to start the Jurassic game.
    // It initializes the game, prints instructions, and handles user input.
    public static void main(String[] args) {
        System.out.println("Starting Jurassic Game - ROAR!");
        System.out.println("===========================================");
        System.out.println("        WELCOME TO JURASSIC PARK!     ");
        System.out.println("============================================");
        System.out.println("You are trapped in a Jurassic park with two dinosaurs!");
        System.out.println("Your goal is to find the key and escape through the hatch!");
        System.out.println("You can move using the following commands:");
        System.out.println("u - up, d - down, l - left, r - right, z - zap, ? - random teleport");
        System.out.println("You can zap the dinosaurs in the direction of your last move!");
        System.out.println("If you zap a dinosaur, it will be stunned for the next move.");
        System.out.println("Your goal is to pick up the key and escape through the hatch!");
        System.out.println("Let the hunt begin!  ROAR!\n");
        System.out.println("Press <enter> to start the game...");
        sc.nextLine();
        System.out.println("Initializing the game...");

        // Randomly initialize the game state
        initializeGame();

        // Start the game loop
        while (true) {
            printPark();
            System.out.println("Step " + steps + ":");
            System.out.print("What are your next steps? [udlrz?]+: ");
            String command = sc.nextLine().toLowerCase();

            if (!command.matches("[udlrz?]+")) {
                System.out.println("Input is invalid! Should be in the format: [udlrz?]+");
                continue;
            }

            // Execute each move in the command string
            for (int i = 0; i < command.length(); i++) {
                char move = command.charAt(i);
                steps++;
                inputs++;
                moveDinosaurs();         // Move the dinosaurs randomly
                executeMove(move);       // Execute the player's move
                if (checkEnd()) return;  // Check if the game has ended
                                         // Print the park after each move
                if (i < command.length() - 1) {
                    System.out.println("Queued steps remaining: " + command.substring(i + 1));
                    promptEnterKey();
                }
            }
        }
    }

    // Initializes the game by randomly placing the player, dinosaurs, key, and hatch
    // Making sure that no two entities occupy the same position.
    public static void initializeGame() {
        width = rand.nextInt(5) + 5; // 5 to 9
        height = rand.nextInt(5) + 5; // 5 to 9

        do {
            playerX = rand.nextInt(width);
            playerY = rand.nextInt(height);
            dino1X = rand.nextInt(width);
            dino1Y = rand.nextInt(height);
            dino2X = rand.nextInt(width);
            dino2Y = rand.nextInt(height);
            keyX = rand.nextInt(width);
            keyY = rand.nextInt(height);
            hatchX = rand.nextInt(width);
            hatchY = rand.nextInt(height);
        } while ((playerX == dino1X && playerY == dino1Y) ||
                 (playerX == dino2X && playerY == dino2Y) ||
                 (playerX == keyX && playerY == keyY) ||
                 (keyX == hatchX && keyY == hatchY));
    }

    // Prints the current state of the park, including the player, dinosaurs, key, and hatch
    public static void printPark() {
        for (int y = -1; y <= height; y++) {
            for (int x = -1; x <= width; x++) {
                if (y == -1 || y == height) {
                    System.out.print(WALL_H);
                } else if (x == -1 || x == width) {
                    System.out.print(WALL_V);
                } else if (x == playerX && y == playerY) {
                    System.out.print(PLAYER);
                } else if ((x == dino1X && y == dino1Y && !dino1Zapped) ||
                           (x == dino2X && y == dino2Y && !dino2Zapped)) {
                    System.out.print(DINO);
                } else if (x == keyX && y == keyY && !hasKey) {
                    System.out.print(KEY);
                } else if (x == hatchX && y == hatchY) {
                    System.out.print(HATCH);
                } else {
                    System.out.print(EMPTY);
                }
            }
            System.out.println();
        }
    }

    // Moves the dinosaurs randomly
    public static void moveDinosaurs() {
        if (!dino1Zapped) {
            int dx = rand.nextInt(3) - 1;
            int dy = rand.nextInt(3) - 1;
            if (isInBounds(dino1X + dx, dino1Y + dy)) {
                dino1X += dx;
                dino1Y += dy;
            }
        }
        if (!dino2Zapped) {
            int dx = rand.nextInt(3) - 1;
            int dy = rand.nextInt(3) - 1;
            if (isInBounds(dino2X + dx, dino2Y + dy)) {
                dino2X += dx;
                dino2Y += dy;
            }
        }
    }

    // Executes the player's move based on the input character
    public static void executeMove(char move) {
        if ("udlr".indexOf(move) >= 0) {
            lastMove = move;
            int dx = 0, dy = 0;
            if (move == 'u') dy = -1;
            if (move == 'd') dy = 1;
            if (move == 'l') dx = -1;
            if (move == 'r') dx = 1;
            if (isInBounds(playerX + dx, playerY + dy)) {
                playerX += dx;
                playerY += dy;
            }
            }
         else if (move == '?') {   // Randomly teleport the player to a new position
            playerX = rand.nextInt(width);
            playerY = rand.nextInt(height);}
         else if (move == 'z') {   // Zap the dinosaurs in the direction of the last move
            if (lastMove == 'u') zap(playerX, playerY - 1, 0, -1);
            if (lastMove == 'd') zap(playerX, playerY + 1, 0, 1);
            if (lastMove == 'l') zap(playerX - 1, playerY, -1, 0);
            if (lastMove == 'r') zap(playerX + 1, playerY, 1, 0);
        }
    }

    // Zaps the dinosaurs in the specified direction
    public static void zap(int x, int y, int dx, int dy) {
        for (int i = 0; i < 3; i++) {
            if (x == dino1X && y == dino1Y) dino1Zapped = true;
            if (x == dino2X && y == dino2Y) dino2Zapped = true;
            x += dx;
            y += dy;
        }
    }

    // Checks if the game has ended, either by the player being killed by a dinosaur or successfully escaping
    public static boolean checkEnd() {

        // Check if the player is killed by a dinosaur
        if ((playerX == dino1X && playerY == dino1Y && !dino1Zapped) ||
            (playerX == dino2X && playerY == dino2Y && !dino2Zapped)) {
            System.out.println("");
            System.out.println("``````````````````````````````````````````");
            System.out.println("              MISSION FAILED!");
            System.out.println("``````````````````````````````````````````");
            System.out.println(" x_x  Oh no!! You were killed by the dinosaurs after " + inputs + " inputs and " + steps + " steps!");
            System.out.println("");
            return true;
        }

        // Pick up the key if the player is on its position
        if (playerX == keyX && playerY == keyY) {
            hasKey = true;
            System.out.println("~~~~~~~~~~~~~~~~~~~~");
            System.out.println("Picked up the key!");
            System.out.println("~~~~~~~~~~~~~~~~~~~~");
        }

        // Check if the player has reached the hatch with the key
        if (playerX == hatchX && playerY == hatchY && hasKey) {
            System.out.println("");
            System.out.println("************************************************");
            System.out.println("              MISSION ACCOMPLISHED!");
            System.out.println("************************************************");
            System.out.println(" ^_^  Congratulations!! You escaped the Jurassic park in " + inputs + " inputs and " + steps + " steps!");
            System.out.println("");
            return true;
        }
        return false;
    }

    // Checks if the given coordinates are within the boundaries
    public static boolean isInBounds(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    // Waits for the user to press the enter key before continuing
    public static void promptEnterKey() {
        System.out.println("Press <enter> to continue...");
        sc.nextLine();
    }
}

// NOTE: I am not calling sc.close() because closing scanner was causing issues
// with the Scanner if it is used again later in the program.
// In this case, we are using it only once in the main method, so it is safe to leave it open.
