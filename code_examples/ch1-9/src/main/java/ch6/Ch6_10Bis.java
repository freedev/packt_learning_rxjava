/* Compilable code examples can be found at https://github.com/thomasnield/packt_learning_rxjava */

package ch6;

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

public class Ch6_10Bis extends Ch6_Base {
    public static void main(String[] args) {

        int numberOfThreads = 20;
        ExecutorService executor =
                Executors.newFixedThreadPool(numberOfThreads);
        Scheduler scheduler = Schedulers.from(executor);

        Observable.fromCallable(() ->
                getResponse("https://api.github.com/users/thomasnield/starred")
        ).subscribeOn(scheduler).flatMap(list -> Observable.fromArray(list.toArray()))

                .subscribe(Ch6_Base::println);
        sleep(10000);
    }

    private static List<Map<String, Object>> getResponse(String path) {
        try {
            TypeReference<List<Map<String, Object>>> listTypeReference = new TypeReference<List<Map<String, Object>>>(){};
            String starred = new Scanner(new URL(path).openStream(),
                    "UTF-8").useDelimiter("\\A").next();
            List<Map<String, Object>> value = objectMapper.readValue(new URL(path), listTypeReference);
//            return new Scanner(new URL(path).openStream(),
//                    "UTF-8").useDelimiter("\\A").next();
            return value;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}