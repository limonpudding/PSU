package psu.lp.analyse;

public class Matrix {

    char[] letters;
    double[] probabilities;

    public Matrix() {
        letters = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        this.probabilities = new double[letters.length];
    }

    public Matrix(char[] letters, double[] probabilities) {
        // размер массивов должен совпадать
        this.letters = letters;
        this.probabilities = probabilities;
    }

    public Matrix(double[] probabilities) {
        this.letters = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
        this.probabilities = probabilities;
    }

    public char[] getLetters() {
        return letters;
    }

    public void setLetters(char[] letters) {
        this.letters = letters;
    }

    public double[] getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(double[] probabilities) {
        this.probabilities = probabilities;
    }

    public double getProbability(char letter) {
        int id = getLetterId(letter);
        return this.probabilities[id];
    }

    public char getLetterById(int id) {
        return letters[id];
    }

    public double getProbabilityById(int id) {
        return probabilities[id];
    }

    public void setLetterById(int id, char letter) {
        letters[id] = letter;
    }

    public void setProbabilityById(int id, double probability) {
        probabilities[id] = probability;
    }
    
    public boolean containsLetter(char letter) {
        for (int i = 0; i < this.letters.length; i++) {
            if (this.letters[i] == letter) {
                return true;
            }
        }
        return false;
    }
    
    public int getLetterId(char letter) {
        for (int i = 0; i < this.letters.length; i++) {
            if (this.letters[i] == letter) {
                return i;
            }
        }
        return -1;
    }
    
    public void changeProbabilityAtLetter(double value, char key) {
        int id = getLetterId(key);
        this.probabilities[id] = value;
    }

    public int size() {
        return letters.length;
    }

    public void printToConsole() {
        for (int i = 0; i < letters.length; i++ ) {
            System.out.println("Key: " + getLetterById(i) + " | Value: " + getProbabilityById(i));
        }
    }

    public void removeById(int id) {
        int newSize = size() - 1;
        char[] newLetters = new char[newSize];
        double[] newProbabilities = new double[newSize];
        for (int i = 0; i < newSize; i++) {
            if (i < id) {
                newLetters[i] = letters[i];
                newProbabilities[i] = probabilities[i];
            } else {
                newLetters[i] = letters[i + 1];
                newProbabilities[i] = probabilities[i + 1];
            }
        }
        letters = newLetters;
        probabilities = newProbabilities;
    }

    public void addElement(char letter, double probability) {
        char[] newLetters = new char[letters.length + 1];
        double[] newProbabilities = new double[probabilities.length + 1];
        for (int i = 0; i < size(); i++) {
            newLetters[i] = letters[i];
            newProbabilities[i] = probabilities[i];
        }
        newLetters[size()] = letter;
        newProbabilities[size()] = probability;
        letters = newLetters;
        probabilities = newProbabilities;
    }

    public void removeAll() {
        this.letters = new char[0];
        this.probabilities = new double[0];
    }
}
