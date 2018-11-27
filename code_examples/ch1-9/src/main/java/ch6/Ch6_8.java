/* Compilable code examples can be found at https://github.com/thomasnield/packt_learning_rxjava */

package ch6;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.ThreadLocalRandom;

public class Ch6_8 extends Ch6_Base {
    public static void main(String[] args) {
        Observable<Integer> lengths =
                Observable.just("Alpha", "Beta", "Gamma", "Delta",
                        "Epsilon")
                        .subscribeOn(Schedulers.computation())
                        .map(Ch6_8::intenseCalculation)
                        .map(String::length);
        lengths.subscribe(i ->
                Ch6_Base.println("Received " + i));
        lengths.subscribe(i ->
                Ch6_Base.println("Received " + i));
        sleep(10000);
    }

    public static <T> T intenseCalculation(T value) {
        println("intenseCalculation");
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