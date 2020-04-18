package psu.lp.des;

import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

import java.util.ArrayList;
import java.util.List;

public class HW {

    public static void main(String[] args) {
        List<byte[]> binaryTexts = generateTexts();
        System.out.println("Тексты готовы");
        String mainKey = "bad_key"; // Оригинальный ключ
        // массив ключей для проверки:
        String[] checkKeys = new String[]{"bestkey", "bad_key", "wronggg", "ohmygod", "aestkey", "cestkey"};
        double[] time = new double[binaryTexts.size()];
        int k = 0;
        DES des = new DES(mainKey);
        des.setEnableThreadSleep(true); // Включается задержка Thread.sleep()
        // Далее вычисляется время выполнения алгоритма с верным ключом для каждого текста
        for (byte[] binaryText: binaryTexts) {
            long startMillis = System.currentTimeMillis();
            des.encryptDES(binaryText);
            long endMillis = System.currentTimeMillis();
            time[k++] = endMillis - startMillis;
        }

        PearsonsCorrelation pearsonsCorrelation = new PearsonsCorrelation();
        double[] cor = new double[checkKeys.length];
        double maxCor = 0;
        int maxCorId = 0;
        des.setEnableThreadSleep(false); // Выключается задержка Thread.sleep()
        // Далее по очереди проверяются все ключи
        for (byte i = 0; i < checkKeys.length; i++) {
            des.setMainKey(checkKeys[i]);

            double[] hwVars = new double[binaryTexts.size()];
            k = 0;
            // Для каждого текста вычисляется расстояние Хемминга для текущего ключа
            for (byte[] binaryText: binaryTexts) {
                des.encryptDES(binaryText);
                hwVars[k++] = des.getHammingWeight();
            }

            cor[i] = pearsonsCorrelation.correlation(time, hwVars); // Вычисляется коэффициент корреляции Пирсона для текущего ключа
            if (maxCor < cor[i]) {
                maxCor = cor[i];
                maxCorId = i;
            }
        }

        System.out.println("\nВерное значение ключа: " + mainKey);
        System.out.println("Наибольший коэффициент корреляции: " + maxCor);
        System.out.println("Найденный ключ: " + checkKeys[maxCorId]);
        System.out.println("\nВсе варианты: ");
        for (int i = 0; i < checkKeys.length; i++) {
            System.out.println("Ключ: " + checkKeys[i] + " | Коэффициент: " + cor[i]);
        }
    }

    // Метод для генерации 100 текстов в бинарной форме
    private static List<byte[]> generateTexts() {
        List<byte[]> result = new ArrayList<>();
        for (int i = 0; i < Integer.MAX_VALUE && i >= 0; i+=Integer.MAX_VALUE/100) {
            byte[] value = new byte[64];
            String str = String.format("%64s",Integer.toBinaryString(i)).replace(' ', '0');
            for (int j = 0; j < 32; j++) {
                value[j] = (byte) (str.charAt(j+32) - '0');
            }
            for (int j = 32; j < 64; j++) {
                value[j] = (byte) (str.charAt(j) - '0');
            }
            result.add(value);
        }
        return result;
    }
}

