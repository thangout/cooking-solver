/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cookingsolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Thang Do
 */
public class Chef {

	String INGREDIENTS_FILE = "ingredients_test.txt";

	ArrayList<Recipe> willCookList;

	Basket basket;
	Cookbook cb;

	public Chef() {
		basket = new Basket(INGREDIENTS_FILE);
		cb = new Cookbook(basket);
		willCookList = new ArrayList<>();
	}

	public void findOptimalCookList() {
		while (true) {
		//fill the list in cookbook by recipes that Can be cooked,
			//neighbor creating
			cb.fillCanBeCookedRecipes();

			//adding cheapest recipe
			if (cb.getCanBeCookedRecipes().size() > 0) {
				Recipe recp = evaluateCandidateRecipes();
				addRecipeToCookList(recp);
			} else {
				System.out.println("§end§");
				break;
			}
		}

	}

	public Recipe evaluateCandidateRecipes() {
		int sizeCandidateList = cb.getCanBeCookedRecipes().size();
		int recipePrice = -1;
		Recipe bestRecipe = null;
		//find min price recipe
		for (int i = 0; i < sizeCandidateList; i++) {
			Recipe recp = cb.getRecipeToCook(i);
			if (i == 0) {
				recipePrice = (int) recp.getPrice();
				bestRecipe = recp;
			}
			if (recp.getPrice() < recipePrice) {
				bestRecipe = recp;
				recipePrice = (int) recp.getPrice();
			}
		}
		return bestRecipe;
	}

	public void addRecipeToCookList(Recipe recp) {
		willCookList.add(recp);
		removeFromBasket(recp);
	}

	public void removeRecipeFromCookList(Recipe recp) {
		willCookList.remove(recp);
		addToBasket(recp);
	}

	public void removeFromBasket(Recipe recipe) {
		HashMap<Ingredient, Integer> usage = recipe.getIngredientsNeeded();
		Set<Entry<Ingredient, Integer>> uset = usage.entrySet();
		Iterator ite = uset.iterator();
		while (ite.hasNext()) {
			Entry<Ingredient, Integer> temp = (Entry<Ingredient, Integer>) ite.next();
			int weightNeeded = temp.getValue();
			basket.removeFromBasket(temp.getKey(), weightNeeded);
		}
	}

	public void addToBasket(Recipe recipe) {
		HashMap<Ingredient, Integer> usage = recipe.getIngredientsNeeded();
		Set<Entry<Ingredient, Integer>> uset = usage.entrySet();
		Iterator ite = uset.iterator();
		while (ite.hasNext()) {
			Entry<Ingredient, Integer> temp = (Entry<Ingredient, Integer>) ite.next();
			int weightNeeded = temp.getValue();
			basket.addToBasket(temp.getKey(), weightNeeded);
		}
	}

	public Cookbook getCookbook() {
		return cb;
	}

	public ArrayList<Recipe> getWillCookList() {
		return willCookList;
	}

	

	

}
