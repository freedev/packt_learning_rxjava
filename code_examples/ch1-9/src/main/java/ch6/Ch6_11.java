/* Compilable code examples can be found at https://github.com/thomasnield/packt_learning_rxjava */

package ch6;

import base.ChBase;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class Ch6_11 extends ChBase {
    public static void main(String[] args) {
        Observable.interval(1, TimeUnit.SECONDS,
                Schedulers.newThread())
                .subscribe(i -> ChBase.println("Received " + i));
        sleep(5000);
    }
}