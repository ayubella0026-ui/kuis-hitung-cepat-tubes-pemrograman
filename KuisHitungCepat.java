import java.util.Random;
import java.util.Scanner;

public class KuisHitungCepat {

    static final int JUMLAH_SOAL = 5;
    static final int POIN_BENAR = 20;

    static final int EASY = 1;
    static final int MEDIUM = 2;
    static final int HARD = 3;

    static int hasilkanOperand(Random random, int tingkatKesulitan) {
        int batasAtas;
        switch (tingkatKesulitan) {
            case EASY:
                batasAtas = 10;
                break;
            case MEDIUM:
                batasAtas = 50;
                break;
            case HARD:
                batasAtas = 20;
                break;
            default:
                batasAtas = 10;
        }
        return random.nextInt(batasAtas) + 1;
    }

    static int hitungJawaban(int operandPertama, int operandKedua, int tingkatKesulitan) {
        int hasil;
        switch (tingkatKesulitan) {
            case EASY:
                hasil = operandPertama + operandKedua;
                break;
            case MEDIUM:
                hasil = operandPertama - operandKedua;
                break;
            case HARD:
                hasil = operandPertama * operandKedua;
                break;
            default:
                hasil = 0;
        }
        return hasil;
    }

    static boolean periksaJawaban(int jawabanBenar, int jawabanPemain) {
        return jawabanBenar == jawabanPemain;
    }

    static void tampilkanRekap(int[] jawabanBenar, int[] jawabanPemain) {
        int jumlahBenar = 0;
        for (int i = 0; i < JUMLAH_SOAL; i++) {
            if (periksaJawaban(jawabanBenar[i], jawabanPemain[i])) {
                jumlahBenar++;
            }
        }
        int jumlahSalah = JUMLAH_SOAL - jumlahBenar;
        int skorAkhir = jumlahBenar * POIN_BENAR;

        System.out.println("[ REKAP RONDE ]");
        System.out.println("Jumlah benar : " + jumlahBenar);
        System.out.println("Jumlah salah : " + jumlahSalah);
        System.out.println("Skor akhir   : " + skorAkhir + " / 100");
        System.out.println("No | Jawaban Benar | Jawaban Anda | Hasil");
        for (int i = 0; i < JUMLAH_SOAL; i++) {
            String hasil = periksaJawaban(jawabanBenar[i], jawabanPemain[i]) ? "Benar" : "Salah";
            System.out.printf("%2d | %13d | %12d | %s%n",
                    (i + 1), jawabanBenar[i], jawabanPemain[i], hasil);
        }
    }

    static int bacaBilanganBulat(Scanner input, String pesan) {
        System.out.print(pesan);
        while (!input.hasNextInt()) {
            System.out.println("Input harus berupa bilangan bulat.");
            input.next();
            System.out.print(pesan);
        }
        return input.nextInt();
    }

    static int pilihKesulitan(Scanner input) {
        int pilihan = 0;
        boolean pilihanValid = false;

        while (!pilihanValid) {
            System.out.println();
            System.out.println("[ PILIH TINGKAT KESULITAN ]");
            System.out.println("1. Easy    >  (penjumlahan 1-10)");
            System.out.println("2. Medium  >  (pengurangan 1-50)");
            System.out.println("3. Hard    >  (perkalian   1-20)");
            pilihan = bacaBilanganBulat(input, "Tingkat Kesulitan Anda: ");

            switch (pilihan) {
                case EASY:
                    System.out.println("Mode Easy dipilih.");
                    pilihanValid = true;
                    break;
                case MEDIUM:
                    System.out.println("Mode Medium dipilih.");
                    pilihanValid = true;
                    break;
                case HARD:
                    System.out.println("Mode Hard dipilih.");
                    pilihanValid = true;
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Pilih 1, 2, atau 3.");
            }
        }
        return pilihan;
    }

    static String ambilSimbol(int tingkatKesulitan) {
        switch (tingkatKesulitan) {
            case EASY:
                return "+";
            case MEDIUM:
                return "-";
            case HARD:
                return "x";
            default:
                return "?";
        }
    }

    static void mainkanRonde(Scanner input, Random random, int tingkatKesulitan) {
        int[] jawabanBenar = new int[JUMLAH_SOAL];
        int[] jawabanPemain = new int[JUMLAH_SOAL];

        System.out.println();
        for (int i = 0; i < JUMLAH_SOAL; i++) {
            int operandPertama = hasilkanOperand(random, tingkatKesulitan);
            int operandKedua = hasilkanOperand(random, tingkatKesulitan);
            jawabanBenar[i] = hitungJawaban(operandPertama, operandKedua, tingkatKesulitan);

            String soal = "Soal " + (i + 1) + ": " + operandPertama + " "
                    + ambilSimbol(tingkatKesulitan) + " " + operandKedua + " = ";
            jawabanPemain[i] = bacaBilanganBulat(input, soal);

            if (periksaJawaban(jawabanBenar[i], jawabanPemain[i])) {
                System.out.println("Benar!");
            } else {
                System.out.println("Salah. Jawaban yang benar: " + jawabanBenar[i]);
            }
        }
        tampilkanRekap(jawabanBenar, jawabanPemain);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Random random = new Random();
        int pilihanLanjut;

        System.out.println("[ KUIS HITUNG CEPAT INTERAKTIF ]");

        do {
            int tingkatKesulitan = pilihKesulitan(input);
            mainkanRonde(input, random, tingkatKesulitan);

            pilihanLanjut = -1;
            while (pilihanLanjut != 0 && pilihanLanjut != 1) {
                pilihanLanjut = bacaBilanganBulat(input, "\nMain lagi? (1 = ya, 0 = selesai): ");
                if (pilihanLanjut != 0 && pilihanLanjut != 1) {
                    System.out.println("Pilihan tidak valid. Ketik 1 atau 0.");
                }
            }
        } while (pilihanLanjut == 1);

        System.out.println("Terima kasih sudah bermain!");
        input.close();
    }
}