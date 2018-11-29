/* Compilable code examples can be found at https://github.com/thomasnield/packt_learning_rxjava */

package ch6;

import base.ChBase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class Ch6_13  extends ChBase {
    public static void main(String[] args) {
//Happens on IO Scheduler
        Observable.just("WHISKEY/27653/TANGO", "6555/BRAVO",
                "232352/5675675/FOXTROT")
                .subscribeOn(Schedulers.io())
                .flatMap(s -> {
                    println("fromArray");
                    return Observable.fromArray(s.split("/"));
                })
//Happens on Computation Scheduler
                .observeOn(Schedulers.computation())
                .filter(s -> {
                    println("matches");
                    return s.matches("[0-9]+");
                })
                .map(s1 -> {
                    println("valueOf");
                    return Integer.valueOf(s1);
                })
                .reduce((total, next) -> {
                    println("total + next");
                    return total + next;
                })
                .subscribe(i -> println("Received " + i
                        ));
        sleep(1000);
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}