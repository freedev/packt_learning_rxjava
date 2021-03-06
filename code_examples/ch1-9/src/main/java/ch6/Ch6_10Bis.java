/* Compilable code examples can be found at https://github.com/thomasnield/packt_learning_rxjava */

package ch6;

import base.ChBase;
import com.fasterxml.jackson.core.type.TypeReference;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class Ch6_10Bis extends ChBase {
    public static void main(String[] args) {

        int numberOfThreads = 4;
        ExecutorService executor =
                Executors.newFixedThreadPool(numberOfThreads);
        Scheduler scheduler = Schedulers.from(executor);

        Observable<Object> objectObservable = Observable
                .fromCallable(() -> getResponse("https://api.github.com/users/freedev/starred") )
                .flatMap(Observable::fromIterable)
                .flatMap(line -> {
                    println("entering flatMap just");
                    /*
                    Observable<Object> just = Observable.just(line);
                    just.subscribeOn(Schedulers.computation());
                    return just.map(Ch6_10Bis::intenseCalculation);
                    */
                    return Observable.just(line)
                                     .subscribeOn(scheduler)
                                     .map(i2 -> {
                                         println("before intenseCalculation");
                                         return intenseCalculation(i2);
                                     });

                });

        objectObservable.subscribe(s -> {
            ChBase.println("Received " + s);
        });
        try {
            println("awaitTermination");
            executor.awaitTermination(3, TimeUnit.SECONDS);
            println("awaitTermination completed");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        sleep(3000);
    }

    public static <T> T intenseCalculation(T value) {
        sleep(ThreadLocalRandom.current().nextInt(3000));
        return value;
    }

    private static List<Map<String, Object>> getResponse(String path) {
        try {
            println("entering getResponse");
            TypeReference<List<Map<String, Object>>> listTypeReference = new TypeReference<List<Map<String, Object>>>() {};
            String starred = new Scanner(new URL(path).openStream(), "UTF-8").useDelimiter("\\A").next();
            List<Map<String, Object>> value = objectMapper.readValue(new URL(path), listTypeReference);
            println("exiting getResponse");
            return value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}