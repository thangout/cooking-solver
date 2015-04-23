/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cookingsolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Thang Do
 */
public class Recipe {

	HashMap usage;
	String name;

	public Recipe(String name) {
		this.name = name;
		//<Ingredient, weight usage>
		usage = new HashMap<Ingredient, Integer>();
	}

	public void addIngredient(Ingredient ing, int amount) {
		usage.put(ing, amount);
	}

	public void removeIngredient(Ingredient ing) {
		usage.remove(ing);
	}

	public float getPrice() {
		Set<Entry<Ingredient, Integer>> entrySet = usage.entrySet();
		float price = 0;
		for (Entry<Ingredient, Integer> temp : entrySet) {
			price += (temp.getKey().getPrice() * (float) temp.getValue());
		}
		return price;
	}

	public int getWeight() {
		Set<Entry<Ingredient, Integer>> entrySet = usage.entrySet();
		int weight = 0;
		for (Entry<Ingredient, Integer> temp : entrySet) {
			weight += temp.getValue();
		}
		return weight;
	}

	public void printIngredients() {
		Set<Entry<Ingredient, Integer>> entrySet = usage.entrySet();
		Iterator ite = entrySet.iterator();
		System.out.println("______________");
		System.out.println(name);
		while (ite.hasNext()) {
			Entry<Ingredient, Integer> temp = (Entry<Ingredient, Integer>) ite.next();
			System.out.println(temp.getKey().getName() + " : " + temp.getValue() + "g");
		}
		System.out.println("Price: " + getPrice() + ",-");
	}

	public HashMap getIngredientsNeeded() {
		return usage;
	}
}
