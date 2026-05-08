import java.util.*;

public class Tuprak_6 {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner sc = new Scanner(System.in);
        Book book1 = new Book("Java Programming", 1, "John Doe");
        DVD dvd1 = new DVD("Inception", 2, 148);
        library.addItem(book1);
        library.addItem(dvd1);
        Member member1 = new Member("Alice", "M001");
        library.addMember(member1);
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Tambah Item");
            System.out.println("2. Pinjam Item");
            System.out.println("3. Kembalikan Item");
            System.out.println("4. Lihat Status Perpustakaan");
            System.out.println("5. Lihat Item Dipinjam Member");
            System.out.println("6. Lihat Log");
            System.out.println("7. Tambah Member");
            System.out.println("8. Keluar");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline
            switch (choice) {
                case 1:
                    System.out.println("Jenis item (Book/DVD): ");
                    String type = sc.nextLine();
                    System.out.println("Title: ");
                    String title = sc.nextLine();
                    System.out.println("ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    if (type.equals("Book")) {
                        System.out.println("Author: ");
                        String author = sc.nextLine();
                        Book book = new Book(title, id, author);
                        System.out.println(library.addItem(book));
                    } else if (type.equals("DVD")) {
                        System.out.println("Duration: ");
                        int dur = sc.nextInt();
                        DVD dvd = new DVD(title, id, dur);
                        System.out.println(library.addItem(dvd));
                    }
                    break;
                case 2:
                    System.out.println("Member ID: ");
                    String mid = sc.nextLine();
                    Member m = library.findMemberById(mid);
                    System.out.println("Item ID: ");
                    int iid = sc.nextInt();
                    LibraryItem item = library.findItemById(iid);
                    System.out.println("Days: ");
                    int days = sc.nextInt();
                    try {
                        String msg = m.borrow(item, days);
                        library.logActivity(item.getClass().getSimpleName() + " " + item.title + " dipinjam oleh " + m.getName());
                        System.out.println(msg);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("Member ID: ");
                    String mid2 = sc.nextLine();
                    Member m2 = library.findMemberById(mid2);
                    System.out.println("Item ID: ");
                    int iid2 = sc.nextInt();
                    LibraryItem item2 = library.findItemById(iid2);
                    System.out.println("Days Late: ");
                    int late = sc.nextInt();
                    try {
                        String msg = m2.returnItem(item2, late);
                        library.logActivity(item2.getClass().getSimpleName() + " " + item2.title + " dikembalikan oleh " + m2.getName());
                        System.out.println(msg);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 4:
                    System.out.println(library.getLibraryStatus());
                    break;
                case 5:
                    System.out.println("Member ID: ");
                    String mid3 = sc.nextLine();
                    Member m3 = library.findMemberById(mid3);
                    m3.getBorrowedItems();
                    break;
                case 6:
                    System.out.println(library.getAllLogs());
                    break;
                case 7:
                    System.out.println("Name: ");
                    String name = sc.nextLine();
                    System.out.println("Member ID: ");
                    String memId = sc.nextLine();
                    Member newMember = new Member(name, memId);
                    library.addMember(newMember);
                    System.out.println("Member " + name + " berhasil ditambahkan");
                    break;
                case 8:
                    return;
            }
        }
    }
}
