package ru.geekbrains.lesson6;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class MainApp {
    public static void main(String[] args) {
        // 1. Создайте массив с набором слов, и с помощью Stream API найдите наиболее часто встречающееся;
        task1(5);
        // 2. Создайте массив объектов типа Сотрудник (с полями Имя, Возраст, Зарплата) и вычислите среднюю зарплату сотрудника;
        task2();
        // 3. Напишите метод, способный найти в массиве сотрудников из п. 2 N самых старших сотрудников и отпечатает в консоль сообщение вида “N самых старших сотрудников зовут: имя1, имя2, имяN;”.
        task3(5);
        // 4. Взять строку, состоящую из 100 слов разделенных пробелом, получить список слов длиннее 5 символов, и склеить их в одну строку с пробелом в качестве разделителя;
        task4();
        // 5. Посчитать сумму четных чисел в пределах от 100 до 200 (включительно);
        task5();
        // 6. Посчитать суммарную длину строк в одномерном массиве;
        task6();
        // 7. Из массива слов получить первые три слова в алфавитном порядке;
        task7();
    }

    private static void task1(int n) {
        // 1. Создайте массив с набором слов, и с помощью Stream API найдите наиболее часто встречающееся;
        System.out.println("task1");
        System.out.println(
                Stream.of("dull", "mist", "cruelty", "format", "harmony", "cave", "emergency", "harmony", "heal", "jury", "suggest", "link", "constant", "monstrous", "shelter", "dine", "blonde", "cave", "frank", "emergency", "blonde", "monstrous", "cave", "indirect", "swing", "plagiarize", "blonde", "disk", "cruelty", "swing", "swing", "blonde", "original", "withdrawal", "blonde", "swing", "nest", "herb", "manner", "delivery", "representative", "manner", "railroad", "sea", "emergency", "revoke", "shelter", "withdrawal", "heal", "script")
                        .collect(groupingBy(Function.identity(), counting()))
                        .entrySet()
                        .stream()
                        .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                        .limit(n)
                        .collect(toList()));
        System.out.println();
    }

    private static void task2() {
        // 2. Создайте массив объектов типа Сотрудник (с полями Имя, Возраст, Зарплата) и вычислите среднюю зарплату сотрудника;
        System.out.println("task2");
        System.out.println(
                (int) Math.abs(
                        Stream.of(
                                new Employee("Irwys Smith", 26, 63836f),
                                new Employee("Fae Wilson", 32, 145906f),
                                new Employee("Khestri Brown", 55, 149397f),
                                new Employee("Odenne Johnson", 43, 76576f),
                                new Employee("Lysabel Thomas", 64, 73457f),
                                new Employee("Eugan Jones", 24, 140393f),
                                new Employee("Jeralia Williams", 21, 130252f),
                                new Employee("Panne Miller", 35, 117033f),
                                new Employee("Taeme Garcia", 41, 112244f),
                                new Employee("Wenrys Martinez", 51, 110896f),
                                new Employee("Eddena Strickland", 38, 101635f),
                                new Employee("Bren Mejia", 52, 51895f),
                                new Employee("Krisia Palmer", 49, 78650f),
                                new Employee("Napari Mann", 33, 68453f),
                                new Employee("Irric Thornton", 20, 59354f)
                        )
                                .mapToDouble(Employee::getSalary)
                                .average()
                                .orElse(Double.NaN)
                )
        );
        System.out.println();
    }

    private static void task3(int n) {
        // 3. Напишите метод, способный найти в массиве сотрудников из п. 2 N самых старших сотрудников и отпечатает в консоль сообщение вида “N самых старших сотрудников зовут: имя1, имя2, имяN;”.
        System.out.println("task3");
        System.out.printf("%s самых старших сотрудников зовут: %s", n,
                Stream.of(
                        new Employee("Irwys Smith", 26, 63836f),
                        new Employee("Fae Wilson", 32, 145906f),
                        new Employee("Khestri Brown", 55, 149397f),
                        new Employee("Odenne Johnson", 43, 76576f),
                        new Employee("Lysabel Thomas", 64, 73457f),
                        new Employee("Eugan Jones", 24, 140393f),
                        new Employee("Jeralia Williams", 21, 130252f),
                        new Employee("Panne Miller", 35, 117033f),
                        new Employee("Taeme Garcia", 41, 112244f),
                        new Employee("Wenrys Martinez", 51, 110896f),
                        new Employee("Eddena Strickland", 38, 101635f),
                        new Employee("Bren Mejia", 52, 51895f),
                        new Employee("Krisia Palmer", 49, 78650f),
                        new Employee("Napari Mann", 33, 68453f),
                        new Employee("Irric Thornton", 20, 59354f)
                )
                        .sorted(Comparator.comparing(Employee::getAge, Comparator.reverseOrder()))
                        .limit(n)
                        .map(x -> x.getName() + String.format("%s(%d)", x.getName(), x.getAge()))
                        .collect(Collectors.joining(", "))
        );
        System.out.printf("%n%n");
    }

    private static void task4() {
        // 4. Взять строку, состоящую из 100 слов разделенных пробелом, получить список слов длиннее 5 символов, и склеить их в одну строку с пробелом в качестве разделителя;
        System.out.println("task4");
        System.out.println(
                Stream.of("draft inch formula drawing start shadow load extract superintendent greeting ferry shatter wrong review suffer reluctance bounce beam sit troop drift evoke obligation expansion abnormal whip achieve profound agency rebel cousin clock plot imposter recommend sight marketing applied incongruous mushroom ruin delicate daughter reproduce width ideal talkative football ton direct accumulation instal preach miracle bottle knee appetite give pottery tough privilege storage script dictionary spell hope Koran level mist bloody forget lazy output collapse circumstance vacuum virus disagreement word strikebreaker crash curl freight socialist rock peak decrease census faithful owner great integration cast stretch suffer base scale TRUE introduction disappear"
                        .split(" "))
                        .filter(x -> x.length() > 5)
                        .collect(joining(" "))
        );
        System.out.println();
    }

    private static void task5() {
        // 5. Посчитать сумму четных чисел в пределах от 100 до 200 (включительно);
        System.out.println("task5");
        System.out.println(
                IntStream.of(230, 133, 209, 202, 168, 76, 52, 242, 196, 67, 135, 70, 141, 131, 183, 205, 214, 130, 158, 213, 116, 88, 89, 78, 110, 99, 80, 57, 226, 209, 91, 190, 64, 98, 223, 54, 232, 64, 67, 134, 102, 151, 89, 113, 170, 102, 68, 226, 130, 205)
                        .filter(x -> x >= 100 && x <= 200 && x % 2 == 0)
                        .reduce(0, Integer::sum)
        );
        System.out.println();
    }

    private static void task6() {
        // 6. Посчитать суммарную длину строк в одномерном массиве;
        System.out.println("task6");
        System.out.println(
                Stream.of("draft", "inch", "formula", "drawing", "start", "shadow", "load", "extract", "superintendent", "greeting")
                        .map(String::length)
                        .reduce(0, Integer::sum)
        );
        System.out.println();
    }

    private static void task7() {
        // 7. Из массива слов получить первые три слова в алфавитном порядке;
        System.out.println("task7");
        System.out.println(
                Stream.of("extract", "inch", "formula", "drawing", "start", "shadow", "load", "permit", "superintendent", "anchor")
                        .sorted()
                        .limit(3)
                        .collect(Collectors.toList())
        );
        System.out.println();
    }

    private static class Employee {
        private final String name;
        private final int age;
        private final float salary;

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public float getSalary() {
            return salary;
        }

        public Employee(String name, int age, float salary) {
            this.name = name;
            this.age = age;
            this.salary = salary;
        }
    }
}
