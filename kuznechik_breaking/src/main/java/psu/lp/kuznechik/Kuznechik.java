package psu.lp.kuznechik;

public class Kuznechik {
    static final int BLOCK_SIZE = 16; // длина блока

    // таблица прямого нелинейного преобразования
    static final byte[] Pi = {
            (byte) 0xFC, (byte) 0xEE, (byte) 0xDD, 0x11, (byte) 0xCF, 0x6E, 0x31, 0x16,
            (byte) 0xFB, (byte) 0xC4, (byte) 0xFA, (byte) 0xDA, 0x23, (byte) 0xC5, 0x04, 0x4D,
            (byte) 0xE9, 0x77, (byte) 0xF0, (byte) 0xDB, (byte) 0x93, 0x2E, (byte) 0x99, (byte) 0xBA,
            0x17, 0x36, (byte) 0xF1, (byte) 0xBB, 0x14, (byte) 0xCD, 0x5F, (byte) 0xC1,
            (byte) 0xF9, 0x18, 0x65, 0x5A, (byte) 0xE2, 0x5C, (byte) 0xEF, 0x21,
            (byte) 0x81, 0x1C, 0x3C, 0x42, (byte) 0x8B, 0x01, (byte) 0x8E, 0x4F,
            0x05, (byte) 0x84, 0x02, (byte) 0xAE, (byte) 0xE3, 0x6A, (byte) 0x8F, (byte) 0xA0,
            0x06, 0x0B, (byte) 0xED, (byte) 0x98, 0x7F, (byte) 0xD4, (byte) 0xD3, 0x1F,
            (byte) 0xEB, 0x34, 0x2C, 0x51, (byte) 0xEA, (byte) 0xC8, 0x48, (byte) 0xAB,
            (byte) 0xF2, 0x2A, 0x68, (byte) 0xA2, (byte) 0xFD, 0x3A, (byte) 0xCE, (byte) 0xCC,
            (byte) 0xB5, 0x70, 0x0E, 0x56, 0x08, 0x0C, 0x76, 0x12,
            (byte) 0xBF, 0x72, 0x13, 0x47, (byte) 0x9C, (byte) 0xB7, 0x5D, (byte) 0x87,
            0x15, (byte) 0xA1, (byte) 0x96, 0x29, 0x10, 0x7B, (byte) 0x9A, (byte) 0xC7,
            (byte) 0xF3, (byte) 0x91, 0x78, 0x6F, (byte) 0x9D, (byte) 0x9E, (byte) 0xB2, (byte) 0xB1,
            0x32, 0x75, 0x19, 0x3D, (byte) 0xFF, 0x35, (byte) 0x8A, 0x7E,
            0x6D, 0x54, (byte) 0xC6, (byte) 0x80, (byte) 0xC3, (byte) 0xBD, 0x0D, 0x57,
            (byte) 0xDF, (byte) 0xF5, 0x24, (byte) 0xA9, 0x3E, (byte) 0xA8, (byte) 0x43, (byte) 0xC9,
            (byte) 0xD7, 0x79, (byte) 0xD6, (byte) 0xF6, 0x7C, 0x22, (byte) 0xB9, 0x03,
            (byte) 0xE0, 0x0F, (byte) 0xEC, (byte) 0xDE, 0x7A, (byte) 0x94, (byte) 0xB0, (byte) 0xBC,
            (byte) 0xDC, (byte) 0xE8, 0x28, 0x50, 0x4E, 0x33, 0x0A, 0x4A,
            (byte) 0xA7, (byte) 0x97, 0x60, 0x73, 0x1E, 0x00, 0x62, 0x44,
            0x1A, (byte) 0xB8, 0x38, (byte) 0x82, 0x64, (byte) 0x9F, 0x26, 0x41,
            (byte) 0xAD, 0x45, 0x46, (byte) 0x92, 0x27, 0x5E, 0x55, 0x2F,
            (byte) 0x8C, (byte) 0xA3, (byte) 0xA5, 0x7D, 0x69, (byte) 0xD5, (byte) 0x95, 0x3B,
            0x07, 0x58, (byte) 0xB3, 0x40, (byte) 0x86, (byte) 0xAC, 0x1D, (byte) 0xF7,
            0x30, 0x37, 0x6B, (byte) 0xE4, (byte) 0x88, (byte) 0xD9, (byte) 0xE7, (byte) 0x89,
            (byte) 0xE1, 0x1B, (byte) 0x83, 0x49, 0x4C, 0x3F, (byte) 0xF8, (byte) 0xFE,
            (byte) 0x8D, 0x53, (byte) 0xAA, (byte) 0x90, (byte) 0xCA, (byte) 0xD8, (byte) 0x85, 0x61,
            0x20, 0x71, 0x67, (byte) 0xA4, 0x2D, 0x2B, 0x09, 0x5B,
            (byte) 0xCB, (byte) 0x9B, 0x25, (byte) 0xD0, (byte) 0xBE, (byte) 0xE5, 0x6C, 0x52,
            0x59, (byte) 0xA6, 0x74, (byte) 0xD2, (byte) 0xE6, (byte) 0xF4, (byte) 0xB4, (byte) 0xC0,
            (byte) 0xD1, 0x66, (byte) 0xAF, (byte) 0xC2, 0x39, 0x4B, 0x63, (byte) 0xB6
    };

    // таблица обратного нелинейного преобразования
    static final byte[] reverse_Pi = {
            (byte) 0xA5, 0x2D, 0x32, (byte) 0x8F, 0x0E, 0x30, 0x38, (byte) 0xC0,
            0x54, (byte) 0xE6, (byte) 0x9E, 0x39, 0x55, 0x7E, 0x52, (byte) 0x91,
            0x64, 0x03, 0x57, 0x5A, 0x1C, 0x60, 0x07, 0x18,
            0x21, 0x72, (byte) 0xA8, (byte) 0xD1, 0x29, (byte) 0xC6, (byte) 0xA4, 0x3F,
            (byte) 0xE0, 0x27, (byte) 0x8D, 0x0C, (byte) 0x82, (byte) 0xEA, (byte) 0xAE, (byte) 0xB4,
            (byte) 0x9A, 0x63, 0x49, (byte) 0xE5, 0x42, (byte) 0xE4, 0x15, (byte) 0xB7,
            (byte) 0xC8, 0x06, 0x70, (byte) 0x9D, 0x41, 0x75, 0x19, (byte) 0xC9,
            (byte) 0xAA, (byte) 0xFC, 0x4D, (byte) 0xBF, 0x2A, 0x73, (byte) 0x84, (byte) 0xD5,
            (byte) 0xC3, (byte) 0xAF, 0x2B, (byte) 0x86, (byte) 0xA7, (byte) 0xB1, (byte) 0xB2, 0x5B,
            0x46, (byte) 0xD3, (byte) 0x9F, (byte) 0xFD, (byte) 0xD4, 0x0F, (byte) 0x9C, 0x2F,
            (byte) 0x9B, 0x43, (byte) 0xEF, (byte) 0xD9, 0x79, (byte) 0xB6, 0x53, 0x7F,
            (byte) 0xC1, (byte) 0xF0, 0x23, (byte) 0xE7, 0x25, 0x5E, (byte) 0xB5, 0x1E,
            (byte) 0xA2, (byte) 0xDF, (byte) 0xA6, (byte) 0xFE, (byte) 0xAC, 0x22, (byte) 0xF9, (byte) 0xE2,
            0x4A, (byte) 0xBC, 0x35, (byte) 0xCA, (byte) 0xEE, 0x78, 0x05, 0x6B,
            0x51, (byte) 0xE1, 0x59, (byte) 0xA3, (byte) 0xF2, 0x71, 0x56, 0x11,
            0x6A, (byte) 0x89, (byte) 0x94, 0x65, (byte) 0x8C, (byte) 0xBB, 0x77, 0x3C,
            0x7B, 0x28, (byte) 0xAB, (byte) 0xD2, 0x31, (byte) 0xDE, (byte) 0xC4, 0x5F,
            (byte) 0xCC, (byte) 0xCF, 0x76, 0x2C, (byte) 0xB8, (byte) 0xD8, 0x2E, 0x36,
            (byte) 0xDB, 0x69, (byte) 0xB3, 0x14, (byte) 0x95, (byte) 0xBE, 0x62, (byte) 0xA1,
            0x3B, 0x16, 0x66, (byte) 0xE9, 0x5C, 0x6C, 0x6D, (byte) 0xAD,
            0x37, 0x61, 0x4B, (byte) 0xB9, (byte) 0xE3, (byte) 0xBA, (byte) 0xF1, (byte) 0xA0,
            (byte) 0x85, (byte) 0x83, (byte) 0xDA, 0x47, (byte) 0xC5, (byte) 0xB0, 0x33, (byte) 0xFA,
            (byte) 0x96, 0x6F, 0x6E, (byte) 0xC2, (byte) 0xF6, 0x50, (byte) 0xFF, 0x5D,
            (byte) 0xA9, (byte) 0x8E, 0x17, 0x1B, (byte) 0x97, 0x7D, (byte) 0xEC, 0x58,
            (byte) 0xF7, 0x1F, (byte) 0xFB, 0x7C, 0x09, 0x0D, 0x7A, 0x67,
            0x45, (byte) 0x87, (byte) 0xDC, (byte) 0xE8, 0x4F, 0x1D, 0x4E, 0x04,
            (byte) 0xEB, (byte) 0xF8, (byte) 0xF3, 0x3E, 0x3D, (byte) 0xBD, (byte) 0x8A, (byte) 0x88,
            (byte) 0xDD, (byte) 0xCD, 0x0B, 0x13, (byte) 0x98, 0x02, (byte) 0x93, (byte) 0x80,
            (byte) 0x90, (byte) 0xD0, 0x24, 0x34, (byte) 0xCB, (byte) 0xED, (byte) 0xF4, (byte) 0xCE,
            (byte) 0x99, 0x10, 0x44, 0x40, (byte) 0x92, 0x3A, 0x01, 0x26,
            0x12, 0x1A, 0x48, 0x68, (byte) 0xF5, (byte) 0x81, (byte) 0x8B, (byte) 0xC7,
            (byte) 0xD6, 0x20, 0x0A, 0x08, 0x00, 0x4C, (byte) 0xD7, 0x74
    };

    // вектор линейного преобразования
    static final byte[] l_vec = {
            1, (byte) 148, 32, (byte) 133, 16, (byte) 194, (byte) 192, 1,
            (byte) 251, 1, (byte) 192, (byte) 194, 16, (byte) 133, 32, (byte) 148
    };

    // массив для хранения констант
    static byte[][] iter_C = new byte[32][16];
    // массив для хранения ключей
    static byte[][] iterKey = new byte[10][64];

    // функция X
    static public byte[] kuzX(byte[] a, byte[] b)
    {
        int i;
        byte[] c = new byte[BLOCK_SIZE];
        for (i = 0; i < BLOCK_SIZE; i++)
            c[i] = (byte) (a[i] ^ b[i]);
        return c;
    }

    // Функция S
    static public byte[] kuzS(byte[] in_data)
    {
        int i;
        byte[] out_data = new byte[in_data.length];
        for (i = 0; i < BLOCK_SIZE; i++)
        {
            int data = in_data[i];
            if(data < 0)
            {
                data = data + 256;
            }
            out_data[i] = Pi[data];
        }
        return out_data;
    }

    // умножение в поле Галуа
    static private byte kuzGFMul(byte a, byte b)
    {
        byte c = 0;
        byte hi_bit;
        int i;
        for (i = 0; i < 8; i++)
        {
            if ((b & 1) == 1)
                c ^= a;
            hi_bit =  (byte) (a & 0x80);
            a <<= 1;
            if (hi_bit < 0)
                a ^= 0xc3; //полином  x^8+x^7+x^6+x+1
            b >>= 1;
        }
        return c;
    }
    // функция R сдвигает данные и реализует уравнение, представленное для расчета L-функции
    static private byte[] kuzR(byte[] state)
    {
        int i;
        byte a_15 = 0;
        byte[] internal = new byte[16];
        for (i = 15; i >= 0; i--)
        {
            if(i == 0)
                internal[15] = state[i];
            else
                internal[i - 1] = state[i];
            a_15 ^= kuzGFMul(state[i], l_vec[i]);
        }
        internal[15] = a_15;
        return internal;
    }
    static public byte[] kuzL(byte[] in_data)
    {
        int i;
        byte[] out_data = new byte[in_data.length];
        byte[] internal = in_data;
        for (i = 0; i < 16; i++)
        {
            internal = kuzR(internal);
        }
        out_data = internal;
        return out_data;
    }

    // функция S^(-1)
    static private byte[] kuzReverseS(byte[] in_data)
    {
        int i;
        byte[] out_data = new byte[in_data.length];
        for (i = 0; i < BLOCK_SIZE; i++)
        {
            int data = in_data[i];
            if(data < 0)
            {
                data = data + 256;
            }
            out_data[i] = reverse_Pi[data];
        }
        return out_data;
    }
    static private byte[] kuzReverseR(byte[] state)
    {
        int i;
        byte a_0;
        a_0 = state[15];
        byte[] internal = new byte[16];
        for (i = 1; i < 16; i++)
        {
            internal[i] = state[i - 1];
            a_0 ^= kuzGFMul(internal[i], l_vec[i]);
        }
        internal[0] = a_0;
        return internal;
    }
    static private byte[] kuzReverseL(byte[] in_data)
    {
        int i;
        byte[] out_data = new byte[in_data.length];
        byte[] internal;
        internal = in_data;
        for (i = 0; i < 16; i++)
            internal = kuzReverseR(internal);
        out_data = internal;
        return out_data;
    }
    // функция расчета констант
    static private void getKuzC()
    {
        int i;
        byte[][] iter_num = new byte[32][16];
        for (i = 0; i < 32; i++)
        {
            for(int j = 0; j < BLOCK_SIZE; j++)
                iter_num[i][j] = 0;
            iter_num[i][0] = (byte) (i+1);
        }
        for (i = 0; i < 32; i++)
        {
            iter_C[i] = kuzL(iter_num[i]);
        }
    }
    // функция, выполняющая преобразования ячейки Фейстеля
    static private byte[][] kuzF(byte[] in_key_1, byte[] in_key_2, byte[] iter_const)
    {
        byte[] internal;
        byte[] out_key_2 = in_key_1;
        internal = kuzX(in_key_1, iter_const);
        internal = kuzS(internal);
        internal = kuzL(internal);
        byte[] out_key_1 = kuzX(internal, in_key_2);
        byte key[][] = new byte[2][];
        key[0] = out_key_1;
        key[1] = out_key_2;
        return key;
    }
    // функция расчета раундовых ключей
    public byte[][] expandKey(byte[] key_1, byte[] key_2)
    {
        int i;

        byte[][] iter12 = new byte[2][];
        byte[][] iter34 = new byte[2][];
        getKuzC();
        iterKey[0] = key_1;
        iterKey[1] = key_2;
        iter12[0] = key_1;
        iter12[1] = key_2;
        for (i = 0; i < 4; i++)
        {
            iter34 = kuzF(iter12[0], iter12[1], iter_C[8 * i]);
            iter12 = kuzF(iter34[0], iter34[1], iter_C[1 + 8 * i]);
            iter34 = kuzF(iter12[0], iter12[1], iter_C[2 + 8 * i]);
            iter12 = kuzF(iter34[0], iter34[1], iter_C[3 + 8 * i]);
            iter34 = kuzF(iter12[0], iter12[1], iter_C[4 + 8 * i]);
            iter12 = kuzF(iter34[0], iter34[1], iter_C[5 + 8 * i]);
            iter34 = kuzF(iter12[0], iter12[1], iter_C[6 + 8 * i]);
            iter12 = kuzF(iter34[0], iter34[1], iter_C[7 + 8 * i]);

            iterKey[2 * i + 2] = iter12[0];
            iterKey[2 * i + 3] = iter12[1];
        }
        return iterKey;
    }
    // функция шифрования блока
    public byte[] encrypt(byte[] blk)
    {
        int i;
        byte[] out_blk = new byte[BLOCK_SIZE];
        out_blk = blk;
        for(i = 0; i < 9; i++)
        {
            out_blk = kuzX(iterKey[i], out_blk);
            out_blk = kuzS(out_blk);
            out_blk = kuzL(out_blk);
        }
        out_blk = kuzX(out_blk, iterKey[9]);
        return out_blk;
    }
    //функция расшифрования блока
    public byte[] decrypt(byte[] blk)
    {
        int i;
        byte[] out_blk = new byte[BLOCK_SIZE];
        out_blk = blk;

        out_blk = kuzX(out_blk, iterKey[9]);
        for(i = 8; i >= 0; i--)
        {
            out_blk = kuzReverseL(out_blk);
            out_blk = kuzReverseS(out_blk);
            out_blk = kuzX(iterKey[i], out_blk);
        }
        return out_blk;
    }
}