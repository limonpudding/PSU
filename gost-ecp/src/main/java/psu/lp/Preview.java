package psu.lp;

import psu.lp.gost.Curve;

public class Preview {

    public static void main(String[] args) {
        Curve curve = new Curve();
        String message = "Очень важное сообщение";
        String testSign = curve.sign(message);
        if (curve.verify(message, testSign)) {
            System.out.println("Подпись верна!");
        } else {
            System.out.println("Подпись неверна!");
        }
    }
}
