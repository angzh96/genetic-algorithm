package com.lagodiuk.ga;

/**
 * Fitness function, which calculates difference between chromosomes vector
 * and target vector
 */
public class ChromosomeFitness {
    private final int[] target = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10};

    public Double calculate(Chromosome chromosome) {
        double delta = 0;
        int[] v = chromosome.getVector();
        for(int i = 0; i < 10; i++) {
            delta += this.sqr(v[i] - this.target[i]);
        }
        return delta;
    }
    private double sqr(double x) {
        return x * x;
    }
}
