package com.angzh.ga;

import java.util.*;

public class Population implements Iterable<Chromosome> {

    private static final int MAX_POPULATION_SIZE = 32;

    private List<Chromosome> chromosomes = new ArrayList<Chromosome>(MAX_POPULATION_SIZE);

    private final Random random = new Random();

    // 新增个体
    public void addChromosome(Chromosome chromosome) {
        this.chromosomes.add(chromosome);
    }

    // 获取种群规模
    public int getSize() {
        return this.chromosomes.size();
    }

    // 获取种群中随机个体
    public Chromosome getRandomChromosome() {
        int numOfChromosomes = this.chromosomes.size();
        //TODO improve random generator
        //maybe use pattern strategy
        int index = this.random.nextInt(numOfChromosomes);
        return this.chromosomes.get(index);
    }

    // 获取种群中指定个体
    public Chromosome getChromosomeByIndex(int index) {
        return this.chromosomes.get(index);
    }

    // 去重
//    public void deleteDuplicate() {
//
//    }

    // 种群中个体排序
    public void sortPopulationByFitness(ChromosomesComparator chromosomesComparator) {
        Collections.shuffle(this.chromosomes);
        Collections.sort(this.chromosomes, chromosomesComparator);
    }

    // 缩减种群
    public void trim(int len) {
        this.chromosomes = this.chromosomes.subList(0, len);
    }

    @Override
    public Iterator<Chromosome> iterator() {
        return this.chromosomes.iterator();
    }
}
