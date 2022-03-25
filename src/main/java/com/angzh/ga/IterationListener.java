package com.angzh.ga;

/**
 * 迭代监听器类，监听每次算法的执行，输出执行结果
 * After each iteration Genetic algorithm notifies listener
 */
public class IterationListener {
    private final double threshold = 20;

    //返回bestfit
    public double update(GeneticAlgorithm ga) {

        Chromosome best = ga.getBest();
        double bestFit = ga.fitness(best);
        int iteration = ga.getIteration();

        // 输出最好的解
        System.out.println(String.format("%s\t%s\t%s", iteration, bestFit, best));

        // 如果适应度满足要求，就停止算法
        if(bestFit < this.threshold) {
            ga.terminate();
        }

        return bestFit;
    }
}
