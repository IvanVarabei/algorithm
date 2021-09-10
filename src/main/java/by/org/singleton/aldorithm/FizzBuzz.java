package by.org.singleton.aldorithm;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class FizzBuzz {
    static int LIMIT = 1_000_000_000;

    public static void main(String... args) throws FileNotFoundException {
        int i;
        PrintStream ps = new PrintStream(new FileOutputStream("file.txt"));
        for (i = 1; i < LIMIT - 15; i += 15) {
            ps.printf("%d\n" +      // 1
                            "%d\n" +   // 2
                            "Fizz\n" +   // 3
                            "%d\n" +   // 4
                            "Buzz\n" +   // 5
                            "Fizz\n" +   // 6
                            "%d\n" +   // 7
                            "%d\n" +   // 8
                            "Fizz\n" +  // 9
                            "Buzz\n" +   // 10
                            "%d\n" +    // 11
                            "Fizz\n" +   // 12
                            "%d\n" +  // 13
                            "%d\n" +   // 14
                            "FizzBuzz\n",   // 15
                    i, i + 1, i + 3, i + 6, i + 7, i + 10, i + 12, i + 13);
        }
        while (i <= LIMIT) {
            if (i % 3 == 0) {
                ps.print("Fizz\n");
            } else if (i % 5 == 0) {
                ps.print("Buzz\n");
            } else {
                ps.printf("%d\n", i);
            }
            i++;
        }
    }
}
