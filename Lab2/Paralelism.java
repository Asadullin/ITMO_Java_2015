package com.company;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by INDIGO-оя on 30.11.2015.
 */
public class Paralelism implements IterativeParallelism {

    public <T> T minimum(int threads,  List<T> list, Comparator<T> comparator) throws InterruptedException {
        List<T> minimums = new ArrayList<>();
        for (int threadNumber =0 ; threadNumber < threads; threadNumber++){
            List<T> portion = list.subList(threadNumber * list.size() / threads, ((threadNumber + 1) * list.size() / threads));
            Thread t = new Thread(new Runnable() {
                public void run() {
                    T min = Collections.min(portion, comparator);
                    minimums.add(min);
                }
            });
            t.start();
            t.join();
        }


        return Collections.min(minimums, comparator);
    }

    @Override
    public <T> T maximum(int threads, List<T> list, Comparator<T> comparator) throws InterruptedException {
        return minimum(threads, list, comparator.reversed());
    }

    @Override
    public <T> boolean all(int threads, List<T> list, Predicate<T> predicate) throws InterruptedException {
        ArrayList<Boolean> predicat = new ArrayList<>();
        Thread[] th = new Thread[threads];
        for (int threadNumber =0 ; threadNumber < threads; threadNumber++){
            List<T> portion = list.subList(threadNumber * list.size() / threads, ((threadNumber + 1) * list.size() / threads));
            Thread t =  new Thread(new Runnable() {
                public void run() {
                        for (T t : portion) {
                            if (predicate.test(t) == false) {
                                predicat.add(predicate.test(t));
                                break;
                            }
                        }
                }
            });
            th[threadNumber] = t;
            t.start();
//            t.join();
        }
        for(int i = 0;i < threads;i++)th[i].join();

        return predicat.contains(false) ? false : true ;
    }

    @Override
    public <T> boolean any(int threads, List<T> list, Predicate<T> predicate) throws InterruptedException {
        ArrayList<Boolean> predicat = new ArrayList<>();
        for (int threadNumber =0 ; threadNumber < threads; threadNumber++){
            List<T> portion = list.subList(threadNumber * list.size() / threads, ((threadNumber + 1) * list.size() / threads));
            Thread t =  new Thread(new Runnable() {
                public void run() {
                    for (T t : portion) {
                        if (predicate.test(t) == true) {
                            predicat.add(predicate.test(t));
                            break;
                        }
                    }
                }
            });
            t.start();
            t.join();
        }

        return predicat.contains(true) ? true : false ;
    }

    @Override
    public <T> List<T> filter(int threads, List<T> list, Predicate<T> predicate) throws InterruptedException {
        List<T> predicat = new ArrayList<>();
        for (int threadNumber =0 ; threadNumber < threads; threadNumber++){
            List<T> portion = list.subList(threadNumber * list.size() / threads, ((threadNumber + 1) * list.size() / threads));
            Thread t =  new Thread(new Runnable() {
                public void run() {
                    for (T t : portion) {
                        if (predicate.test(t) == true) {
                            predicat.add(t);
                        }
                    }
                }
            });
            t.start();
            t.join();
        }
        return predicat ;
    }

    @Override
    public <T, R> List<R> map(int threads, List<T> list, Function<T, R> function) throws InterruptedException {
        List<R> predicat = new ArrayList<>();
        for (int threadNumber =0 ; threadNumber < threads; threadNumber++){
            List<T> portion = list.subList(threadNumber * list.size() / threads, ((threadNumber + 1) * list.size() / threads));
            Thread t =  new Thread(new Runnable() {
                public void run() {
                    for (T t : portion) {
                        predicat.add(function.apply(t));
                    }
                }
            });
            t.start();
            t.join();
        }
        return predicat ;
    }

//    private <T> List<T> getPartList(int threads, List<T> list) {
//        List<T> part = new ArrayList<>();
//        int targetTread = list.size() / threads;
//        if (targetTread  > 0) {
//            for (int i = 0; i <list.size();  i+=targetTread) {
//                part.add((T) list.subList(i, i + targetTread));
//            }
//        }
//        //result.add(list.subList((threads - 1) * itemsInThread, list.size()));
//        return part;
//    }
}



