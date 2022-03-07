package com.lagodiuk.ga;

import java.util.LinkedList;
import java.util.List;

public class GeneticAlgorithm {
    private static final int ALL_PARENTAL_CHROMOSOMES = Integer.MAX_VALUE;

    private Population population;

    private boolean terminate = false;

    private int parentChromosomesSurviveCount = ALL_PARENTAL_CHROMOSOMES;

    private int iteration = 0;

    private final ChromosomesComparator chromosomesComparator = new ChromosomesComparator();

    private final List<IterationListener> iterationListeners = new LinkedList<IterationListener>();

    public GeneticAlgorithm(Population population) {
        this.population = population;
//        this.chromosomesComparator = new ChromosomesComparator();
        this.population.sortPopulationByFitness(this.chromosomesComparator);
    }

    //进化
    public void evolve() {
        int parentPopulationSize = this.population.getSize();

        Population newPopulation = new Population();

        for(int i = 0; (i < parentPopulationSize) && (i < this.parentChromosomesSurviveCount); i++) {
            newPopulation.addChromosome(this.population.getChromosomeByIndex(i));
        }

        for(int i = 0; i < parentPopulationSize; i++) {
            Chromosome chromosome = this.population.getChromosomeByIndex(i);
            Chromosome mutated = chromosome.mutate();

            Chromosome otherChromosome = this.population.getRandomChromosome();
            List<Chromosome> crossovered = chromosome.crossover(otherChromosome);

            newPopulation.addChromosome(mutated);
            for(Chromosome chr : crossovered) {
                newPopulation.addChromosome(chr);
            }
        }

        newPopulation.sortPopulationByFitness(this.chromosomesComparator);
        newPopulation.trim(parentPopulationSize);
        this.population = newPopulation;
    }

    //多个体进化
    public void multiEvolve(int count) {
        this.terminate = false;

        for(int i = 0; i < count; i++) {
            if(this.terminate) {
                break;
            }
            this.evolve();
            this.iteration = i;
            for(IterationListener listener2 : this.iterationListeners) {
                listener2.update(this);
            }
        }
    }


    public int getIteration() {
        return this.iteration;
    }

    public void terminate() {
        this.terminate = true;
    }

    public Population getPopulation() {
        return this.population;
    }

    public Chromosome getBest() {
        return this.population.getChromosomeByIndex(0);
    }

    public Chromosome getWorst() {
        return this.population.getChromosomeByIndex(this.population.getSize() - 1);
    }

    public void setParentChromosomesSurviveCount(int parentChromosomesCount) {
        this.parentChromosomesSurviveCount = parentChromosomesCount;
    }

    public int getParentChromosomesSurviveCount() {
        return this.parentChromosomesSurviveCount;
    }

    public void addIterationListener(IterationListener listener) {
        this.iterationListeners.add(listener);
    }

    public void removeIterationListener(IterationListener listener) {
        this.iterationListeners.remove(listener);
    }

    public double fitness(Chromosome chromosome) {
        return this.chromosomesComparator.fit(chromosome);
    }

    public void clearCache() {
        this.chromosomesComparator.clearCache();
    }
}
