package com.angzh.ga;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GeneticAlgorithm {
    private static final int ALL_PARENTAL_CHROMOSOMES = Integer.MAX_VALUE;

    // 种群
    // Population类
    private Population population;

    // 终止条件
    private boolean terminate = false;

    // 每次进化保留的上一代的个体数目，初始为最大整数值
    private int parentChromosomesSurviveCount = ALL_PARENTAL_CHROMOSOMES;

    // 迭代次数
    private int iteration = 0;

    // 个体比较器
    // ChromosomesComparator类
    private final ChromosomesComparator chromosomesComparator = new ChromosomesComparator();

    // 迭代监听
    // IterationListener类列表
//    private final List<IterationListener> iterationListeners = new LinkedList<IterationListener>();
    private final IterationListener listener = new IterationListener();

    // 构造函数，参数为种群
    public GeneticAlgorithm(Population population) {
        this.population = population;
        this.population.sortPopulationByFitness(this.chromosomesComparator);
    }

    // 遗传进化
    public void evolve() {
        // 进化前种群规模
        int parentPopulationSize = this.population.getSize();

        Population newPopulation = new Population();

        for(int i = 0; (i < parentPopulationSize) && (i < this.parentChromosomesSurviveCount); i++) {
            newPopulation.addChromosome(this.population.getChromosomeByIndex(i));
        }

        // 对种群中的个体进行交叉变异操作
        for(int i = 0; i < parentPopulationSize - 1; i += 2) {
            Chromosome chromosome = this.population.getChromosomeByIndex(i);
            Chromosome mateChromosome = this.population.getChromosomeByIndex(i+1);

            List<Chromosome> crossovered = chromosome.crossover(mateChromosome);
            for(Chromosome chr : crossovered) {
                newPopulation.addChromosome(chr);
            }

            Chromosome mutated = crossovered.get(0).mutate();
            newPopulation.addChromosome(mutated);
        }

        // 新种群去重排序，并修改新种群规模为原种群大小
//        newPopulation.deleteDuplicate();
        newPopulation.sortPopulationByFitness(this.chromosomesComparator);
        newPopulation.trim(parentPopulationSize);
        this.population = newPopulation;
    }

    // 多次遗传进化
    public void multiEvolve(int count) {
        this.terminate = false;
        double[] bestFits = new double[count];

        for(int i = 0; i < count; i++) {
            // 终止进化
            if(this.terminate) {
                break;
            }
            this.evolve();
            // 迭代次数
            this.iteration = i;
            // 更新输出每次迭代的结果
            bestFits[i] = listener.update(this);
//            for(IterationListener listener : this.iterationListeners) {
//                listener.update(this);
//            }
        }
        System.out.println("==========最好的适应度值数组========");
        String bestFitsStr = Arrays.toString(bestFits);
        System.out.println(bestFitsStr);

        // 输出到文件
        try {
            File file = new File("/Users/zxy/Desktop/科研/论文实验/bestFits.txt");
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(bestFitsStr.substring(1,bestFitsStr.length() - 1));
            bw.close();
            System.out.println("File Write Done");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 获取迭代次数
    public int getIteration() {
        return this.iteration;
    }

    // 终止函数
    public void terminate() {
        this.terminate = true;
    }

    // 获取种群
    public Population getPopulation() {
        return this.population;
    }

    // 获取最优个体
    public Chromosome getBest() {
        return this.population.getChromosomeByIndex(0);
    }

    // 获取最差个体
    public Chromosome getWorst() {
        return this.population.getChromosomeByIndex(this.population.getSize() - 1);
    }

    // 设置存活数量
    public void setParentChromosomesSurviveCount(int parentChromosomesCount) {
        this.parentChromosomesSurviveCount = parentChromosomesCount;
    }

    // 获取存活数量
    public int getParentChromosomesSurviveCount() {
        return this.parentChromosomesSurviveCount;
    }

    // 添加迭代监听器
//    public void addIterationListener(IterationListener listener) {
//        this.iterationListeners.add(listener);
//    }

    // 移除迭代监听器
//    public void removeIterationListener(IterationListener listener) {
//        this.iterationListeners.remove(listener);
//    }

    // 适应度计算，首先去个体比较器的cache中寻找
    public double fitness(Chromosome chromosome) {
        return this.chromosomesComparator.fit(chromosome);
    }

    // 清除缓存
    public void clearCache() {
        this.chromosomesComparator.clearCache();
    }
}
