package ru.geekbrains.lesson3;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    private final ArrayList<T> fruits;

    public Box(ArrayList<T> fruits) {
        this.fruits = fruits;
    }

    public void add(T fruit) {
        fruits.add(fruit);
    }

    public int getSize() {
        return fruits.size();
    }

    public float getWeight() {
        if (fruits.size() == 0)
            return 0f;

        return fruits.size() * fruits.get(0).getWeight();
    }

    public boolean compare(Box<? extends Fruit> anotherBox) {
        return this.getWeight() == anotherBox.getWeight();
    }

    public int moveTo(Box<T> anotherBox) {
        anotherBox.fruits.addAll(fruits);
        fruits.clear();
        return anotherBox.fruits.size();
    }
}
