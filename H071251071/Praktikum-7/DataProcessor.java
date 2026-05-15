import java.util.Random;

public class DataProcessor {
    private final Random random = new Random();

    public int process(String fileName) {
        try {
            int delay = 500 + random.nextInt(1500); // 500ms - 2000ms
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        int baseWordCount = 50 + (fileName.length() * 3);
        int simulatedWords = baseWordCount + random.nextInt(100);
        return simulatedWords;
    }
}
