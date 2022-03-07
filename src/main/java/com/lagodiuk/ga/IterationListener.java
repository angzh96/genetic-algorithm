package com.lagodiuk.ga;

/**
 * After each iteration Genetic algorithm notifies listener
 */
public class IterationListener {
    private final double threshold = 1e-5;

    public void update(GeneticAlgorithm ga) {

        Chromosome best = ga.getBest();
        double bestFit = ga.fitness(best);
        int iteration = ga.getIteration();

        //输出最好的解
        System.out.println(String.format("%s\t%s\t%s", iteration, bestFit, best));

        //如果适应度满足要求，就停止遗传算法
        if(bestFit < this.threshold) {
            ga.terminate();
        }
    }
}
