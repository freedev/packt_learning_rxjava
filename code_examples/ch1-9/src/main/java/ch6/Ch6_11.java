/* Compilable code examples can be found at https://github.com/thomasnield/packt_learning_rxjava */

package ch6;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import java.util.concurrent.TimeUnit;

public class Ch6_11 extends Ch6_Base {
    public static void main(String[] args) {
        Observable.interval(1, TimeUnit.SECONDS,
                Schedulers.newThread())
                .subscribe(i -> Ch6_Base.println("Received " + i));
        sleep(5000);
    }
}