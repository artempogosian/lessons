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


import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainApp {
    static final int SIZE = 100000000;

    public static void main(String[] args) {
        float[] arr = new float[SIZE];
        method1(arr);
        method2(arr, 4);
    }

    private static void method1(float[] arr) {
        Arrays.fill(arr, 1f);

        long startTime = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++)
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));

        System.out.println(System.currentTimeMillis() - startTime);
    }

    private static void method2(float[] arr, int nThreads) {
        Arrays.fill(arr, 1f);

        ExecutorService exec = Executors.newFixedThreadPool(nThreads);

        try {
            long startTime = System.currentTimeMillis();

            Future<Long> future = exec.submit(() -> {
                for (int i = 0; i < arr.length; i++)
                    arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));

                return System.currentTimeMillis() - startTime;
            });

            System.out.println(future.get());
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            exec.shutdown();
        }
    }
}
