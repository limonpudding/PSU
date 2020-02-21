package psu.lp.kuznechik;

import javax.xml.bind.DatatypeConverter;
import java.util.Arrays;

public class Break {


    static byte[] key_1 =
            {0x77, 0x66, 0x55, 0x44, 0x33, 0x22, 0x11, 0x00, (byte) 0xff, (byte) 0xee,
                    (byte) 0xdd, (byte) 0xcc, (byte) 0xbb, (byte) 0xaa, (byte) 0x99, (byte) 0x88};
    static byte[] key_2 =
            {(byte) 0xef, (byte) 0xcd, (byte) 0xab, (byte) 0x89, 0x67, 0x45, 0x23, 0x01,
                    0x10, 0x32, 0x54, 0x76, (byte) 0x98, (byte) 0xba, (byte) 0xdc, (byte) 0xfe};
    static byte[] blk = DatatypeConverter.parseHexBinary("8899aabbccddeeff0077665544332211");

    static byte[][] iterKey;
    static byte[] masterKey = {0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07, 0x08, 0x09, 0x0A, 0x0B, 0x0C, 0x0D, 0x0E, 0x0F, 0x10, 0x11, 0x12, 0x13, 0x14, 0x15, 0x16, 0x17, 0x18, 0x19, 0x1A, 0x1B, 0x1C, 0x1D, 0x1E, 0x1F};
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
//                kuznechik.expandKey(Arrays.copyOf(masterKey, 16), Arrays.copyOfRange(masterKey, 16, 16));
                if (Arrays.equals(temp, (kuznechik.encrypt(plainText)))) {
                    switch (iterFalse) {
                        case 0:
                            result[i] = (byte) (plainText[i] ^ Kuznechik.reverse_Pi[0]);
                            break;
                        case 1:
                            byte[] temp2 = plainText;
                            //output(plainText);
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
//        Kuznechik kuznechik = new Kuznechik();
//        kuznechik.expandKey(key_1, key_2);
//        byte[] encriptBlok = kuznechik.encrypt(blk);
//        System.out.println(DatatypeConverter.printHexBinary(encriptBlok));
//        byte[] decriptBlok = kuznechik.decrypt(encriptBlok);
//        System.out.println(DatatypeConverter.printHexBinary(decriptBlok));

        byte[] k1 = new byte[16];
        byte[] k2 = new byte[16];
        iterKey = kuznechik.expandKey(Arrays.copyOf(masterKey, 16), Arrays.copyOfRange(masterKey, 16, 32));
        k1 = breakKuznechik(0);
        k2 = breakKuznechik(1);

        System.out.println("Добытый мастер ключ");
        byte[] newMasterKey = solderKeyParts(k1, k2);
        output(newMasterKey);
        if (Arrays.equals(newMasterKey,masterKey))
        {
            System.out.println("Успешно");
        }
        else
        {
            System.out.println("Неудача");
        }
    }

    private static void output(byte[] key) {
        for (int i = 0; i < key.length; i++) {
            System.out.print(key[i] + "  ");
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
