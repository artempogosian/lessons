package ru.geekbrains.lesson4;

// Необходимо реализовать два метода, которые делают следующее:

// 1. Создают одномерный длинный массив, например:
//      static final int SIZE = 100_000_000;
//      float[] arr = new float[size];
//
// 2. Заполняют этот массив единицами.

// 3. Проходят по всему массиву и для каждой ячейки считают новое значение по формуле:
//      arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
//
// 4. Замеряют время, ушедшее только на вычисление новых значений элементов массива.
//
// Отличия первого метода от второго:
//    - Первый метод в одном потоке пробегает по массиву и вычисляет значения.
//    - Второй метод принимает на вход ссылку на массив и количество потоков, которое участвует в обработке.
//          Внутри второго метода запускается FixedThreadPool и решает поставленную задачу.


import java.util.*;
import java.util.concurrent.*;

public class MainApp {
    static final int SIZE = 100000000;

    public static void main(String[] args) {
        float[] arr = new float[SIZE];
        method1(arr);
        method2(arr, Runtime.getRuntime().availableProcessors());

        // Results
        // method1: 11691
        // method2: 1540
    }

    private static void method1(float[] arr) {
        Arrays.fill(arr, 1f);
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("method1: " + (System.currentTimeMillis() - startTime));
    }

    private static void method2(float[] arr, int nThreads) {
        Arrays.fill(arr, 1f);
        ExecutorService pool = Executors.newFixedThreadPool(nThreads);
        List<Callable<Long>> tasks = new ArrayList<>(nThreads);
        try {
            for (int i = 0; i < nThreads; i++) {
                int startIndex = arr.length / nThreads * i;
                int endIndex = i + 1 == nThreads ? arr.length : (arr.length / nThreads) * (i + 1);
                tasks.add(new job(arr, startIndex, endIndex));
            }
            long startTime = System.currentTimeMillis();
            pool.invokeAll(tasks);
            System.out.println("method2: " + (System.currentTimeMillis() - startTime));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pool.shutdown();
        }
    }

    public static class job implements Callable<Long> {
        private final float[] arr;
        private final int startIndex;
        private final int endIndex;

        public job(float[] arr, int startIndex, int endIndex) {
            this.arr = arr;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
        }

        @Override
        public Long call() {
            for (int i = startIndex; i < endIndex; i++) {
                arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            return null;
        }
    }
}
