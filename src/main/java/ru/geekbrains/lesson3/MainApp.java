package ru.geekbrains.lesson3;

import java.util.*;
import java.util.stream.Collectors;

public class MainApp {
    public static void main(String[] args) {
        // 1. Написать метод, который меняет два элемента массива местами (массив может быть любого ссылочного типа);
        // task1(new String[]{"One", "Two", "Three", "Four", "Five"}, 1, 4);
        // task1(new String[]{"One", "Two", "Three", "Four", "Five"}, 3, 2);
        // task1(new String[]{"One", "Two", "Three", "Four", "Five"}, 2, 2);
        // task1(new String[]{"One", "Two", "Three", "Four", "Five"}, 0, 5);
        // task1(new String[]{"One", "Two", "Three", "Four", "Five"}, -1, 0);

        // 2. Написать метод, который преобразует массив в ArrayList;
        //task2(new Object[]{"test 1", 1, 4f});

        // 3. Задача:
        //    a. Даны классы Fruit, Apple extends Fruit, Orange extends Fruit;
        //    b. Класс Box, в который можно складывать фрукты. Коробки условно сортируются по типу фрукта, поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
        //    c. Для хранения фруктов внутри коробки можно использовать ArrayList;
        //    d. Сделать метод getWeight(), который высчитывает вес коробки, зная вес одного фрукта и их количество: вес яблока – 1.0f, апельсина – 1.5f (единицы измерения не важны);
        //    e. Внутри класса Box сделать метод compare(), который позволяет сравнить текущую коробку с той, которую подадут в compare() в качестве параметра. true – если их массы равны, false в противоположном случае.
        //       Можно сравнивать коробки с яблоками и апельсинами;
        //    f. Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую. Помним про сортировку фруктов: нельзя яблоки высыпать в коробку с апельсинами.
        //       Соответственно, в текущей коробке фруктов не остается, а в другую перекидываются объекты, которые были в первой;
        //    g. Не забываем про метод добавления фрукта в коробку.
        // task3();

        // 4. Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся).
        //    Найти и вывести список слов, из которых состоит массив (т.е. если слово Яблоко встречается 10 раз, то его надо в список добавить только 1 раз). Посчитать, сколько раз встречается каждое слово.
        task4();
    }

    private static <T> void task1(T[] inputArr, int p1, int p2) {
        if (inputArr == null)
            throw new NullPointerException("inputArr is null");

        if ((p1 >= inputArr.length || p1 < 0) || (p2 >= inputArr.length || p2 < 0))
            throw new IllegalArgumentException(String.format("Indexes are out of bounds for length %d", inputArr.length));

        System.out.printf("Before:\t" + Arrays.toString(inputArr) + "%n");
        T tmp = inputArr[p1];
        inputArr[p1] = inputArr[p2];
        inputArr[p2] = tmp;
        System.out.printf("After:\t" + Arrays.toString(inputArr) + "%n");

        System.out.println();
    }

    private static <T> void task2(T[] inputArr) {
        if (inputArr == null)
            throw new NullPointerException("inputArr is null");

        ArrayList<T> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, inputArr);

        for (T t : arrayList) System.out.println(t);
    }

    private static void task3() {
        Box<Orange> orangeBox1 = new Box<>(getOranges());
        Box<Orange> orangeBox2 = new Box<>(getOranges());
        Box<Orange> orangeBox3 = new Box<>(getOranges());
        Box<Orange> orangeBox4 = new Box<>(getOranges(3));
        Box<Orange> orangeBox5 = new Box<>(getOranges(2));
        Box<Apple> appleBox1 = new Box<>(getApples());
        Box<Apple> appleBox2 = new Box<>(getApples());
        Box<Apple> appleBox3 = new Box<>(getApples(2));

        //orangeBox4.add(new Apple()); <- compile error

        System.out.printf("orangeBox1[%d] compare to orangeBox2[%d]: %b%n", orangeBox1.getSize(), orangeBox2.getSize(), orangeBox1.compare(orangeBox2));
        System.out.printf("orangeBox4[%d] compare to appleBox2[%d]: %b%n", orangeBox4.getSize(), appleBox2.getSize(), orangeBox4.compare(appleBox2));
        System.out.printf("orangeBox5[%d] compare to appleBox3[%d]: %b%n", orangeBox5.getSize(), appleBox3.getSize(), orangeBox5.compare(appleBox3));
        appleBox3.add(new Apple());
        System.out.println("Added new apple to the appleBox3");
        System.out.printf("orangeBox5[%d] compare to appleBox3[%d]: %b%n", orangeBox5.getSize(), appleBox3.getSize(), orangeBox5.compare(appleBox3));
        System.out.printf("appleBox1[%d] compare to appleBox2[%d]: %b%n", appleBox1.getSize(), appleBox2.getSize(), appleBox1.compare(appleBox2));

        System.out.printf("Move orangeBox2[%d] to orangeBox3[%d]: %d%n", orangeBox2.getSize(), orangeBox3.getSize(), orangeBox2.moveTo(orangeBox3));
        System.out.printf("orangeBox2 size [%d]; orangeBox3 size [%d]%n", orangeBox2.getSize(), orangeBox3.getSize());
        System.out.printf("Move appleBox2[%d] to appleBox3[%d]: %d%n", appleBox2.getSize(), appleBox3.getSize(), appleBox2.moveTo(appleBox3));
        System.out.printf("appleBox2 size [%d]; appleBox3 size [%d]%n", appleBox2.getSize(), appleBox3.getSize());

        System.out.printf("The weight of the orangeBox1[%d] is: %f%n", orangeBox1.getSize(), orangeBox1.getWeight());
        System.out.printf("The weight of the appleBox3[%d] is: %f%n", appleBox3.getSize(), appleBox3.getWeight());

        //orangeBox1.moveTo(appleBox1); <- compile error
    }

    private static void task4() {
        String[] wordsArr = new String[]{
                "climate",
                "cookie",
                "meat",
                "throat",
                "assumption",
                "meat",
                "signature",
                "phone",
                "information",
                "signature",
                "director",
                "strategy",
                "storage",
                "strategy",
                "climate",
                "distribution",
                "science",
                "cookie",
                "conclusion",
                "information"
        };

        System.out.printf("Original array:%n%n");

        for (String word : wordsArr)
            System.out.println(word);

        System.out.printf("%nDuplicates removed:%n%n");

        Map<String, Long> wordsList = Arrays.stream(wordsArr).collect(
                Collectors.groupingBy(word -> word, LinkedHashMap::new, Collectors.counting()));

        wordsList.forEach((k, v) -> System.out.printf("%s : %d%n", k, v));
    }

    //region Helpers

    private static ArrayList<Orange> getOranges() {
        return getOranges(0);
    }

    private static ArrayList<Orange> getOranges(int count) {
        ArrayList<Orange> oranges = new ArrayList<>();
        Random random = new Random();
        var number = count == 0 ? random.nextInt(10) : count;
        for (int i = 0; i < number; i++) {
            oranges.add(new Orange());
        }
        return oranges;
    }

    private static ArrayList<Apple> getApples() {
        return getApples(0);
    }

    private static ArrayList<Apple> getApples(int count) {
        ArrayList<Apple> apples = new ArrayList<>();
        Random random = new Random();
        var number = count == 0 ? random.nextInt(10) : count;
        for (int i = 0; i < number; i++) {
            apples.add(new Apple());
        }
        return apples;
    }

    //endregion
}
