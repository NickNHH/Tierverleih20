package ch.bbw.Tierverlei_v20;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Animals {
    private ArrayList<Animal> animals;

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(ArrayList<Animal> animals) {
        this.animals = animals;
    }

    @Override
    public String toString() {
        return "animals [animals=" + Arrays.toString(animals.toArray()) + "]";
    }
}
