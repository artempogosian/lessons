package ru.geekbrains.lesson2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainApp {
    private static final int LENGTH = 4;

    public static void main(String[] args) {
        // 1. Напишите метод, на вход которого подаётся двумерный строковый массив размером 4х4. При подаче массива другого размера необходимо бросить исключение
        // MyArraySizeException.
        // 2. Далее метод должен пройтись по всем элементам массива, преобразовать в int и просуммировать. Если в каком-то элементе массива преобразование не удалось
        // (например, в ячейке лежит символ или текст вместо числа), должно быть брошено исключение MyArrayDataException с детализацией, в какой именно ячейке лежат
        // неверные данные.
        // 3. В методе main() вызвать полученный метод, обработать возможные исключения MyArraySizeException и MyArrayDataException и вывести результат расчета
        // (сумму элементов, при условии что подали на вход корректный массив).
        task1();

        // 4. Напишите тесты для сравнения скорости обращения по индексу к среднему элементу ArrayList и LinkedList.
        // В коллекциях должно быть по 10, 1000, 100.000, 10.000.000 элементов. Чтобы это заняло хоть сколько-то значительное время можно выполнить 10.000 обращений.
        task2();

        // 5. Напишите тест для удаления центральных элементов по индексу из ArrayList и LinkedList, каждый раз удаляйте по половине элементов
        // (протестировать размеры коллекций: 100 [удалить 50], 10000 [удалить 5000], 100000 [удалить 50000]).
        task3();
    }

    private static void task1() {
        String[][] strArr = new String[][]{
                {"1", "2", "3", "4"},
                {"5", "6", "7", "8"},
                {"9", "10", "11", "12"},
                {"13", "14", "15", "16"}
        };

        try {
            int sum = convertToInteger(strArr);
            System.out.printf("Сумма элементов массива равна: %d %n", sum);
        } catch (MyArraySizeException | MyArrayDataException e) {
            System.out.println(e.getMessage());
        }
    }

    private static int convertToInteger(String[][] inputArr) throws MyArraySizeException, MyArrayDataException {
        if (inputArr == null) throw new NullPointerException("inputArr");
        if (inputArr.length != LENGTH) throw new MyArraySizeException(LENGTH);

        for (String[] i : inputArr)
            if (i.length != LENGTH)
                throw new MyArraySizeException(LENGTH);

        int[][] intArr = new int[LENGTH][LENGTH];
        int sum = 0;

        for (int i = 0; i < inputArr.length; i++)
            for (int j = 0; j < inputArr[i].length; j++)
                try {
                    int number = Integer.parseInt(inputArr[i][j]);
                    intArr[i][j] = number;
                    sum += number;
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(inputArr[i][j], i, j);
                }

        return sum;
    }

    private static void task2() {
        final int[] grades = new int[]{10, 1000, 100000, 10000000};
        final int count = 10000;
        long startTime;
        long endTime;

        for (int grade : grades) {
            // example with ArrayList
            List<Integer> arrayList = new ArrayList<>(grade);
            for (int i = 0; i < grade; i++) arrayList.add(i);
            startTime = System.currentTimeMillis();
            for (int i = 0; i < count; i++) arrayList.get(arrayList.size() / 2);
            endTime = System.currentTimeMillis() - startTime;

            System.out.printf("Time: %d | Grade: %s | Type ArrayList %n", endTime, grade);

            // example with LinkedList
            List<Integer> linkedList = new LinkedList<>();
            for (int i = 0; i < grade; i++) linkedList.add(i);
            startTime = System.currentTimeMillis();
            for (int i = 0; i < count; i++) linkedList.get(linkedList.size() / 2);
            endTime = System.currentTimeMillis() - startTime;

            System.out.printf("Time: %d | Grade: %s | Type LinkedList %n", endTime, grade);
        }
/*
                     10    1000    100.000    10.000.000

         Attempt 1

         ArrayList:  0     0       0          0
         LinkedList: 0     0       1079       321919

         Attempt 2

         ArrayList:  0     0       0          0
         LinkedList: 0     0       1032       319375

         Attempt 3

         ArrayList:  0     0       0          0
         LinkedList: 0     15      1141       353188
 */
    }

    private static void task3() {
        int[] grades = new int[]{100, 10000, 100000};
        long startTime;
        long endTime;

        for (int grade : grades) {
            int half = grade / 2;

            // example with ArrayList
            List<Integer> arrayList = new ArrayList<>(grade);
            for (int i = 0; i < grade; i++) arrayList.add(i);
            startTime = System.currentTimeMillis();
            do {
                arrayList.remove(arrayList.size() / 2);
            } while (arrayList.size() > half);
            endTime = System.currentTimeMillis() - startTime;

            System.out.printf("Time: %d | Grade: %s | Type ArrayList %n", endTime, grade);

            // example with LinkedList
            List<Integer> linkedList = new LinkedList<>();
            for (int i = 0; i < grade; i++) linkedList.add(i);
            startTime = System.currentTimeMillis();
            do {
                linkedList.remove(linkedList.size() / 2);
            } while (linkedList.size() > half);
            endTime = System.currentTimeMillis() - startTime;

            System.out.printf("Time: %d | Grade: %s | Type LinkedList %n", endTime, grade);
        }
/*
                     100    10.000    100.000

         Attempt 1

         ArrayList:  0      0         125
         LinkedList: 0      47        3484

         Attempt 2

         ArrayList:  0      0         125
         LinkedList: 0      31        3187

         Attempt 3

         ArrayList:  0      0         126
         LinkedList: 0      46        3110
*/
    }
}
