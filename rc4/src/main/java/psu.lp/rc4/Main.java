package psu.lp.rc4;

public class Main {

    private static int[] s = new int[256];
    private static int[] k = new int[256];
    private static int i = 0, j = 0;

    private static int Z() {
        i = (i + 1) % 256;
        j = (j + s[i]) % 256;
        swapS(j, i);

        return s[(s[i] + s[j]) % 256];
    }

    public static void main(String[] args) {
        for (i = 0; i < s.length; i++) {
            s[i] = i;
        }
        System.out.print("Введите n: ");
        int n = 6;
        for (i = 0; i < k.length; i++) {
            k[i] = (i % n);
        }
        j = 0;
        for (i = 0; i < s.length; i++) {
            j = (s[i] + k[i] + j) % 256;
            swapS(j, i);
        }

        i = j = 0;

        String kek = "ABCDEFGHIJKLMNOP";
        StringBuilder answer = new StringBuilder();
        for (int k = 0; k < kek.length(); k++) {
            answer.append((char) ((int) kek.charAt(k) ^ Z()));
        }
        String strAnswer = answer.toString();
        System.out.println(strAnswer);
        printAsHex(strAnswer);
    }

    private static void swapS(int j, int i) {
        int tmp = s[i];
        s[i] = s[j];
        s[j] = tmp;
    }

    private static void printAsHex(String output) {
        for (int i = 0; i < output.length(); i++) {
            System.out.printf("%02X ", (int) output.charAt(i));
        }
        System.out.println();
    }
}