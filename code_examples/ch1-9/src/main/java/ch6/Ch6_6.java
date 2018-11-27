/* Compilable code examples can be found at https://github.com/thomasnield/packt_learning_rxjava */

package ch6;

import io.reactivex.schedulers.Schedulers;
import io.reactivex.Observable;

import java.util.concurrent.ThreadLocalRandom;

public class Ch6_6 extends Ch6_Base {
    public static void main(String[] args) {
        Observable.just("Alpha", "Beta", "Gamma", "Delta",
                "Epsilon")
                .subscribeOn(Schedulers.computation())
                .map(Ch6_6::intenseCalculation)
                .blockingSubscribe(Ch6_Base::println,
                        Throwable::printStackTrace,
                        () -> Ch6_Base.println("Done!"));
    }

    public static <T> T intenseCalculation(T value) {
        Ch6_Base.println("intenseCalculation");
        sleep(ThreadLocalRandom.current().nextInt(3000));
        return value;
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}