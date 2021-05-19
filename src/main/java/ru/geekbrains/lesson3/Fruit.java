package ru.geekbrains.lesson3;

public class Fruit {
    private final float weight;

    public Fruit(float weight) {
        this.weight = weight;
    }

    protected float getWeight() {
        return weight;
    }
}
