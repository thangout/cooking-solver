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

/**
 *
 * @author Thang Do
 */
public class CookingSolver {

	static ArrayList<Ingredient> ingredients;
	static HashMap<String, Ingredient> ingredientMap;
	static ArrayList<Recipe> recipes;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws IOException {
		ingredients = new ArrayList<>();
		ingredientMap = new HashMap<>();
		recipes = new ArrayList<>();
		parseIngredients();
	}

	public static void parseIngredients() throws IOException {
		FileInputStream fstream = new FileInputStream("ingredients.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String line = "";
		while ((line = br.readLine()) != null) {
			String[] temp = line.trim().split(",");
			String name = temp[0];
			String[] values = temp[1].trim().split(" ");
			int weight = Integer.parseInt(values[0]);
			int price = Integer.parseInt(values[1]);
			Ingredient ing = new Ingredient(name, weight, price);
			ingredientMap.put(name, ing);
		}
	}

	public static void parseRecipes() throws IOException {
		FileInputStream fstream = new FileInputStream("recipes.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String line = "";
		while ((line = br.readLine()) != null) {
			String recipeName = line;
			Recipe recp = new Recipe(recipeName);
			while (!" ".equals(line = br.readLine())) {
				String[] temp = line.trim().split(",");
				String ingName = temp[0];
				int weight = Integer.parseInt(temp[1]);
				recp.addIngredient(ingredientMap.get(ingName), weight);
			}
			recipes.add(recp);
		}
	}
}
