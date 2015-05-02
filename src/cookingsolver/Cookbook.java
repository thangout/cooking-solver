/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cookingsolver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thang Do
 */
public class Cookbook {

	String RECIPE_FILE;
	ArrayList<Recipe> recipes;
	ArrayList<Recipe> canBeCookedRecipes;
	Basket basket;

	public Cookbook(Basket basket, String fileName) {
		this.basket = basket;
		this.RECIPE_FILE = fileName;
		recipes = new ArrayList<>();
		try {
			parseRecipes();
		} catch (IOException ex) {
			Logger.getLogger(Cookbook.class.getName()).log(Level.SEVERE, null, ex);
		}
//		printRecipesForGusek();
	}

	public void parseRecipes() throws IOException {
		FileInputStream fstream = new FileInputStream(RECIPE_FILE);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String line = "";
		while ((line = br.readLine()) != null) {
			String recipeName = line;
			Recipe recp = new Recipe(recipeName);
			while (!"".equals(line = br.readLine()) && !(line == null)) {
				String[] temp = line.trim().split(",");
				String ingName = temp[0].toLowerCase();
				int weight = Integer.parseInt(temp[1].trim());
				recp.addIngredient(basket.getIngredientByName(ingName), weight);
			}
			recipes.add(recp);
		}
	}

	/**
	 * Check ingredients in basket for a recipe
	 *
	 * @param recipe
	 * @return true if basket has enough ingredients to cook given recipe
	 */
	public boolean canICookRecipe(Recipe recipe) {
		HashMap<Ingredient, Integer> usage = recipe.getIngredientsNeeded();
		Set<Entry<Ingredient, Integer>> uset = usage.entrySet();
		Iterator ite = uset.iterator();
		while (ite.hasNext()) {
			Entry<Ingredient, Integer> temp = (Entry<Ingredient, Integer>) ite.next();
			int weightNeeded = temp.getValue();
			if (!basket.hasEnoughIngredient(temp.getKey(), weightNeeded)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Get recipe at given index
	 *
	 * @param index
	 * @return recipe
	 */
	public Recipe getRecipe(int index) {
		return recipes.get(index);
	}

	/**
	 * Finds all recipes that can be cooked from basket ingredients and fill
	 * them to canBeCookedRecipes list
	 */
	public void fillCanBeCookedRecipes() {
		Iterator ite = recipes.iterator();
		canBeCookedRecipes = new ArrayList<>();
		while (ite.hasNext()) {
			Recipe recp = (Recipe) ite.next();
			if (canICookRecipe(recp)) {
				canBeCookedRecipes.add(recp);
			}
		}
	}

	public ArrayList<Recipe> getCanBeCookedRecipes() {
		return canBeCookedRecipes;
	}

	public Recipe getRecipeToCook(int index) {
		return canBeCookedRecipes.get(index);
	}

	public void printRecipesForGusek() {
		Iterator ite = recipes.iterator();
		ArrayList<String> ingredients = basket.getIngredientName();

		for (int i = 0; i < ingredients.size(); i++) {
			String ingName = ingredients.get(i);
//			String parsedName = ingName.replaceAll(" ","");
			System.out.print(ingName.replaceAll(" ","") + "\t");

			for (int j = 0; j < recipes.size(); j++) {
				Recipe recp = recipes.get(j);
				if (recp.getIngredientsNeeded().containsKey(basket.getIngredientByName(ingName.toLowerCase()))) {
					HashMap<Ingredient, Integer> map = recp.getIngredientsNeeded();
					System.out.print(map.get(basket.getIngredientByName(ingName.toLowerCase())) + "\t");	
				}else{
					System.out.print("0\t");	
				}
			}
			System.out.println("");

		}
		while (ite.hasNext()) {
			Recipe recp = (Recipe) ite.next();
			/*Recipe and price*/
			String name = recp.getName().replaceAll(" ", "");
//			System.out.println(name + " " + recp.getPrice());
//			System.out.print(name + " ");
			/* print nutritions */
//			System.out.print(recp.getProteins() + " ");
//			System.out.print(recp.getCarbs()+ " ");
//			System.out.print(recp.getFats()+ " ");
		}

		while (ite.hasNext()) {
			Recipe recp = (Recipe) ite.next();
			/*Recipe and price*/
			String name = recp.getName().replaceAll(" ", "");
		}

		while (ite.hasNext()) {
			Recipe recp = (Recipe) ite.next();
			/*Recipe and price*/
			String name = recp.getName().replaceAll(" ", "");
		}

		System.out.println("__________________");
	}
}
