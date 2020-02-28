package psu.lp.kuznechik;

import java.util.Arrays;

public class Break {

    static byte[][] iterKey;
    static byte[] masterKey = {0x00, 0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, (byte) 0x88, (byte) 0x99, (byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD, (byte) 0xEE, (byte) 0xFF, 0x00, 0x11, 0x22, 0x33, 0x44, 0x55, 0x66, 0x77, (byte) 0x88, (byte) 0x99, (byte) 0xAA, (byte) 0xBB, (byte) 0xCC, (byte) 0xDD, (byte) 0xEE, (byte) 0xFF};
    static Kuznechik kuznechik = new Kuznechik();

    static byte[] breakKuznechik(int iterFalse) {
        byte[] plainText = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        byte[] tmpByte;
        byte[] result = new byte[16];

        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 256; j++) {
                plainText[i] = (byte) j;
                tmpByte = Arrays.copyOf(plainText, plainText.length);
                for (int k = 0; k < iterFalse; k++) {
                    tmpByte = Kuznechik.kuzX(tmpByte, iterKey[k]);
                    tmpByte = Kuznechik.kuzS(tmpByte);
                    tmpByte = Kuznechik.kuzL(tmpByte);
                }
                tmpByte = Kuznechik.kuzX(tmpByte, iterKey[iterFalse]);
                tmpByte = Kuznechik.kuzS(tmpByte);
                tmpByte[i] = 0;
                tmpByte = Kuznechik.kuzL(tmpByte);
                for (int k = iterFalse + 1; k < 9; k++) {
                    tmpByte = Kuznechik.kuzX(tmpByte, iterKey[k]);
                    tmpByte = Kuznechik.kuzS(tmpByte);
                    tmpByte = Kuznechik.kuzL(tmpByte);
                }
                tmpByte = Kuznechik.kuzX(tmpByte, iterKey[9]);
                if (Arrays.equals(tmpByte, (kuznechik.encrypt(plainText)))) {
                    switch (iterFalse) {
                        case 0:
                            result[i] = (byte) (plainText[i] ^ Kuznechik.reverse_Pi[0]);
                            break;
                        case 1:
                            byte[] tmpByte2 = plainText;
                            tmpByte2 = Kuznechik.kuzX(tmpByte2, iterKey[0]);
                            tmpByte2 = Kuznechik.kuzS(tmpByte2);
                            tmpByte2 = Kuznechik.kuzL(tmpByte2);
                            result[i] = (byte) (tmpByte2[i] ^ Kuznechik.reverse_Pi[0]);
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
