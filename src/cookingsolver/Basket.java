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
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Thang Do
 */
public class Basket implements Cloneable {

	String INGREDIENTS_FILE;

	//<Ingredient,weightWeBought>
	HashMap<Ingredient, Integer> ingredientBought;
	HashMap<Ingredient, Integer> ingredientBoughtForReset;
	ArrayList<String> ingredientName;

	public Basket(String fileName) {
		INGREDIENTS_FILE = fileName;
		ingredientBought = new HashMap<>();
		ingredientName = new ArrayList<>();
		try {
			parseIngredients();
		} catch (IOException ex) {
			Logger.getLogger(Basket.class.getName()).log(Level.SEVERE, null, ex);
		}
		ingredientBoughtForReset = (HashMap<Ingredient, Integer>) ingredientBought.clone();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public void parseIngredients() throws IOException {
		FileInputStream fstream = new FileInputStream(INGREDIENTS_FILE);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String line = "";
		while ((line = br.readLine()) != null) {
			String[] temp = line.trim().split(",");
			String name = temp[0];
			ingredientName.add(name);
			String[] values = temp[1].trim().split(" ");
			int weight = Integer.parseInt(values[0]);
			//GUSEK PRINT DATA
//			System.out.println(name.replaceAll(" ", "") + "\t 0 \t" + weight );
//			System.out.print(name.replaceAll(" ", "")+ " ");
			int price = Integer.parseInt(values[1]);
			double protein = Double.parseDouble(values[2]);
			double carb = Double.parseDouble(values[3]);
			double fat = Double.parseDouble(values[4]);
//			Ingredient ing = new Ingredient(name.toLowerCase(), weight, price);
			Ingredient ing = new Ingredient(name.toLowerCase(), weight, price,
				protein, carb, fat);
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
		return new Ingredient("You dont have in basket : " + name, 0, 0);
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

	public int getWeight() {
		Set<Map.Entry<Ingredient, Integer>> entrySet = ingredientBought.entrySet();
		int weight = 0;
		for (Map.Entry<Ingredient, Integer> temp : entrySet) {
			weight += temp.getValue();
		}
		return weight;
	}

	public void printContent() {
		Set<Map.Entry<Ingredient, Integer>> entrySet = ingredientBought.entrySet();
		for (Map.Entry<Ingredient, Integer> temp : entrySet) {
			System.out.println(temp.getKey().getName() + " : " + temp.getValue() + "g");
		}
	}

	/**
	 * Refill the basket as it was unused
	 */
	public void resetBasket() {
		ingredientBought = (HashMap<Ingredient, Integer>) ingredientBoughtForReset.clone();
	}

	public ArrayList<String> getIngredientName() {
		return ingredientName;
	}

}
