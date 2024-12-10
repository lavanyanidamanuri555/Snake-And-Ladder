
import java.util.*;

public class GameBoard {
    private Dice dice;
    private Queue<Player> nextTurn;
    private List<Jumper> snakes;
    private List<Jumper> ladders;
    private Map<String, Integer> playersCurrentPosition;
    int boardSize;

    GameBoard(Dice dice, Queue<Player> nextTurn, List<Jumper> snakes, List<Jumper> ladders,
              Map<String, Integer> playersCurrentPosition, int boardSize) {
        this.dice = dice;
        this.nextTurn = nextTurn;
        this.snakes = snakes;
        this.ladders = ladders;
        this.playersCurrentPosition = playersCurrentPosition;
        this.boardSize = boardSize;
    }

    void startGame() {
        while (!nextTurn.isEmpty()) {
            Player player = nextTurn.poll();
            int currentPosition = playersCurrentPosition.get(player.getPlayerName());
            int diceValue = dice.rollDice();
            int nextCell = currentPosition + diceValue;

            if (nextCell > boardSize) {
                nextTurn.offer(player); // Player remains in the queue if they exceed the board
            } else if (nextCell == boardSize) {
                System.out.println(player.getPlayerName() + " won the game!");
                break; // Terminate the game once the first player wins
            } else {
                int[] nextPosition = new int[1];
                boolean[] ladderUsed = new boolean[1];
                nextPosition[0] = nextCell;

                // Check for snakes
                snakes.forEach(snake -> {
                    if (snake.startPoint == nextCell) {
                        nextPosition[0] = snake.endPoint;
                    }
                });

                if (nextPosition[0] != nextCell) {
                    System.out.println(player.getPlayerName() + " bitten by a snake at: " + nextCell);
                }

                // Check for ladders
                ladders.forEach(ladder -> {
                    if (ladder.startPoint == nextCell) {
                        nextPosition[0] = ladder.endPoint;
                        ladderUsed[0] = true;
                    }
                });

                if (nextPosition[0] != nextCell && ladderUsed[0]) {
                    System.out.println(player.getPlayerName() + " climbed a ladder at: " + nextCell);
                }

                if (nextPosition[0] == boardSize) {
                    System.out.println(player.getPlayerName() + " won the game!");
                    break; // Terminate the game once the first player wins
                } else {
                    playersCurrentPosition.put(player.getPlayerName(), nextPosition[0]);
                    System.out.println(player.getPlayerName() + " is at position " + nextPosition[0]);
                    nextTurn.offer(player);
                }
            }
        }
    }
}
