/* Compilable code examples can be found at https://github.com/thomasnield/packt_learning_rxjava */

package ch6;

import base.ChBase;
import io.reactivex.Observable;

import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

public class Ch6_16 extends ChBase {
    public static void main(String[] args) {
        Observable.range(1, 10)
                  .map(i -> intenseCalculation(i))
                  .subscribe(i -> println("Received " + i + " " + LocalTime.now()));
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