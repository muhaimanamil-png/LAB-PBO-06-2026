import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Tuprak_7_2 {
    public static void main(String[] args) {
        List<String> documents = Arrays.asList(
                "Dokumen_01.txt",
                "Dokumen_02.txt",
                "Dokumen_03.txt",
                "Dokumen_04.txt",
                "Dokumen_05.txt",
                "Dokumen_06.txt",
                "Dokumen_07.txt",
                "Dokumen_08.txt",
                "Dokumen_09.txt",
                "Dokumen_10.txt"
        );

        int threadCount = 4;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(documents.size());

        DataProcessor processor = new DataProcessor();
        Map<String, Integer> resultWords = new ConcurrentHashMap<>();
        Map<String, Long> resultDurations = new ConcurrentHashMap<>();
        Map<String, String> resultThreads = new ConcurrentHashMap<>();

        System.out.println("Memulai pemrosesan dokumen dengan " + threadCount + " thread...\n");

        for (String document : documents) {
            executor.submit(() -> {
                long startTime = System.currentTimeMillis();
                int words = processor.process(document);
                long durationMs = System.currentTimeMillis() - startTime;

                resultWords.put(document, words);
                resultDurations.put(document, durationMs);
                resultThreads.put(document, Thread.currentThread().getName());

                System.out.printf("[%s] Selesai memproses %s (%d kata).%n",
                        Thread.currentThread().getName(), document, words);
                latch.countDown();
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Thread utama terganggu saat menunggu penyelesaian dokumen.");
        } finally {
            executor.shutdown();
        }

        printSummary(documents, resultWords, resultThreads, resultDurations);
    }

    private static void printSummary(List<String> documents,
                                     Map<String, Integer> resultWords,
                                     Map<String, String> resultThreads,
                                     Map<String, Long> resultDurations) {
        System.out.println("\n===== Tabel Ringkasan Pemrosesan =====");
        System.out.format("| Nama Dokumen    | Thread          | Durasi (ms)  | Jumlah Kata|%n");

        long totalWords = 0;
        long totalDuration = 0;

        for (String document : documents) {
            int words = resultWords.getOrDefault(document, 0);
            String thread = resultThreads.getOrDefault(document, "-");
            long duration = resultDurations.getOrDefault(document, 0L);

            System.out.format("| %-15s | %-10s | %-12d | %-10d |%n",
                    document, thread, duration, words);

            totalWords += words;
            totalDuration += duration;
        }

        System.out.format("------------------------------------------------------------%n");

        double averageDuration = documents.isEmpty() ? 0.0 : (double) totalDuration / documents.size();
        DecimalFormat df = new DecimalFormat("0.00");

        System.out.println("Total kata keseluruhan : " + totalWords);
        System.out.println("Rata-rata durasi       : " + df.format(averageDuration) + " ms");
    }
}
