import java.util.*;

public class Member {
    private String name;
    private String memberId;
    private List<LibraryItem> borrowedItems;

    public Member(String name, String memberId) {
        this.name = name;
        this.memberId = memberId;
        this.borrowedItems = new ArrayList<>();
    }
    public String getMemberId() {
        return memberId;
    }
    public String getName() {
        return name;
    }

    public String borrow(LibraryItem item, int days) {
        if (item.isBorrowed()) {
            throw new IllegalStateException("Item tidak tersedia");
        }
        String msg = item.borrowItem(days);
        borrowedItems.add(item);
        return msg;
    }

    public String returnItem(LibraryItem item, int daysLate) {
        if (!borrowedItems.contains(item)) {
            throw new IllegalStateException("Item tidak dipinjam oleh member ini");
        }
        String msg = item.returnItem();
        borrowedItems.remove(item);
        double fine = item.calculateFine(daysLate);
        return "Item " + item.title + " berhasil dikembalikan dengan denda: Rp " + String.format("%,.0f", fine);
    }

    public void getBorrowedItems() {
        if (borrowedItems.isEmpty()) {
            System.out.println("Tidak ada item yang dipinjam");
        } else {
            for (LibraryItem item : borrowedItems) {
                System.out.println(item.getDescription());
            }
        }
    }
}