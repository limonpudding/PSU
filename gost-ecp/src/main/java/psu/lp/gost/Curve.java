package psu.lp.gost;

import psu.lp.Stribog;

import java.math.BigInteger;
import java.util.BitSet;
import java.util.Random;

public class Curve {

    private BigInteger p, a, b;
    private BigInteger q; //порядок циклической подгруппы
    private Point P; //генератор циклической подгруппы
    private BigInteger d; //ключ подписи, 0 < d < q
    private Point Q; //ключ проверки подписи, dP = Q
    private Stribog stribog = new Stribog();

    /**
     * переменные для удобства
     */
    private final static BigInteger zero = new BigInteger("0");
    private final static BigInteger one = new BigInteger("1");
    private final static BigInteger two = new BigInteger("2");
    private final static BigInteger three = new BigInteger("3");

    public Curve() {
        p = new BigInteger("3623986102229003635907788753683874306021320925534678605086546150450856166624002482588482022271496854025090823603058735163734263822371964987228582907372403");
        a = new BigInteger("7");
        b = new BigInteger("1518655069210828534508950034714043154928747527740206436194018823352809982443793732829756914785974674866041605397883677596626326413990136959047435811826396");
        q = new BigInteger("3623986102229003635907788753683874306021320925534678605086546150450856166623969164898305032863068499961404079437936585455865192212970734808812618120619743");
        BigInteger Px, Py;
        Px = new BigInteger("1928356944067022849399309401243137598997786635459507974357075491307766592685835441065557681003184874819658004903212332884252335830250729527632383493573274");
        Py = new BigInteger("2288728693371972859970012155529478416353562327329506180314497425931102860301572814141997072271708807066593850650334152381857347798885864807605098724013854");
        P = new Point(Px, Py);
        d = nextBigInteger(q.subtract(one));
        Q = generatePoint(d);
    }

    public Curve(BigInteger p, BigInteger a, BigInteger b, BigInteger q, Point P) {
        this.p = p;
        this.a = a;
        this.b = b;
        this.q = q;
        this.P = P;
        this.d = nextBigInteger(q.subtract(one));
        this.Q = generatePoint(d);
    }

    public Point generatePoint(BigInteger d) {
        Point q = Mult(P, d);
        return q;
    }

    public String sign(String text) {
        int[] hash = stribog.getHash(text);
        BigInteger a = Stribog.hashToBigInteger(hash);
        BigInteger e = a.mod(q);
        if (e.equals(zero))
            e = one;
        BigInteger r = zero;
        BigInteger s = zero;
        BigInteger k = zero;
        k = nextBigInteger(q.subtract(one));
        while (r.equals(zero) && s.equals(zero)) {
            Point C = Mult(P, k);
            r = C.getX().mod(q);
            s = ((r.multiply(d)).add(k.multiply(e))).mod(q);
        }

        return r.toString() + ":" + s.toString();
    }

    public boolean verify(String text, String sign) {
        String[] rs = sign.split(":");

        BigInteger r = new BigInteger(rs[0]);
        BigInteger s = new BigInteger(rs[1]);
        if ((r.compareTo(zero) < 1) || (s.compareTo(zero) < 1) || (r.compareTo(q) > -1) || (s.compareTo(q) > -1))
            return false;

        int[] hash = new Stribog().getHash(text);
        BigInteger a = Stribog.hashToBigInteger(hash);
        BigInteger e = a.mod(q);
        if (e.equals(zero))
            e = one;

        BigInteger v = e.modInverse(q);
        BigInteger z1 = (s.multiply(v)).mod(q);
        BigInteger z2 = (r.negate().multiply(v)).mod(q);


        Point C = Sum(Mult(P, z1), Mult(Q, z2));
        BigInteger RR = C.getX().mod(q);

        return RR.equals(r);
    }

    // region Операции работы с кривой
    private Point Sum(Point p1, Point p2) {
        if (p1.is_O) {
            return p2;
        } else if (p2.is_O) {
            return p1;
        }

        boolean is_O = false;

        BigInteger tg = zero;
        if (p1.getX().equals(p2.getX()) && p1.getY().equals(p2.getY())) {
            BigInteger value = (p1.getY().multiply(two)).mod(p);
            if (value.equals(zero))
                is_O = true;
            else
                tg = ((p1.getX().multiply(p1.getX()).multiply(three).add(a)).multiply(value.modInverse(p)).mod(p));
        } else {
            BigInteger value = (p2.getX().subtract(p1.getX())).mod(p);
            if (value.equals(zero))
                is_O = true;
            else
                tg = ((p2.getY().subtract(p1.getY())).multiply(value.modInverse(p)).mod(p));
        }

        BigInteger x, y = zero;
        if (is_O) {
            return new Point(true);
        } else {
            x = (tg.multiply(tg).subtract(p1.getX().subtract(p2.getX())).mod(p));
            y = (tg.multiply(p1.getX().subtract(x)).subtract(p1.getY())).mod(p);
            return new Point(x, y);
        }
    }

    private Point Mult(Point point, BigInteger n) {
        BitSet number = BitSet.valueOf(n.toByteArray());

        Point power = new Point(point);

        Point result = number.get(0) ? new Point(power) : new Point(true);

        for (int i = 1; i < number.length(); i++) {
            power = Sum(power, power);
            if (number.get(i)) {
                result = Sum(result, power);
            }
        }

        return result;
    }

    public static BigInteger nextBigInteger(BigInteger q) {
        Random r = new Random();
        BigInteger result = new BigInteger(q.bitLength() - 1, r); // (2^4-1) = 15 is the maximum value
        return result.equals(zero) ? one : result;
    }
}