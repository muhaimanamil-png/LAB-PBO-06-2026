import java.util.*;

public class Library {
    private List<LibraryItem> items;
    private List<Member> members;
    private LibraryLogger logger;

    public Library() {
        items = new ArrayList<>();
        members = new ArrayList<>();
        logger = new LibraryLogger();
    }

    public String addItem(LibraryItem item) {
        items.add(item);
        return item.title + " berhasil ditambahkan";
    }

    public LibraryItem findItemById(int itemId) {
        for (LibraryItem item : items) {
            if (item.itemId == itemId) {
                return item;
            }
        }
        throw new NoSuchElementException("Item dengan ID " + itemId + " tidak ditemukan");
    }

    public String getLibraryStatus() {
        StringBuilder sb = new StringBuilder();
        for (LibraryItem item : items) {
            sb.append(item.getDescription()).append(" - ").append(item.isBorrowed() ? "Dipinjam" : "Tersedia").append("\n");
        }
        return sb.toString();
    }

    public String getAllLogs() {
        return logger.getLogs();
    }

    public void logActivity(String activity) {
        logger.logActivity(activity);
    }

    public void addMember(Member member) {
        members.add(member);
    }

    public Member findMemberById(String memberId) {
        for (Member m : members) {
            if (m.getMemberId().equals(memberId)) {
                return m;
            }
        }
        throw new NoSuchElementException("Member tidak ditemukan");
    }
}