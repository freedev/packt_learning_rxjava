/* Compilable code examples can be found at https://github.com/thomasnield/packt_learning_rxjava */

package ch6;

import base.ChBase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Ch6_14 extends ChBase {
    public static void main(String[] args) {
//Happens on IO Scheduler
        Observable.just("WHISKEY/27653/TANGO", "6555/BRAVO",
                "232352/5675675/FOXTROT")
                .subscribeOn(Schedulers.io())
                .flatMap(s -> {
                    println("flatMap");
                    return Observable.fromArray(s.split("/"));
                })
 //               .doOnNext(s -> println("Split out " + s))
//Happens on Computation Scheduler
                .observeOn(Schedulers.computation())
                .filter(s -> {
                    println("filter");
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
//                .doOnSuccess(i -> println("Calculated sum" + i ))
//Switch back to IO Scheduler
                .observeOn(Schedulers.io())
                .map(i -> {
                    println("toString");
                    return i.toString();
                })
                .doOnSuccess(s -> println("Writing " + s + " to file"))
                .subscribe(s ->
                {
                    println("write");
                    write(s, "/tmp/output.txt");
                });
        sleep(1000);
    }

    public static void write(String text, String path) {
        BufferedWriter writer = null;
        try {
//create a temporary file
            File file = new File(path);
            writer = new BufferedWriter(new FileWriter(file));
            writer.append(text);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
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