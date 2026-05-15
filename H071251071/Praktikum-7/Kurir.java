import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Kurir implements Runnable {
    private String nama;
    private Gudang gudang;
    private AtomicBoolean running;
    private Random random = new Random();

    public Kurir(String nama, Gudang gudang, AtomicBoolean running) {
        this.nama = nama;
        this.gudang = gudang;
        this.running = running;
    }

    @Override
    public void run() {
        while (running.get() && !Thread.currentThread().isInterrupted()) {
            int jumlah = random.nextInt(3) + 1;
            System.out.printf("[%s] Mencoba mengambil %d barang...%n", nama, jumlah);
            gudang.ambilStok(jumlah);

            try {
                Thread.sleep(2000 + random.nextInt(1000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.printf("[%s] Berhenti.%n", nama);
    }
}
