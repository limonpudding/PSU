package psu.lp.des;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class DES {

    private static int[] initIPTable = new int[]{
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17,  9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };

    private static int[] finalIPTable = new int[]{
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41,  9, 49, 17, 57, 25
    };

    private static int[] expandE = new int[]{
            32,  1,  2,  3,  4,  5,
            4,  5,  6,  7,  8,  9,
            8,  9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32,  1
    };

    private static int[] initCD = new int[]{
            57, 49, 41, 33, 25, 17,  9,
            1, 58, 50, 42, 34, 26, 18,
            10,  2, 59, 51, 43, 35, 27,
            19, 11,  3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14,  6, 61, 53, 45, 37, 29,
            21, 13,  5, 28, 20, 12,  4
    };

    private static int[] roundKeyPermut = new int[]{
            14, 17, 11, 24,  1,  5,  3, 28,
            15,  6, 21, 10, 23, 19, 12,  4,
            26,  8, 16,  7, 27, 20, 13,  2,
            41, 52, 31, 37, 47, 55, 30, 40,
            51, 45, 33, 48, 44, 49, 39, 56,
            34, 53, 46, 42, 50, 36, 29, 32
    };

    private static byte[][][] S = new byte[][][]{
            {
                    {14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7},
                    {0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8},
                    {4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0},
                    {15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13}
            },
            {
                    {15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10},
                    {3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5},
                    {0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15},
                    {13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9}
            },
            {
                    {10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8},
                    {13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1},
                    {13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7},
                    {1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12}
            },
            {
                    {7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15},
                    {13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9},
                    {10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4},
                    {3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14}
            },
            {
                    {2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9},
                    {14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6},
                    {4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14},
                    {11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3}
            },
            {
                    {12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11},
                    {10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8},
                    {9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6},
                    {4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13}
            },
            {
                    {4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1},
                    {13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6},
                    {1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2},
                    {6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12}
            },
            {
                    {13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7},
                    {1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2},
                    {7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8},
                    {2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11}
            }
    };

    public static byte[] P = new byte[]{
            16, 7, 20, 21, 29, 12, 28, 17,
            1, 15, 23, 26, 5, 18, 31, 10,
            2, 8, 24, 14, 32, 27, 3, 9,
            19, 13, 30, 6, 22, 11, 4, 25
    };

    private static byte[] shift = new byte[]{1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1};

    private List<byte[]> keys;

    private String mainKey;

    private int hammingWeight;

    private boolean enableThreadSleep;

    public DES(String mainKey) {
        this.mainKey = mainKey;
        this.hammingWeight = 0;
        this.enableThreadSleep = false;
    }

    public byte[] encryptDES(byte[] binaryWord) {
        generateKeys();
        hammingWeight = 0;
        binaryWord = initIP(binaryWord);
        byte[] L = Arrays.copyOfRange(binaryWord, 0, 32);
        byte[] R = Arrays.copyOfRange(binaryWord, 32, 64);
        byte[] newL;
        byte[] newR;
        for (int i = 0; i < 16; i++) {
            newL = Arrays.copyOf(R, 32);
            newR = xorBytes(roundFunction(R, i), L);
            L = newL;
            R = newR;
        }
        binaryWord = new byte[64];
        System.arraycopy(L, 0, binaryWord, 0, 32);
        System.arraycopy(R, 0, binaryWord, 32, 32);
        binaryWord = finalIP(binaryWord);
        return binaryWord;
    }

    public byte[] decryptDES(byte[] binaryWord) {
        generateKeys();
        hammingWeight = 0;
        binaryWord = initIP(binaryWord);
        byte[] L = Arrays.copyOfRange(binaryWord, 0, 32);
        byte[] R = Arrays.copyOfRange(binaryWord, 32, 64);
        byte[] newL;
        byte[] newR;
        for (int i = 15; i >= 0; i--) {
            newR = Arrays.copyOf(L, 32);
            newL = xorBytes(roundFunction(L, i), R);
            L = newL;
            R = newR;
        }
        binaryWord = new byte[64];
        System.arraycopy(L, 0, binaryWord, 0, 32);
        System.arraycopy(R, 0, binaryWord, 32, 32);
        binaryWord = finalIP(binaryWord);
        return binaryWord;
    }

    private static byte[] initIP(byte[] binaryArray) {
        byte[] transposed = new byte[64];
        for (int i = 0; i < 64; i++) {
            transposed[i] = binaryArray[initIPTable[i] - 1];
        }
        return transposed;
    }

    private static byte[] finalIP(byte[] binaryArray) {
        byte[] transposed = new byte[64];
        for (int i = 0; i < 64; i++) {
            transposed[i] = binaryArray[finalIPTable[i] - 1];
        }
        return transposed;
    }

    public static byte[] wordToBinaryAsByteArray(String word) {
        byte[] binary = new byte[word.length() * 8];
        String binaryLetter;
        for (int i = 0; i < word.length(); i++) {
            binaryLetter = getCharAsBinaryString(word, i);
            int pos = 0;
            for (int j = i * 8; j < i * 8 + 8; j++) {
                binary[j] = (byte) Integer.parseInt(String.valueOf(binaryLetter.charAt(pos)));
                pos += 1;
            }
        }
        return binary;
    }

    private static String getCharAsBinaryString(String word, int i) {
        String binaryLetter = Integer.toBinaryString(word.charAt(i));
        binaryLetter = String.format("%0" + (8 - binaryLetter.length()) + "d%s", 0, binaryLetter);
        return binaryLetter;
    }

    private void generateKeys() {
        byte[] binaryKey56 = wordToBinaryAsByteArray(mainKey);
        byte[] binaryKey64 = new byte[64];
        int count = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 7; j++) {
                if (binaryKey56[i*7+j] == (byte)1) {
                    count++;
                }
                binaryKey64[i*8+j] = binaryKey56[i*7+j];
            }
            binaryKey64[(i+1)*8-1] = count % 2 == 0 ? (byte)1 : (byte)0;
            count = 0;
        }

        for (int i = 0; i < 56; i++) {
            binaryKey56[i] = binaryKey64[initCD[i]-1];
        }

        byte[] C = Arrays.copyOfRange(binaryKey56, 0, 28);
        byte[] D = Arrays.copyOfRange(binaryKey56, 28, 56);
        keys = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            byte[] shiftedC = shiftKey(C, shift[i]);
            byte[] shiftedD = shiftKey(D, shift[i]);
            byte[] roundKey56 = new byte[56];
            System.arraycopy(shiftedC, 0, roundKey56, 0, 28);
            System.arraycopy(shiftedD, 0, roundKey56, 28, 28);
            byte[] roundKey48 = new byte[48];
            for (int j = 0; j < 48; j++) {
                roundKey48[j] = roundKey56[roundKeyPermut[j] - 1];
            }
            keys.add(roundKey48);
            C = Arrays.copyOf(shiftedC, 28);
            D = Arrays.copyOf(shiftedD, 28);
        }
    }

    private byte[] shiftKey(byte[] key, byte amount) {
        int keyLength = key.length;
        byte[] newKey = new byte[keyLength];
        for (int i = 0; i < keyLength; i++) {
            newKey[i] = key[(i + amount) % keyLength];
        }
        return newKey;
    }

    public byte[] functionE(byte[] R) {
        byte[] expanded = new byte[48];
        for (int i = 0; i < 48; i++) {
            expanded[i] = R[expandE[i] - 1];
        }
        return expanded;
    }

    private byte[] roundFunction(byte[] R, int iterationNum) {
        byte[] E = functionE(R);
        byte[] roundKey = keys.get(iterationNum);
        byte[] xor = xorBytes(E, roundKey);
        byte[] B = new byte[32];
        byte[] b;
        for (int i = 0; i < 8; i++) {
            b = permutS(Arrays.copyOfRange(xor, i * 6, i * 6 + 6), i);
            System.arraycopy(b, 0, B, i * 4, 4);
        }
        return functionP(B);
    }

    private byte[] functionP(byte[] b) {
        byte[] result = new byte[32];
        try {
            for (int i = 0; i < 32; i++) {
                if (b[P[i] - 1] == 1) {
                    hammingWeight += 1;
                    result[i] = b[P[i] - 1];
                    if (enableThreadSleep) {
                        Thread.sleep(5);
                    }
                }
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public byte[] permutS(byte[] b, int part) {
        int p = Integer.parseInt(Integer.toString(b[0] * 10 + b[5]), 2);
        int q = Integer.parseInt(Integer.toString(b[1] * 1000 + b[2] * 100 + b[3] * 10 + b[4]), 2);
        byte[] s = new byte[4];
        String binary = Integer.toBinaryString(S[part][p][q]);
        while (binary.length() < 4) {
            binary = "0" + binary;
        }
        for (int i = 0; i < 4; i++) {
            s[i] = (byte) Integer.parseInt(String.valueOf(binary.charAt(i)));
        }
        return s;
    }

    public byte[] xorBytes(byte[] a, byte[] b) {
        byte[] result = new byte[a.length];
        for (int i = 0; i < a.length; i++) {
            result[i] = (byte) (a[i] ^ b[i]);
        }
        return result;
    }

    public String getMainKey() {
        return mainKey;
    }

    public void setMainKey(String mainKey) {
        this.mainKey = mainKey;
    }

    public int getHammingWeight() {
        return hammingWeight;
    }

    public boolean isEnableThreadSleep() {
        return enableThreadSleep;
    }

    public void setEnableThreadSleep(boolean enableThreadSleep) {
        this.enableThreadSleep = enableThreadSleep;
    }
}
