package psu.lp;

import java.util.Arrays;

public class Stribog256 extends Stribog {

    Stribog256() {
        super();
        for (int i = 0; i < 64; i++) {
            iv[i] = 0x01;
        }
    }

    @Override
    public int[] getHash(String message) {
        int[] hash512 = getHashX(message.chars().toArray());
        return Arrays.copyOfRange(hash512, 0, 32);
    }
}
