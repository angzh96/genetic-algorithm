
import com.angzh.ga.Chromosome;
import com.angzh.ga.GeneticAlgorithm;
import com.angzh.ga.IterationListener;
import com.angzh.ga.Population;

import java.util.Arrays;
import java.util.Random;

public class Demo {

	public static void main(String[] args) {
		System.out.println("===================初始化种群====================");
		Population population = createInitialPopulation(50);

		GeneticAlgorithm ga = new GeneticAlgorithm(population);

		System.out.println("================每次进化的最优个体=================");
		System.out.println(String.format("%s\t%s\t%s", "iter", "fitness", "chromosome"));

		// 添加迭代监听器
//		ga.addIterationListener(new IterationListener());

		// 多次遗传进化
		ga.multiEvolve(200);
	}

	/**
	 * 初始化种群
	 */
	private static Population createInitialPopulation(int populationSize) {
		Population population = new Population();
		for (int i = 0; i < populationSize; i++) {
			Chromosome chromosome = new Chromosome();
			System.out.println("第" + (i+1) + "个个体：");
			chromosome.initialChromosome();
			population.addChromosome(chromosome);
		}
		return population;
	}
}
