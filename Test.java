import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Test {

    static Random random = new Random();
    public static void main(String[] args) throws IOException {
        FileWriter fwSize = new FileWriter("C:\\Users\\Lenovo\\Desktop\\DSproject\\inputs\\samples.txt", true);
        FileWriter fwGaus = new FileWriter("C:\\Users\\Lenovo\\Desktop\\DSproject\\inputs\\gaussian_exe_times.txt", true);
        FileWriter fwUni = new FileWriter("C:\\Users\\Lenovo\\Desktop\\DSproject\\inputs\\uniform_exe_times.txt", true);

        for (int i = 500; i > 0; i -= 25) {
            int numbers = i * 1000;
            int uniformSum = 0, gaussianSum = 0;
            for (int j = 0; j < 1000; j++) {
                uniformSum += uniform(numbers);
                gaussianSum += gaussian(numbers);
            }
            int uniformExeTime = uniformSum / 1000;
            int gaussianExeTime = gaussianSum / 1000;
            fwSize.write(numbers + "\n");
            fwGaus.write(uniformExeTime + "\n");
            fwUni.write(gaussianExeTime + "\n");
        }
        fwSize.close();
        fwGaus.close();
        fwUni.close();
    }

    public static long gaussian(int size) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            int random = (int) Test.random.nextGaussian(50, 25);
            if (!Main.SplayTree.find(random))
                Main.SplayTree.add(random);
            else
                Main.SplayTree.find(random);
        }
        return System.currentTimeMillis() - start;
    }

    public static long uniform(int size) {
        long startTime = System.currentTimeMillis();
        int[] array = new int[size];
        for (int i = 0; i < array.length; i++) {
            int num = Test.random.nextInt(99) + 1;
            array[i] = num;
            if (!Main.SplayTree.find(num)) {
                Main.SplayTree.add(num);
                Main.SplayTree.find(array[Test.random.nextInt(i + 1)]);
            }
        }
        return System.currentTimeMillis() - startTime;
    }

}
