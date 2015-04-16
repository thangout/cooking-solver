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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thang Do
 */
public class Cookbook {

	String RECIPE_FILE = "recipe.txt";
	ArrayList<Recipe> recipes;
	Basket basket;

	public Cookbook(Basket basket) {
		this.basket = basket;
		recipes = new ArrayList<>();
		try {
			parseRecipes();
		} catch (IOException ex) {
			Logger.getLogger(Cookbook.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void parseRecipes() throws IOException {
		FileInputStream fstream = new FileInputStream(RECIPE_FILE);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String line = "";
		while ((line = br.readLine()) != null) {
			String recipeName = line;
			Recipe recp = new Recipe(recipeName);
			while (!" ".equals(line = br.readLine())) {
				String[] temp = line.trim().split(",");
				String ingName = temp[0];
				int weight = Integer.parseInt(temp[1]);
				recp.addIngredient(basket.getIngredientByName(ingName), weight);
			}
			recipes.add(recp);
		}
	}
}
