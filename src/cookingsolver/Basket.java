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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thang Do
 */
public class Basket {

	String INGREDIENTS_FILE;

	//<Ingredient,weightWeBought>
	HashMap<Ingredient, Integer> ingredientBought;

	public Basket(String fileName) {
		INGREDIENTS_FILE = fileName;
		ingredientBought = new HashMap<>();
		try {
			parseIngredients();
		} catch (IOException ex) {
			Logger.getLogger(Basket.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void parseIngredients() throws IOException {
		FileInputStream fstream = new FileInputStream(INGREDIENTS_FILE);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String line = "";
		while ((line = br.readLine()) != null) {
			String[] temp = line.trim().split(",");
			String name = temp[0];
			String[] values = temp[1].trim().split(" ");
			int weight = Integer.parseInt(values[0]);
			int price = Integer.parseInt(values[1]);
			Ingredient ing = new Ingredient(name, weight, price);
			ingredientBought.put(ing, weight);
		}
	}

	public Ingredient getIngredientByName(String name) {
		Iterator ite = ingredientBought.keySet().iterator();
		while (ite.hasNext()) {
			Ingredient ing = (Ingredient) ite.next();
			if (ing.getName().equals(name)) {
				return ing;
			}
		}
		return null;
	}

	public boolean hasEnoughIngredient(Ingredient ing, int amountNeeded) {
		int amountInBasket = ingredientBought.get(ing);
		if (amountNeeded <= amountInBasket) {
			return true;
		} else {
			return false;
		}
	}

	public void addToBasket(Ingredient ing, int amount) {
		int inBasketAmount = ingredientBought.get(ing);
		ingredientBought.put(ing, inBasketAmount + amount);
	}

	public void removeFromBasket(Ingredient ing, int amount) {
		int inBasketAmount = ingredientBought.get(ing);
		ingredientBought.put(ing, inBasketAmount - amount);
	}

}
