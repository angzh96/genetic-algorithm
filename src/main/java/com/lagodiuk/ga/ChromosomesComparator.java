package com.lagodiuk.ga;

import java.util.Comparator;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * 个体比较类，根据染色体适应度进行比较，适应度越小越好
 * Assume that chromosome1 is better than chromosome2 <br/>
 * fit1 = calculate(chromosome1) <br/>
 * fit2 = calculate(chromosome2) <br/>
 * So the following condition must be true <br/>
 * fit1.compareTo(fit2) <= 0 <br/>
 */
public class ChromosomesComparator implements Comparator<Chromosome> {

    //cache用来缓存计算过适应度的个体
    private final Map<Chromosome, Double> cache = new WeakHashMap<Chromosome, Double>();

    private final ChromosomeFitness fitnessFunc = new ChromosomeFitness();

    //适应度计算
    public double fit(Chromosome chr) {
        Double fit = this.cache.get(chr);
        if(fit == null) {
            fit = this.fitnessFunc.calculate(chr);
            this.cache.put(chr, fit);
        }
        return fit;
    }

    @Override
    public int compare(Chromosome chr1, Chromosome chr2) {
        Double fit1 = this.fit(chr1);
        Double fit2 = this.fit(chr2);
        int ret = fit1.compareTo(fit2);
        return ret;
    }

    public void clearCache() {
        this.cache.clear();
    }
}
