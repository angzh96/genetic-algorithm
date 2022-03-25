package com.angzh.ga;

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

    // cache用来缓存计算过的<个体，适应度>
    private final Map<Chromosome, Double> cache = new WeakHashMap<Chromosome, Double>();

    // 适应度计算类，计算个体的适应度
    private final ChromosomeFitness fitnessFunc = new ChromosomeFitness();

    // 若chr已存在cache里，直接取出对应适应度；若不在，则计算其适应度并存入cache
    public double fit(Chromosome chr) {
        Double fit = this.cache.get(chr);
        if(fit == null) {
            fit = this.fitnessFunc.calculate(chr);
            this.cache.put(chr, fit);
        }
        return fit;
    }

    // 重写对chromosome的比较
    @Override
    public int compare(Chromosome chr1, Chromosome chr2) {
        Double fit1 = this.fit(chr1);
        Double fit2 = this.fit(chr2);
        int ret = fit1.compareTo(fit2);
        return ret;
    }

    // 清空缓存
    public void clearCache() {
        this.cache.clear();
    }
}
