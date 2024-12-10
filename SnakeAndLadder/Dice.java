
import java.util.Random;

public class Dice {
    private int numberOfDice;

    Dice(int numberOfDice) {
        this.numberOfDice = numberOfDice;
    }

    int rollDice() {
        int totalRoll = 0;
        for (int i = 0; i < numberOfDice; i++) {
            totalRoll += (int) (Math.random() * 6) + 1; // Roll each die
        }
        return totalRoll;
    }
}
