/* Compilable code examples can be found at https://github.com/thomasnield/packt_learning_rxjava */

package ch6;

import base.ChBase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.ThreadLocalRandom;

public class Ch6_9 extends ChBase {
    public static void main(String[] args) {
        Observable<Integer> lengths =
                Observable.just("Alpha", "Beta", "Gamma", "Delta",
                        "Epsilon")
                        .subscribeOn(Schedulers.computation())
                        .map(Ch6_9::intenseCalculation)
                        .map(String::length)
                        .publish()
                        .autoConnect(2);
        lengths.subscribe(i ->
                ChBase.println("Received " + i ));
        lengths.subscribe(i ->
                ChBase.println("Received " + i ));
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