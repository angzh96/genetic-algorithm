package com.lagodiuk.ga;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Population implements Iterable<Chromosome> {

    private static final int MAX_POPULATION_SIZE = 32;

    private List<Chromosome> chromosomes = new ArrayList<Chromosome>(MAX_POPULATION_SIZE);

    private final Random random = new Random();

    public void addChromosome(Chromosome chromosome) {
        this.chromosomes.add(chromosome);
    }

    public int getSize() {
        return this.chromosomes.size();
    }

    public Chromosome getRandomChromosome() {
        int numOfChromosomes = this.chromosomes.size();
        //TODO improve random generator
        //maybe use pattern strategy
        int index = this.random.nextInt(numOfChromosomes);
        return this.chromosomes.get(index);
    }

    public Chromosome getChromosomeByIndex(int index) {
        return this.chromosomes.get(index);
    }

    public void sortPopulationByFitness(ChromosomesComparator chromosomesComparator) {
        Collections.shuffle(this.chromosomes);
        Collections.sort(this.chromosomes, chromosomesComparator);
    }

    /**
     * shortening population till specific number
     */
    public void trim(int len) {
        this.chromosomes = this.chromosomes.subList(0, len);
    }

    @Override
    public Iterator<Chromosome> iterator() {
        return this.chromosomes.iterator();
    }
}
