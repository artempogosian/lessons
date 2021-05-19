package ru.geekbrains.lesson3;

public class Apple extends Fruit {
    private final float weight;

    public Apple() {
        weight = 1.0f;
    }

    @Override
    protected float getWeight() {
        return weight;
    }
}
