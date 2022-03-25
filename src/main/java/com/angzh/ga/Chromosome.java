package com.angzh.ga;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 个体类
 * Chromosome, which represents vector of  integers
 */
public class Chromosome implements  Cloneable{
    private static final Random random = new Random();
    private final int CHROMOSOME_SIZE = 10; //基因个数
    private final int[] vector = new int[CHROMOSOME_SIZE]; //网格序号序列

    /**
     * 初始化个体
     * 地图设置为20*20的网格，x:[0,19]; y:[0,19]，则网格序号为: x+y*20
     */
    public void initialChromosome() {
        Random random = new Random();
        // x、y随机递增
        int[] x = new int[CHROMOSOME_SIZE];
        int[] y = new int[CHROMOSOME_SIZE];
        for(int j = 0; j < CHROMOSOME_SIZE; j++) {
            x[j] = random.nextInt(20);
            y[j] = random.nextInt(20);
        }
        Arrays.sort(x);
        Arrays.sort(y);
        // 生成个体
        for(int j = 0; j < CHROMOSOME_SIZE; j++) {
            this.vector[j] = x[j] + y[j] * 20;
        }
        // 起点和终点不参与进化，以防发生变异
        System.out.println(Arrays.toString(this.vector));
    }

    /**
     * Returns clone of current chromosome, which is mutated a bit
     * 基本位变异，随机选取一位
     */
    public Chromosome mutate() {
        Chromosome result = this.clone();
        int index = random.nextInt(this.vector.length);
        int mutationValue = random.nextInt(100) - random.nextInt(100);
        result.vector[index] += mutationValue;

        return result;
    }
    /**
     * Returns list of siblings <br/>
     * Siblings are actually new chromosomes, <br/>
     * created using any of crossover strategy
     * 单点交叉
     */
    public List<Chromosome> crossover(Chromosome other) {
        Chromosome thisClone = this.clone();
        Chromosome otherClone = other.clone();
        int index = random.nextInt(thisClone.CHROMOSOME_SIZE - 1);
        // 交叉
        for(int i = index; i < thisClone.vector.length; i++) {
            int tmpV = thisClone.vector[i];
            thisClone.vector[i] = otherClone.vector[i];
            otherClone.vector[i] = tmpV;
        }

        return Arrays.asList(thisClone, otherClone);
    }

    @Override
    protected Chromosome clone() {
        Chromosome clone = new Chromosome();
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
