package ru.geekbrains.lesson5;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private final Race race;
    private final int speed;
    private final String name;
    private final CyclicBarrier cyclicBarrier;
    private final CountDownLatch prepareCountDownLatch;
    private final CountDownLatch startCountDownLatch;
    private final Collection<String> results;
    private final Lock lock = new ReentrantLock();

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Car(Race race, int speed, CyclicBarrier cyclicBarrier, CountDownLatch prepareCountDownLatch, CountDownLatch startCountDownLatch, Collection<String> results) {
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        this.cyclicBarrier = cyclicBarrier;
        this.prepareCountDownLatch = prepareCountDownLatch;
        this.startCountDownLatch = startCountDownLatch;
        this.results = results;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            cyclicBarrier.await();
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            prepareCountDownLatch.countDown();
            startCountDownLatch.await();
            cyclicBarrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        int raceNumber = race.getStages().size();
        for (int i = 0; i < raceNumber; i++) {
            race.getStages().get(i).go(this);
            boolean isLastStage = i == raceNumber - 1;
            try {
                lock.lock();
                if (isLastStage && results.size() == 0) {
                    results.add(name);
                    System.out.printf("%s - WIN%n", name);
                }
            } finally {
                lock.unlock();
            }
        }
    }
}
