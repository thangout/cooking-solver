/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cookingsolver;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Thang Do
 */
public class CookingSolver {

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		String[] ingredientsName = {"testoviny","hovezi maso","rajce","cibule","okurka","chleba","vajicko"};
		ArrayList<Ingredient> ingredients = new ArrayList<>();
		ArrayList<Recipe> recipes = new ArrayList<>();
		Random rnd = new Random(5);
		for (int i = 0; i < ingredientsName.length; i++) {
			int price = (int) (rnd.nextDouble()*500+1);
			int weight = (int) (rnd.nextDouble()*1000+1);
			Ingredient temp = new Ingredient(ingredientsName[i],weight,price);
			ingredients.add(temp);
		}

		for (int i = 0; i < 5; i++) {
			Recipe temp = new Recipe();
			int randomIndex = (int) (rnd.nextDouble()*ingredients.size());
			for (int j = 0; j < (int)(rnd.nextDouble()*100 + 1); j++) {
				temp.addIngredient(ingredients.get(randomIndex),100);
			}
			recipes.add(temp);
		}
		
		for (int i = 0; i < recipes.size(); i++) {
//			System.out.println(recipes.get(i).getPrice());	
		}
		Solver solver = new Solver(ingredients);
//		System.out.println(solver.getRecipeSize());
		solver.printRecipes();
	}
	
}
