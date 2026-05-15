import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Pemasok implements Runnable {
    private final String nama;
    private final Gudang gudang;
    private final AtomicBoolean running;
    private final Random random = new Random();

    public Pemasok(String nama, Gudang gudang, AtomicBoolean running) {
        this.nama = nama;
        this.gudang = gudang;
        this.running = running;
    }

    @Override
    public void run() {
        while (running.get() && !Thread.currentThread().isInterrupted()) {
            int jumlah = random.nextInt(3) + 1;
            System.out.printf("[%s] Mencoba menambahkan %d barang...%n", nama, jumlah);
            gudang.tambahStok(jumlah);

            try {
                Thread.sleep(1_000 + random.nextInt(1_001));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.printf("[%s] Berhenti.%n", nama);
    }
}
