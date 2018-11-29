/* Compilable code examples can be found at https://github.com/thomasnield/packt_learning_rxjava */

package ch6;

import base.ChBase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.time.LocalTime;
import java.util.concurrent.ThreadLocalRandom;

public class Ch6_17 extends ChBase {
    public static void main(String[] args) {
        println("entering main");
        Observable.range(1, 10)
                  .flatMap(i -> {
                              println("entering flatMap");
                              return Observable.just(i)
                                               .subscribeOn(Schedulers.computation())
                                               .map(i2 -> {
                                                   println("before intenseCalculation");
                                                   return intenseCalculation(i2);
                                               });
                          }
                  )
                  .subscribe(i -> println("Received " + i + " " + LocalTime.now()));
        sleep(20000);
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
}