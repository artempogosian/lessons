package ru.geekbrains.lesson2;

public class MyArraySizeException extends RuntimeException {
    public MyArraySizeException(int dimension) {
        super(String.format("Размерность массива не соответствует %s", dimension));
    }
}
