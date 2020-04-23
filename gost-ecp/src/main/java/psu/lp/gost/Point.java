package psu.lp.gost;

import java.math.BigInteger;

public class Point {

    public BigInteger x;
    public BigInteger y;
    public boolean is_O;

    public Point(BigInteger x, BigInteger y) {
        this.x = x;
        this.y = y;
        is_O = false;
    }

    public Point(boolean is_O) {
        x = new BigInteger("0");
        y = new BigInteger("0");
        this.is_O = is_O;
    }

    public Point(Point point) {
        x = point.x;
        y = point.y;
        is_O = point.is_O;
    }

    public BigInteger getX() {
        return x;
    }

    public void setX(BigInteger x) {
        this.x = x;
    }

    public BigInteger getY() {
        return y;
    }

    public void setY(BigInteger y) {
        this.y = y;
    }

    public boolean isIs_O() {
        return is_O;
    }

    public void setIs_O(boolean is_O) {
        this.is_O = is_O;
    }

    @Override
    public String toString() {
        if (is_O)
            return "O";
        else
            return "(" + x.toString() + "; " + y.toString() + ")";
    }
}
