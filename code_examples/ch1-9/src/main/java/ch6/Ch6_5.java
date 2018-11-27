/* Compilable code examples can be found at https://github.com/thomasnield/packt_learning_rxjava */

package ch6;

import io.reactivex.Observable;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Ch6_5 extends Ch6_Base {
    public static void main(String[] args) {
        Observable.interval(1, TimeUnit.SECONDS)
                .map(l -> intenseCalculation((l)))
                .subscribe(Ch6_Base::println);
        sleep(Long.MAX_VALUE);
    }

    public static <T> T intenseCalculation(T value) {
        println("intenseCalculation");
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
}