import java.util.concurrent.atomic.AtomicBoolean;

public class Monitoring implements Runnable {
    private Gudang gudang;
    private AtomicBoolean running;

    public Monitoring(Gudang gudang, AtomicBoolean running) {
        this.gudang = gudang;
        this.running = running;
    }

    @Override
    public void run() {
        while (running.get()) {
            int stok = gudang.getStok();
            int kapasitas = gudang.getKapasitasMaksimal();
            String bar = buildBar(stok, kapasitas);
            System.out.printf("[Monitoring] Status Gudang: %s %d/%d%n", bar, stok, kapasitas);
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
        System.out.println("[Monitoring] Thread monitoring berhenti.");
    }

    private String buildBar(int stok, int kapasitas) {
        int filled = (int) Math.round((double) stok / kapasitas * 10);
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < 10; i++) {
            sb.append(i < filled ? '=' : ' ');
        }
        sb.append(']');
        return sb.toString();
    }
}
