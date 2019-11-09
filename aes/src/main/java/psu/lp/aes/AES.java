package psu.lp.aes;

import java.util.Arrays;
import java.util.Scanner;

public class AES {

    private static int[][] sBox = new int[][]{
            {0x63, 0x7c, 0x77, 0x7b, 0xf2, 0x6b, 0x6f, 0xc5, 0x30, 0x01, 0x67, 0x2b, 0xfe, 0xd7, 0xab, 0x76},
            {0xca, 0x82, 0xc9, 0x7d, 0xfa, 0x59, 0x47, 0xf0, 0xad, 0xd4, 0xa2, 0xaf, 0x9c, 0xa4, 0x72, 0xc0},
            {0xb7, 0xfd, 0x93, 0x26, 0x36, 0x3f, 0xf7, 0xcc, 0x34, 0xa5, 0xe5, 0xf1, 0x71, 0xd8, 0x31, 0x15},
            {0x04, 0xc7, 0x23, 0xc3, 0x18, 0x96, 0x05, 0x9a, 0x07, 0x12, 0x80, 0xe2, 0xeb, 0x27, 0xb2, 0x75},
            {0x09, 0x83, 0x2c, 0x1a, 0x1b, 0x6e, 0x5a, 0xa0, 0x52, 0x3b, 0xd6, 0xb3, 0x29, 0xe3, 0x2f, 0x84},
            {0x53, 0xd1, 0x00, 0xed, 0x20, 0xfc, 0xb1, 0x5b, 0x6a, 0xcb, 0xbe, 0x39, 0x4a, 0x4c, 0x58, 0xcf},
            {0xd0, 0xef, 0xaa, 0xfb, 0x43, 0x4d, 0x33, 0x85, 0x45, 0xf9, 0x02, 0x7f, 0x50, 0x3c, 0x9f, 0xa8},
            {0x51, 0xa3, 0x40, 0x8f, 0x92, 0x9d, 0x38, 0xf5, 0xbc, 0xb6, 0xda, 0x21, 0x10, 0xff, 0xf3, 0xd2},
            {0xcd, 0x0c, 0x13, 0xec, 0x5f, 0x97, 0x44, 0x17, 0xc4, 0xa7, 0x7e, 0x3d, 0x64, 0x5d, 0x19, 0x73},
            {0x60, 0x81, 0x4f, 0xdc, 0x22, 0x2a, 0x90, 0x88, 0x46, 0xee, 0xb8, 0x14, 0xde, 0x5e, 0x0b, 0xdb},
            {0xe0, 0x32, 0x3a, 0x0a, 0x49, 0x06, 0x24, 0x5c, 0xc2, 0xd3, 0xac, 0x62, 0x91, 0x95, 0xe4, 0x79},
            {0xe7, 0xc8, 0x37, 0x6d, 0x8d, 0xd5, 0x4e, 0xa9, 0x6c, 0x56, 0xf4, 0xea, 0x65, 0x7a, 0xae, 0x08},
            {0xba, 0x78, 0x25, 0x2e, 0x1c, 0xa6, 0xb4, 0xc6, 0xe8, 0xdd, 0x74, 0x1f, 0x4b, 0xbd, 0x8b, 0x8a},
            {0x70, 0x3e, 0xb5, 0x66, 0x48, 0x03, 0xf6, 0x0e, 0x61, 0x35, 0x57, 0xb9, 0x86, 0xc1, 0x1d, 0x9e},
            {0xe1, 0xf8, 0x98, 0x11, 0x69, 0xd9, 0x8e, 0x94, 0x9b, 0x1e, 0x87, 0xe9, 0xce, 0x55, 0x28, 0xdf},
            {0x8c, 0xa1, 0x89, 0x0d, 0xbf, 0xe6, 0x42, 0x68, 0x41, 0x99, 0x2d, 0x0f, 0xb0, 0x54, 0xbb, 0x16}
    };

    private static int[][] invSBox = new int[][]{
            {0x52, 0x09, 0x6a, 0xd5, 0x30, 0x36, 0xa5, 0x38, 0xbf, 0x40, 0xa3, 0x9e, 0x81, 0xf3, 0xd7, 0xfb},
            {0x7c, 0xe3, 0x39, 0x82, 0x9b, 0x2f, 0xff, 0x87, 0x34, 0x8e, 0x43, 0x44, 0xc4, 0xde, 0xe9, 0xcb},
            {0x54, 0x7b, 0x94, 0x32, 0xa6, 0xc2, 0x23, 0x3d, 0xee, 0x4c, 0x95, 0x0b, 0x42, 0xfa, 0xc3, 0x4e},
            {0x08, 0x2e, 0xa1, 0x66, 0x28, 0xd9, 0x24, 0xb2, 0x76, 0x5b, 0xa2, 0x49, 0x6d, 0x8b, 0xd1, 0x25},
            {0x72, 0xf8, 0xf6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xd4, 0xa4, 0x5c, 0xcc, 0x5d, 0x65, 0xb6, 0x92},
            {0x6c, 0x70, 0x48, 0x50, 0xfd, 0xed, 0xb9, 0xda, 0x5e, 0x15, 0x46, 0x57, 0xa7, 0x8d, 0x9d, 0x84},
            {0x90, 0xd8, 0xab, 0x00, 0x8c, 0xbc, 0xd3, 0x0a, 0xf7, 0xe4, 0x58, 0x05, 0xb8, 0xb3, 0x45, 0x06},
            {0xd0, 0x2c, 0x1e, 0x8f, 0xca, 0x3f, 0x0f, 0x02, 0xc1, 0xaf, 0xbd, 0x03, 0x01, 0x13, 0x8a, 0x6b},
            {0x3a, 0x91, 0x11, 0x41, 0x4f, 0x67, 0xdc, 0xea, 0x97, 0xf2, 0xcf, 0xce, 0xf0, 0xb4, 0xe6, 0x73},
            {0x96, 0xac, 0x74, 0x22, 0xe7, 0xad, 0x35, 0x85, 0xe2, 0xf9, 0x37, 0xe8, 0x1c, 0x75, 0xdf, 0x6e},
            {0x47, 0xf1, 0x1a, 0x71, 0x1d, 0x29, 0xc5, 0x89, 0x6f, 0xb7, 0x62, 0x0e, 0xaa, 0x18, 0xbe, 0x1b},
            {0xfc, 0x56, 0x3e, 0x4b, 0xc6, 0xd2, 0x79, 0x20, 0x9a, 0xdb, 0xc0, 0xfe, 0x78, 0xcd, 0x5a, 0xf4},
            {0x1f, 0xdd, 0xa8, 0x33, 0x88, 0x07, 0xc7, 0x31, 0xb1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xec, 0x5f},
            {0x60, 0x51, 0x7f, 0xa9, 0x19, 0xb5, 0x4a, 0x0d, 0x2d, 0xe5, 0x7a, 0x9f, 0x93, 0xc9, 0x9c, 0xef},
            {0xa0, 0xe0, 0x3b, 0x4d, 0xae, 0x2a, 0xf5, 0xb0, 0xc8, 0xeb, 0xbb, 0x3c, 0x83, 0x53, 0x99, 0x61},
            {0x17, 0x2b, 0x04, 0x7e, 0xba, 0x77, 0xd6, 0x26, 0xe1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0c, 0x7d}
    };

    public static int[][] rcon = new int[][]{
            {0, 0, 0, 0},
            {0x01, 0, 0, 0},
            {0x02, 0, 0, 0},
            {0x04, 0, 0, 0},
            {0x08, 0, 0, 0},
            {0x10, 0, 0, 0},
            {0x20, 0, 0, 0},
            {0x40, 0, 0, 0},
            {0x80, 0, 0, 0},
            {0x1b, 0, 0, 0},
            {0x36, 0, 0, 0}
    };

    public static void main(String[] args) {
        String strKey = "Super Duper Key!";
        String strText = "Just encrypt me!";

        int[][] key = new int[4][4];
        int[][] state = new int[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                key[j][i] = strKey.charAt(i * 4 + j);
                state[j][i] = strText.charAt(i * 4 + j);
            }
        }

        System.out.println("Ключ:\n    " + strKey);
        System.out.println("Ключ в HEX:");
        printAsHex(key);
        System.out.println("Исходный текст:\n    " + strText);
        System.out.println("Исходный текст в HEX:");
        printAsHex(state);

        state = encryptAES(key, state);
        System.out.println("Результат шифрования: " + convertHexToString(state));
        System.out.println("Результат шифрования в HEX: ");
        printAsHex(state);

        state = decryptAES(key, state);
        System.out.println("Результат расшифрования: " + convertHexToString(state));
        System.out.println("Результат расшифрования в HEX: ");
        printAsHex(state);
    }

    private static String convertHexToString(int[][] state) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i ++) {
            for (int j = 0; j < 4; j ++) {
                sb.append((char)state[j][i]);
            }
        }
        return sb.toString();
    }

    private static int[][] decryptAES(int[][] key, int[][] state) {
        int[][] w = keyExpansion(key);
        int[][] roundKey = getRK(w, 40);
        System.out.println("Расширенный ключ:");
        for (int i = 0; i < 44; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(String.format("%02x ", w[j][i]));
            }
        }
        System.out.println();
        System.out.println("Раунд 0");

        state = addRoundKey(state, roundKey);
        printAsHex(state);
        for (int i = 9; i > 0; i--) {
            System.out.println("Раунд " + i);

            System.out.println("InvShiftRows:");
            state = invShiftRows(state);
            printAsHex(state);

            System.out.println("InvSubBytes:");
            state = invSubBytes(state);
            printAsHex(state);

            System.out.println("AddRoundKey:");
            state = addRoundKey(state, getRK(w, i * 4));
            printAsHex(state);

            System.out.println("InvMixColumns:");
            state = invMixColumns(state);
            printAsHex(state);
        }
        System.out.println("Раунд 10");

        System.out.println("InvShiftRows:");
        state = invShiftRows(state);
        printAsHex(state);

        System.out.println("InvSubBytes:");
        state = invSubBytes(state);
        printAsHex(state);

        System.out.println("AddRoundKey:");
        state = addRoundKey(state, getRK(w, 0));
        printAsHex(state);
        return state;
    }

    private static int[][] encryptAES(int[][] key, int[][] state) {
        int[][] w = keyExpansion(key);
        int[][] roundKey = getRK(w, 0);
        System.out.println("Расширенный ключ:");
        for (int i = 0; i < 44; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(String.format("%02x ", w[j][i]));
            }
        }
        System.out.println();
        System.out.println("Раунд 0");

        state = addRoundKey(state, roundKey);
        printAsHex(state);
        for (int i = 1; i < 10; i++) {
            System.out.println("Раунд " + i);

            System.out.println("SubBytes:");
            state = subBytes(state);
            printAsHex(state);

            System.out.println("ShiftRows:");
            state = shiftRows(state);
            printAsHex(state);

            System.out.println("MixColumns:");
            state = mixColumns(state);
            printAsHex(state);

            System.out.println("AddRoundKey:");
            state = addRoundKey(state, getRK(w, i * 4));
            printAsHex(state);
        }
        System.out.println("Раунд 10");

        System.out.println("SubBytes:");
        state = subBytes(state);
        printAsHex(state);

        System.out.println("ShiftRows:");
        state = shiftRows(state);
        printAsHex(state);

        System.out.println("AddRoundKey:");
        state = addRoundKey(state, getRK(w, 40));
        printAsHex(state);
        return state;
    }

    private static int[][] getRK(int[][] w, int shift) {
        int[][] rk = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                rk[i][j] = w[i][shift + j];
            }
        }
        return rk;
    }

    private static void printAsHex(int[][] block) {
        for (int i = 0; i < 4; i++) {
            System.out.print("    ");
            for (int j = 0; j < 4; j++) {
                System.out.printf("%02x ", block[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    public static int[][] keyExpansion(int[][] key) {
        int[][] w = new int[4][44];

        for (int i = 0; i < 4; i++) {
            w[0][i] = key[0][i];
            w[1][i] = key[1][i];
            w[2][i] = key[2][i];
            w[3][i] = key[3][i];
        }

        for (int i = 4; i < 44; i++) {
            int[] wi1 = new int[]{w[0][i - 1], w[1][i - 1], w[2][i - 1], w[3][i - 1]};
            int[] wi4 = new int[]{w[0][i - 4], w[1][i - 4], w[2][i - 4], w[3][i - 4]};
            int[] tmp;
            if (i % 4 == 0) {
                tmp = xorBytes(wi4, xorBytes(subBytes4(rotByte(wi1)), rcon[i / 4]));
            } else {
                tmp = xorBytes(wi4, wi1);
            }
            w[0][i] = tmp[0];
            w[1][i] = tmp[1];
            w[2][i] = tmp[2];
            w[3][i] = tmp[3];
        }
        return w;
    }

    private static int[] rotByte(int[] block) {
        int[] res = new int[block.length];
        for (int i = 0; i < block.length; i++) {
            res[i] = block[(i + 1) % block.length];
        }
        return res;
    }

    public static int[][] addRoundKey(int[][] block, int[][] rk) {
        int[][] res = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                res[i][j] = block[i][j] ^ rk[i][j];
            }
        }
        return res;
    }

    public static int[][] subBytes(int[][] block) {
        int[][] res = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                res[i][j] = sBox[block[i][j] / 16][block[i][j] % 16];
            }
        }
        return res;
    }

    public static int[][] invSubBytes(int[][] block) {
        int[][] res = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                res[i][j] = invSBox[block[i][j] / 16][block[i][j] % 16];
            }
        }
        return res;
    }

    public static int[] subBytes4(int[] block) {
        int[] res = new int[4];
        for (int i = 0; i < 4; i++) {
            res[i] = sBox[block[i] / 16][block[i] % 16];
        }
        return res;
    }

    public static int[][] shiftRows(int[][] block) {
        int[][] res = new int[4][4];
        for (int i = 0; i < 4; i++) {
            res[0][i] = block[0][(i) % 4];
            res[1][i] = block[1][(1 + i) % 4];
            res[2][i] = block[2][(2 + i) % 4];
            res[3][i] = block[3][(3 + i) % 4];
        }
        return res;
    }

    public static int[][] invShiftRows(int[][] block) {
        int[][] res = new int[4][4];
        for (int i = 0; i < 4; i++) {
            res[0][i] = block[0][(i) % 4];
            res[1][i] = block[1][(3 + i) % 4];
            res[2][i] = block[2][(2 + i) % 4];
            res[3][i] = block[3][(1 + i) % 4];
        }
        return res;
    }

    public static int[][] mixColumns(int[][] block) {
        int[][] res = new int[4][4];
        for (int i = 0; i < 4; i++) {
            res[0][i] = multiply(2, block[0][i]) ^ multiply(3, block[1][i]) ^ block[2][i] ^ block[3][i];
            res[1][i] = block[0][i] ^ multiply(2, block[1][i]) ^ multiply(3, block[2][i]) ^ block[3][i];
            res[2][i] = block[0][i] ^ block[1][i] ^ multiply(2, block[2][i]) ^ multiply(3, block[3][i]);
            res[3][i] = multiply(3, block[0][i]) ^ block[1][i] ^ block[2][i] ^ multiply(2, block[3][i]);
        }
        return res;
    }

    public static int[][] invMixColumns(int[][] block) {
        int[][] res = new int[4][4];
        for (int i = 0; i < 4; i++) {
            res[0][i] = multiply(0x0e, block[0][i]) ^ multiply(0x0b, block[1][i]) ^ multiply(0x0d, block[2][i]) ^ multiply(0x09, block[3][i]);
            res[1][i] = multiply(0x09, block[0][i]) ^ multiply(0x0e, block[1][i]) ^ multiply(0x0b, block[2][i]) ^ multiply(0x0d, block[3][i]);
            res[2][i] = multiply(0x0d, block[0][i]) ^ multiply(0x09, block[1][i]) ^ multiply(0x0e, block[2][i]) ^ multiply(0x0b, block[3][i]);
            res[3][i] = multiply(0x0b, block[0][i]) ^ multiply(0x0d, block[1][i]) ^ multiply(0x09, block[2][i]) ^ multiply(0x0e, block[3][i]);
        }
        return res;
    }

    public static int/*%256*/ multiply(int a, int b) {
        int p = 0;
        for (int i = 0; i < 8; i++) {
            if ((b & 1) == 1) {
                p ^= a;
                p %= 256;
            }
            b >>= 1;
            b %= 256;
            int lBit = a & 128;
            lBit %= 256;
            a <<= 1;
            a %= 256;
            if (lBit == 128) {
                a ^= 27; //256 + 27
                a %= 256;
            }
        }
        return p;
    }

    public static int[] xorBytes(int[] a, int[] b) {
        int[] res = new int[a.length];
        for (int i = 0; i < a.length; i++) {
            res[i] = a[i] ^ b[i];
        }
        return res;
    }
}
