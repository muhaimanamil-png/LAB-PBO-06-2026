public class Gudang {
    private int stok;
    private int kapasitasMaksimal;

    public Gudang(int kapasitasMaksimal) {
        this.kapasitasMaksimal = kapasitasMaksimal;
        this.stok = 0;
    }

    public synchronized void tambahStok(int jumlah) {
        while (stok + jumlah > kapasitasMaksimal) {
            try {
                System.out.println("[Gudang] Gudang penuh. Pemasok menunggu...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        stok += jumlah;
        System.out.printf("[Gudang] Stok ditambah %d. Stok sekarang: %d/%d.%n", jumlah, stok, kapasitasMaksimal);
        notifyAll();
    }

    public synchronized void ambilStok(int jumlah) {
        while (stok < jumlah) {
            try {
                System.out.println("[Gudang] Stok tidak cukup. Kurir menunggu...");
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
        stok -= jumlah;
        System.out.printf("[Gudang] Stok diambil %d. Stok sekarang: %d/%d.%n", jumlah, stok, kapasitasMaksimal);
        notifyAll();
    }

    public synchronized int getStok() {
        return stok;
    }

    public int getKapasitasMaksimal() {
        return kapasitasMaksimal;
    }

    public synchronized void notifyAllWaitingThreads() {
        notifyAll();
    }
}
