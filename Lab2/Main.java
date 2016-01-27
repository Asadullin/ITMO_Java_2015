package com.company;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;

public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
	// write your code here
        Random random = new Random();
        ArrayList <Integer> list = new ArrayList<>();
        for (int i = 0 ; i < 300000; i ++  )
        {
          list.add( Math.abs(random.nextInt(10000000))+10);
        }
        list.add(1);

        Predicate<Integer> pared = (s) -> s >1;
        Predicate<Integer> pared1 = (s1) -> s1 == 1;
        Function<Integer, Integer> toInteger = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer s) {
                int a ;
                a = s+10;
                return a;
            }
        };
        Paralelism paralel = new Paralelism();
        long startTime = System.currentTimeMillis();
        System.out.println(paralel.minimum(3, list, Comparator.<Integer>naturalOrder()));

        System.out.println(paralel.maximum(3, list, Comparator.<Integer>naturalOrder()));
        System.out.println(paralel.all(3, list,pared ));
        System.out.println(paralel.any(3, list, pared1));
        paralel.filter(3, list, pared);
        paralel.map(3, list,toInteger );
        long timeSpent = System.currentTimeMillis() - startTime;
        System.out.println("программа выполнялась " + timeSpent + " миллисекунд");



//        System.out.println("Родительский поток.");
//        for(int i = 0; i <3; i++){
//            Thread t = new Thread() {
//                public void run() {
//                    Thread ct = Thread.currentThread();
//
//                    for (int i = 1; i <= 5; i++) {
//                        System.out.println("Значение цикла дочернего потока " + ct.getName() + " - " + i);
//
//                    }
//
//                }
//
//
//            };
//
//// Запуск
//            t.start();
//            t.join();
//           // Thread.yield();
//        }

}


}
