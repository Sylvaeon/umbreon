package me.sylvaeon.umbreon.rpg.world.entity;

import java.util.concurrent.ThreadLocalRandom;

public class Tree {
	TreeSpecies species;
	double precipitationFactor, sizeFactor;

    public Tree(TreeSpecies species, double precipitation) {
        species = species;
        precipitationFactor = (precipitation / 25d) + 0.5d;
        sizeFactor = (ThreadLocalRandom.current().nextInt(24) / 5d) + 0.5d;
    }

    public final int getLogs() {
        return (int) (precipitationFactor * sizeFactor);
    }

    public TreeSpecies getSpecies() {
        return species;
    }

    public void setSpecies(TreeSpecies species) {
        this.species = species;
    }

    public double getPrecipitationFactor() {
        return precipitationFactor;
    }

    public void setPrecipitationFactor(double precipitationFactor) {
        this.precipitationFactor = precipitationFactor;
    }

    public double getSizeFactor() {
        return sizeFactor;
    }

    public void setSizeFactor(double sizeFactor) {
        this.sizeFactor = sizeFactor;
    }
}
