/* Compilable code examples can be found at https://github.com/thomasnield/packt_learning_rxjava */

package ch6;

import io.reactivex.Observable;

import java.util.concurrent.ThreadLocalRandom;

public class Ch6_Base {

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void println(Object s) {
        System.out.println(currentThread() + "->" + s);
    }

    public static String currentThread() {
        return Thread.currentThread().getName();
    }
}