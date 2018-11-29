/* Compilable code examples can be found at https://github.com/thomasnield/packt_learning_rxjava */

package ch6;

import base.ChBase;
import com.fasterxml.jackson.core.type.TypeReference;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Ch6_10 extends ChBase {
    public static void main(String[] args) {
        Observable.fromCallable(() ->
                getResponse("https://api.github.com/users/thomasnield/starred")
        ).flatMap(list -> Observable.fromArray(list.toArray()))
                .subscribeOn(Schedulers.io())
                .subscribe(ChBase::println);
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