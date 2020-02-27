package psu.lp.kuznechik;

import java.util.Arrays;

public class Break {

    static byte[][] iterKey;
    static byte[] masterKey = {0x00, 0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, (byte) 0x88, (byte) 0x99, (byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD, (byte) 0xEE, (byte) 0xFF, 0x00, 0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, (byte) 0x88, (byte) 0x99, (byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD, (byte) 0xEE, (byte) 0xFF};
    static Kuznechik kuznechik = new Kuznechik();

    static byte[] breakKuznechik(int iterFalse) {
        byte[] plainText = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        byte[] temp;
        byte[] result = new byte[16];

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 256; j++) {
                plainText[i] = (byte) j;
                temp = Arrays.copyOf(plainText, plainText.length);
                for (int k = 0; k < iterFalse; k++) {
                    temp = Kuznechik.kuzX(temp, iterKey[k]);
                    temp = Kuznechik.kuzS(temp);
                    temp = Kuznechik.kuzL(temp);
                }
                temp = Kuznechik.kuzX(temp, iterKey[iterFalse]);
                temp = Kuznechik.kuzS(temp);
                temp[i] = 0;
                temp = Kuznechik.kuzL(temp);
                for (int k = iterFalse + 1; k < 9; k++) {
                    temp = Kuznechik.kuzX(temp, iterKey[k]);
                    temp = Kuznechik.kuzS(temp);
                    temp = Kuznechik.kuzL(temp);
                }
                temp = Kuznechik.kuzX(temp, iterKey[9]);
                if (Arrays.equals(temp, (kuznechik.encrypt(plainText)))) {
                    switch (iterFalse) {
                        case 0:
                            result[i] = (byte) (plainText[i] ^ Kuznechik.reverse_Pi[0]);
                            break;
                        case 1:
                            byte[] temp2 = plainText;
                            temp2 = Kuznechik.kuzX(temp2, iterKey[0]);
                            temp2 = Kuznechik.kuzS(temp2);
                            temp2 = Kuznechik.kuzL(temp2);
                            result[i] = (byte) (temp2[i] ^ Kuznechik.reverse_Pi[0]);
                            break;
                    }
                    break;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        byte[] k1;
        byte[] k2;
        iterKey = kuznechik.expandKey(Arrays.copyOf(masterKey, 16), Arrays.copyOfRange(masterKey, 16, 32));
        k1 = breakKuznechik(0);
        k2 = breakKuznechik(1);

        System.out.print("Заданный ключ шифрования: ");
        printAsHex(masterKey);
        System.out.print("Полученный мастер ключ: ");
        byte[] newMasterKey = solderKeyParts(k1, k2);
        printAsHex(newMasterKey);
        if (Arrays.equals(newMasterKey, masterKey)) {
            System.out.println("Полученный ключ совпадает с заданным ключом шифрования!");
        } else {
            System.out.println("Ключи не совпадают!");
        }
    }

    private static void printAsHex(byte[] key) {
        for (int i = 0; i < 32; i++) {
                System.out.printf("%02x", key[i]);
        }
        System.out.println();
    }

    private static byte[] solderKeyParts(byte[] k1, byte[] k2) {
        int newSize = k1.length + k2.length;
        byte[] solderedKey = new byte[newSize];
        int half = k1.length;
        System.arraycopy(k1, 0, solderedKey, 0, k1.length);
        System.arraycopy(k2, 0, solderedKey, half, k2.length);
        return solderedKey;
    }
}
