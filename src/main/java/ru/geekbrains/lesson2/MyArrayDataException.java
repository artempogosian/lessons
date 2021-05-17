package ru.geekbrains.lesson2;

public class MyArrayDataException extends RuntimeException {
    public MyArrayDataException(String str, int row, int col) {
        super(String.format("Не удалось преобразовать в число строку '%s' в ячейке [%d][%d]", str, row, col));
    }
}
