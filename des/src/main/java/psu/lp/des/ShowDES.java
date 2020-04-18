package psu.lp.des;

import java.util.Arrays;
import java.util.Scanner;

public class ShowDES {
    public static void main(String[] args) {
        String word = "";
        String key = "";
        Scanner in = new Scanner(System.in);


        while (word.length() != 8) {
            System.out.println("Введите фразу из 8 символов для шифрования:");
            word = in.nextLine();
        }

        byte[] binaryWord = DES.wordToBinaryAsByteArray(word);
        System.out.print("Исходное слово в двоичной записи:\n    ");
        for (int i = 0; i < binaryWord.length; i++) {
            System.out.print(binaryWord[i]);
        }
        System.out.println();

        DES des = new DES("bestkey");

        byte[] encrypted = des.encryptDES(binaryWord);
        System.out.print("Шифр слова в двоичной записи:\n    ");
        StringBuilder encryptedS = new StringBuilder();
        for (int i = 0; i < encrypted.length; i++) {
            System.out.print(encrypted[i]);
            encryptedS.append(encrypted[i]);
        }
        System.out.println();
        System.out.print("Зашифрованное слово в виде символов:\n    ");
        for (int i = 0; i < 8; i++) {
            char c = (char) Integer.parseInt(encryptedS.substring(i*8, i*8+8), 2);
            System.out.print(c);
        }
        System.out.println();

        byte[] decrypted = des.decryptDES(encrypted);
        System.out.print("Расшифрованное слово в двоичной записи:\n    ");
        for (int i = 0; i < decrypted.length; i++) {
            System.out.print(decrypted[i]);
        }
        System.out.println();

        if (Arrays.equals(binaryWord, decrypted)) {
            System.out.println("Расшифрованное слово совпадает с исходным! Шифрование и расшифрование работает корректно.");
        }
    }
}
