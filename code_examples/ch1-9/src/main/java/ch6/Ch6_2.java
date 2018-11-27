/* Compilable code examples can be found at https://github.com/thomasnield/packt_learning_rxjava */

package ch6;

import io.reactivex.Observable;

import java.util.concurrent.ThreadLocalRandom;

public class Ch6_2 {
    public static void main(String[] args) {
        Observable.just("Alpha", "Beta", "Gamma", "Delta",
                "Epsilon")
                .map(s -> intenseCalculation((s)))
                .subscribe(System.out::println);
        Observable.range(1, 6)
                .map(s -> intenseCalculation((s)))
                .subscribe(System.out::println);
    }

    public static <T> T intenseCalculation(T value) {
        sleep(ThreadLocalRandom.current().nextInt(3000));
        return value;
    }

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