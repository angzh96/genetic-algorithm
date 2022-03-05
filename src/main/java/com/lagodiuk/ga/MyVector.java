package com.lagodiuk.ga;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Chromosome, which represents vector of  integers
 */
public class MyVector implements Chromosome<MyVector>, Cloneable{
    private static final Random random = new Random();
    private final int[] vector = new int[10];

    /**
     * Returns clone of current chromosome, which is mutated a bit
     * 基本位变异
     */
    @Override
    public MyVector mutate() {
        MyVector result = this.clone();
        int index = random.nextInt(this.vector.length);
        int mutationValue = random.nextInt(10) - random.nextInt(10);
        result.vector[index] += mutationValue;

        return result;
    }
    /**
     * Returns list of siblings <br/>
     * Siblings are actually new chromosomes, <br/>
     * created using any of crossover strategy
     * 单点交叉
     */
    @Override
    public List<MyVector> crossover(MyVector other) {
        MyVector thisClone = this.clone();
        MyVector otherClone = other.clone();
        int index = random.nextInt(this.vector.length - 1);
        for(int i = index; i<this.vector.length; i++) {
            int tmp = thisClone.vector[i];
            thisClone.vector[i] = otherClone.vector[i];
            otherClone.vector[i] = tmp;
        }

        return Arrays.asList(thisClone, otherClone);
    }

    @Override
    protected MyVector clone() {
        MyVector clone = new MyVector();
        System.arraycopy(this.vector, 0, clone.vector, 0,this.vector.length);
        return clone;
    }

    public int[] getVector() {
        return this.vector;
    }

    @Override
    public String toString() {
        return Arrays.toString(this.vector);
    }
}
