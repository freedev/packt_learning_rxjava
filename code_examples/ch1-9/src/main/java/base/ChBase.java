package base;/* Compilable code examples can be found at https://github.com/thomasnield/packt_learning_rxjava */

import com.fasterxml.jackson.databind.ObjectMapper;

public class ChBase {

    public static final ObjectMapper objectMapper = new ObjectMapper();

    public static void sleep(long millis) {
        try {
            println("sleep " + millis);
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void println(Object s) {
        System.out.println(currentThread() + "->" + s);
    }

    public static String currentThread() {
        return Thread.currentThread().getName();
    }
}