package ru.geekbrains.lesson3;

public class Orange extends Fruit {
    private final float weight;

    public Orange() {
        weight = 1.5f;
    }

    @Override
    protected float getWeight() {
        return weight;
    }
}
