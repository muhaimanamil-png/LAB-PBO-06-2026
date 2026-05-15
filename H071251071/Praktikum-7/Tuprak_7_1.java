import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class Tuprak_7_1 {
    public static void main(String[] args) {
        Gudang gudang = new Gudang(10);
        AtomicBoolean running = new AtomicBoolean(true);

        ExecutorService pemasokPool = Executors.newFixedThreadPool(2);
        ExecutorService kurirPool = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 2; i++) {
            pemasokPool.submit(new Pemasok("Pemasok-" + i, gudang, running));
        }

        for (int i = 1; i <= 3; i++) {
            kurirPool.submit(new Kurir("Kurir-" + i, gudang, running));
        }

        Thread monitoringThread = new Thread(new Monitoring(gudang, running));
        monitoringThread.setDaemon(true);
        monitoringThread.start();

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\n[Main] Waktu habis. Menghentikan sistem...\n");
        running.set(false);
        gudang.notifyAllWaitingThreads();

        pemasokPool.shutdown();
        kurirPool.shutdown();
        try {
            if (!pemasokPool.awaitTermination(5, TimeUnit.SECONDS)) {
                pemasokPool.shutdownNow();
            }
            if (!kurirPool.awaitTermination(5, TimeUnit.SECONDS)) {
                kurirPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            pemasokPool.shutdownNow();
            kurirPool.shutdownNow();
            Thread.currentThread().interrupt();
        }

        try {
            monitoringThread.join(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("[Main] Sistem logistik berhenti dengan aman.");
    }
}
