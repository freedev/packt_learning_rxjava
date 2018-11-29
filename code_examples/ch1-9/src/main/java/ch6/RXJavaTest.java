/* Compilable code examples can be found at https://github.com/thomasnield/packt_learning_rxjava */

package ch6;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class RXJavaTest {
    public static void main(String[] args) {

        boolean test = false;
        int numberOfThreads = 4;
        ExecutorService executor =
                Executors.newFixedThreadPool(numberOfThreads);
        Scheduler scheduler = Schedulers.from(executor);

        Observable<Object> objectObservable = Observable
                .fromCallable(() -> getResponse("https://raw.githubusercontent.com/freedev/solr-import-export-json/master/README.md"))
                .flatMap(Observable::fromIterable)
                .flatMap(line -> {
                    println("entering flatMap just");
                    if (test) {
                        return Observable.just(line)
                                         .subscribeOn(scheduler)
                                         .map(RXJavaTest::intenseCalculation);
                    }
                    Observable<Object> just = Observable.just(line);
                    just.subscribeOn(scheduler);
                    return just.map(RXJavaTest::intenseCalculation);
                });

        objectObservable.blockingSubscribe(s -> {
            println("Received " + s);
        });
        try {
            println("awaitTermination");
            executor.awaitTermination(3, TimeUnit.SECONDS);
            println("awaitTermination completed");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    public static <T> T intenseCalculation(T value) {
        sleep(ThreadLocalRandom.current().nextInt(3000));
        return value;
    }

    private static List<String> getResponse(String path) {
        try {
            return new BufferedReader(new InputStreamReader(new URL(path).openStream())).lines()
                                                                                        .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void println(Object s) {
        System.out.println(Thread.currentThread().getName() + "->" + s);
    }

    public static void sleep(long millis) {
        try {
            println("sleep " + millis);
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}