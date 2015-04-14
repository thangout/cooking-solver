/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cookingsolver;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thang Do
 */
public class Solver {

	List<Ingredient> ingredients;
	List<Recipe> recipes;

	//number of ingredients i want to cook from
	int k = 2;
	//number of days I want to cook
	int days = 7;

	public Solver(List ingredients) {
		this.ingredients = ingredients;
		recipes = new ArrayList<Recipe>();
		int[] v = new int[ingredients.size()];
//		int[] v = new int[3]; 
		int n = v.length;
		combinations(v, 1, n, 1, k);
	}

	public void initFeasibleSolutions() {
	}

	void combinations(int v[], int start, int n, int k, int maxk) {
		int i;

		/* k here counts through positions in the maxk-element v.
		 * if k > maxk, then the v is complete and we can use it.
		 */
		if (k > maxk) {
			/* insert code here to use combinations as you please */
			Recipe recp = new Recipe();
			for (i = 1; i <= maxk; i++) {
				Ingredient ing = ingredients.get(v[i]-1);
				recp.addIngredient(ing,ing.weight/days );
//				System.out.print(v[i]);
			}
			recipes.add(recp);
//			System.out.println("\n");
			return;
		}

		/* for this k'th element of the v, try all start..n
		 * elements in that position
		 */
		for (i = start; i <= n; i++) {

			v[k] = i;

			/* recursively generate combinations of integers
			 * from i+1..n
			 */
			combinations(v, i + 1, n, k + 1, maxk);
		}
	}

	public int getRecipeSize() {
		return recipes.size();
	}

	public void printRecipes() {
		for (int i = 0; i < recipes.size(); i++) {
			recipes.get(i).printIngredients();
		}
	}

}
