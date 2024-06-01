import java.util.Scanner;

public class AksiUser extends Aksi {
    @Override
    public void tampilanAksi() {
        System.out.println("Aksi User:");
        System.out.println("1. Pesan Film");
        System.out.println("2. Lihat Saldo");
        System.out.println("3. Lihat List Film");
        System.out.println("4. Lihat Pesanan");
        System.out.println("5. Logout");
        System.out.println("6. Tutup Aplikasi");
    }

    @Override
    public void keluar() {
        Akun.logout();
        System.out.println("Anda telah logout.");
    }

    @Override
    public void tutupAplikasi() {
        System.out.println("Aplikasi ditutup.");
        System.exit(0);
    }

    @Override
    public void lihatListFilm() {
        // Implementasi melihat list film
        // mengambil method getFilms untuk menampilkan list film
        for (Film film : Film.getFilms().values()) {
            System.out.println(film.getName() + " - " + film.getDescription() + " - Harga: " + film.getPrice()
                    + " - Stok: " + film.getStock());
        }
    }

    public void lihatSaldo() {
        // Implementasi lihat Saldo
        // method getCurrentUser untuk melihat user yang sedang log in
        User currentUser = Akun.getCurrentUser();
        if (currentUser != null) {
            System.out.println("Saldo anda: " + currentUser.getSaldo());
        }
    }

    public void pesanFilm() {
        // Implementasi pemesanan film
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nama Film yang ingin dipesan: ");
        String namaFilm = scanner.nextLine();
        Film film = Film.getFilms().get(namaFilm); // untuk menunjukan list film
        if (film == null) {
            System.out.println("Film yang dicari tidak ditemukan.");
            // jika input user tidak sesuai dengan film yang ada di list
            return;
        }
        System.out.print("Jumlah tiket yang ingin dipesan: ");
        int jumlahTiket = scanner.nextInt();
        if (jumlahTiket > film.getStock()) {
            System.out.println("Stok tiket tidak mencukupi.");
            // jika input tiket lebih banyak dari stok tiket film yang tersedia
            return;
        }
        double totalHarga = jumlahTiket * film.getPrice();
        User currentUser = Akun.getCurrentUser();
        if (totalHarga > currentUser.getSaldo()) {
            System.out.println("Saldo tidak mencukupi, saldo yang dimiliki " + currentUser.getSaldo() + ".");
            // jika saldo user yang sedang log in lebih sedikit dari harga pesanan
            return;
        }
        film.setStock(film.getStock() - jumlahTiket); // untuk mengurangi jumlah stok tiket
        currentUser.setSaldo(currentUser.getSaldo() - totalHarga); // untuk mengurangi saldo user dengan harga pesanan
        currentUser.addPesanan(film, jumlahTiket); // menampilkan nama film dan jumlah tiket
        System.out.println("Tiket berhasil dipesan.");
    }

    public void lihatPesanan() {
        // Implementasi melihat pesanan
        User currentUser = Akun.getCurrentUser(); // untuk melihat akun user yang sedang log in
        if (currentUser != null && !currentUser.getPesanan().isEmpty()) {
            for (Pesanan pesanan : currentUser.getPesanan().values()) {
                System.out.println("Film: " + pesanan.getFilm().getName() + " - Jumlah: " + pesanan.getKuantitas()
                        + " - Total Harga: " + (pesanan.getKuantitas() * pesanan.getFilm().getPrice()));
                // menampilakan informasi pesanan user
            }
        } else {
            System.out.println("Kamu belum pernah melakukan pemesanan."); // jika user belum membuat pesanan
        }
    }
}