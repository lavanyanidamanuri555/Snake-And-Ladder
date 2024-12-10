
import java.util.*;

public class SnakeAndLadderGame {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of players: ");
        int numberOfPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Queue<Player> allPlayers = new LinkedList<>();
        Map<String, Integer> playersCurrentPosition = new HashMap<>();

        for (int i = 1; i <= numberOfPlayers; i++) {
            System.out.print("Enter name of player " + i + ": ");
            String playerName = scanner.nextLine();
            Player player = new Player(playerName, i);
            allPlayers.offer(player);
            playersCurrentPosition.put(playerName, 0);
        }

        // Ask the user for the number of dice
        System.out.print("Enter the number of dice: ");
        int numberOfDice = scanner.nextInt();

        Dice dice = new Dice(numberOfDice);

        // Randomize number of snakes and ladders
        Random random = new Random();
        int numberOfSnakes = random.nextInt(5) + 1; // Between 1 and 5
        int numberOfLadders = random.nextInt(5) + 1; // Between 1 and 5

        // Generate random snakes and ladders
        List<Jumper> snakes = new ArrayList<>();
        List<Jumper> ladders = new ArrayList<>();
        Set<Integer> usedPositions = new HashSet<>();

        // Generate snakes
        for (int i = 0; i < numberOfSnakes; i++) {
            int start, end;
            do {
                start = random.nextInt(91) + 10; // Ensure start is between 10 and 100
                end = random.nextInt(start - 1) + 1; // Ensure end is less than start
            } while (usedPositions.contains(start) || usedPositions.contains(end));
            usedPositions.add(start);
            usedPositions.add(end);
            snakes.add(new Jumper(start, end));
        }

        // Generate ladders
        for (int i = 0; i < numberOfLadders; i++) {
            int start, end;
            do {
                start = random.nextInt(90) + 1; // Ensure start is between 1 and 90
                end = random.nextInt(100 - start) + start + 1; // Ensure end is greater than start
            } while (usedPositions.contains(start) || usedPositions.contains(end));
            usedPositions.add(start);
            usedPositions.add(end);
            ladders.add(new Jumper(start, end));
        }

        GameBoard gb = new GameBoard(dice, allPlayers, snakes, ladders, playersCurrentPosition, 100);
        gb.startGame();
    }
}
