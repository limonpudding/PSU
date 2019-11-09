package psu.lp.ellipticcurve;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        ArrayList<ZPoint> points = new ArrayList<>();
        int module = 41;
        int right = 0;
        int left = 0;
        for (int x = 0; x < module; x++) {
            right = x * x * x + x + 3;
            right = module(right, module);
            for (int y = 0; y < module; y++) {
                left = y * y;
                left = module(left, module);
                if (left == right) {
                    ZPoint zpoint = new ZPoint(x, y);
                    points.add(zpoint);
                    System.out.println("Точка " + points.size() + ": " + zpoint.toString());
                }
            }
        }
        int p = points.size() + 1;
        System.out.println("Порядок = " + p);
        int res = -1;
        for (int i = 0; i < p - 1; i++) {
            System.out.print("Для точки " + points.get(i).toString() + " - ");
            HashSet<ZPoint> tmp = new HashSet<>();
            ZPoint t1 = points.get(i);
            ZPoint t2 = points.get(i);
            tmp.add(t1);
            boolean flag = true;
            do {
                int l = 0;
                int div;
                if (t1.equals(t2)) {
                    div = 2 * t1.getY();
                    if (div == 0) {
                        flag = false;
                    } else {
                        l = (3 * t1.getX() * t1.getX() + 1) * inverse(div, module);
                    }
                } else {
                    div = t2.getX() - t1.getX();
                    if (div == 0) {
                        flag = false;
                    } else {
                        l = (t2.getY() - t1.getY()) * inverse(div, module);
                    }
                }
                l = module(l, module);
                if (flag) {
                    int x3 = module(l * l - t1.getX() - t2.getX(), module);
                    int y3 = module(l * (t1.getX() - x3) - t1.getY(), module);
                    t1 = new ZPoint(x3, y3);
                    tmp.add(t1);
                }
            } while (flag);
            System.out.println("порядок " + (tmp.size() + 1));
            if (tmp.size() + 1 == 13) {
                res = i;
            }
        }
    }

    static int module(int x, int m) {
        int res = x % m;
        if (res < 0) {
            return m + res;
        }
        return res;
    }

    static boolean hasPoint(Set<ZPoint> points, ZPoint z) {
        for (ZPoint point : points) {
            if (point.equals(z)) {
                return true;
            }
        }
        return false;
    }

    static int inverse(int x, int m) {
        x = module(x, m);
        for (int i = 1; i < m; i++) {
            if (module(x * i, m) == 1) {
                return i;
            }
        }
        return -1;
    }
}
