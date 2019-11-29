package psu.lp;

public class Preview {

    public static void main(String[] args) {
        String message = "Super vajnoe soobshenye";
        Stribog stribog = new Stribog();
        Stribog256 stribog256 = new Stribog256();
        int[] hash = stribog.getHash(message);
        int[] hash256 = stribog256.getHash(message);
        System.out.println("Исходное сообщение: \'" + message + "\'");
        System.out.println("Результат хэширования функцией Стрибог 512: ");
        printHashAsHex(hash);
        System.out.println();
        System.out.println("Результат хэширования функцией Стрибог 256: ");
        printHashAsHex(hash256);
    }

    public static void printHashAsHex(int[] hash) {
        for (int i = 0; i < hash.length; i++) {
            System.out.printf("%02X ", hash[i]);
        }
        System.out.println();
    }
}
