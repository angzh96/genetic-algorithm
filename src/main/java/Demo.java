/*******************************************************************************
 * Copyright 2012 Yuriy Lagodiuk
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

import com.lagodiuk.ga.Fitness;
import com.lagodiuk.ga.GeneticAlgorithm;
import com.lagodiuk.ga.IterartionListener;
import com.lagodiuk.ga.Population;
import com.lagodiuk.ga.MyVector;
import com.lagodiuk.ga.MyVectorFitness;

public class Demo {

	public static void main(String[] args) {
		Population<MyVector> population = createInitialPopulation(10);

		Fitness<MyVector, Double> fitness = new MyVectorFitness();

		GeneticAlgorithm<MyVector, Double> ga = new GeneticAlgorithm<MyVector, Double>(population, fitness);

		addListener(ga);

		ga.evolve(500);
	}

	/**
	 * The simplest strategy for creating initial population <br/>
	 * in real life it could be more complex
	 */
	private static Population<MyVector> createInitialPopulation(int populationSize) {
		Population<MyVector> population = new Population<MyVector>();
		MyVector base = new MyVector();
		for (int i = 0; i < populationSize; i++) {
			// each member of initial population
			// is mutated clone of base chromosome
			MyVector chr = base.mutate();
			population.addChromosome(chr);
		}
		return population;
	}

	/**
	 * After each iteration Genetic algorithm notifies listener
	 */
	private static void addListener(GeneticAlgorithm<MyVector, Double> ga) {
		// just for pretty print
		System.out.println(String.format("%s\t%s\t%s", "iter", "fit", "chromosome"));

		// Lets add listener, which prints best chromosome after each iteration
		ga.addIterationListener(new IterartionListener<MyVector, Double>() {

			private final double threshold = 1e-5;

			@Override
			public void update(GeneticAlgorithm<MyVector, Double> ga) {

				MyVector best = ga.getBest();
				double bestFit = ga.fitness(best);
				int iteration = ga.getIteration();

				// Listener prints best achieved solution
				System.out.println(String.format("%s\t%s\t%s", iteration, bestFit, best));

				// If fitness is satisfying - we can stop Genetic algorithm
				if (bestFit < this.threshold) {
					ga.terminate();
				}
			}
		});
	}
}
