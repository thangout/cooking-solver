/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cookingsolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

/**
 *
 * @author Thang Do
 */
public class Chef {

	//test data
//	String INGREDIENTS_FILE = "ingredients_test.txt";
	String RECIPE_FILE = "recipes.txt";

	//real data
	String INGREDIENTS_FILE = "ingredients.txt";
//	String RECIPE_FILE = "recipes.txt";

	ArrayList<Recipe> willCookList;

	HashSet<Recipe> tabulist;

	Basket basket;
	Cookbook cb;

	public Chef() {
		basket = new Basket(INGREDIENTS_FILE);
		cb = new Cookbook(basket, RECIPE_FILE);
		tabulist = new HashSet<>();
		willCookList = new ArrayList<>();
	}

	public void findOptimalCookList() {
		while (true) {
			//fill the list in cookbook by recipes that Can be cooked,
			//neighbor creating
			cb.fillCanBeCookedRecipes();

			//adding cheapest recipe
			if (cb.getCanBeCookedRecipes().size() > 0) {
//				Recipe recp = evaluateCandidateRecipesByPrice();
//				Recipe recp = evaluateCandidateRecipesByWeight();
//				Recipe recp = evaluateCandidateRecipesByRandom();
//				Recipe recp = evaluateCandidateRecipesByPriceWeightRatio();
				Recipe recp = evaluateCandidateRecipesByBasket();

				addRecipeToCookList(recp);
			} else {
				System.out.println("§end§");
				break;
			}
		}

		float totalPrice = 0;
		for (int i = 0; i < willCookList.size(); i++) {
			Recipe recp = willCookList.get(i);
			recp.printIngredients();
			totalPrice += recp.getPrice();
		}

		System.out.println("**********************************");
		System.out.println("Can eat for " + willCookList.size() + " day/s");
		System.out.println("Total money spend " + totalPrice + ",-");
		System.out.println("Remain weight in basket: " + basket.getWeight() + "g");
		System.out.println("**********************************");

	}

	public Recipe evaluateCandidateRecipesByPrice() {
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
				if (!isTabu(recp)) {
					bestRecipe = recp;
					recipePrice = (int) recp.getPrice();
				}
			}
		}
//		tabulist.add(bestRecipe);
		return bestRecipe;
	}

	public Recipe evaluateCandidateRecipesByWeight() {
		int sizeCandidateList = cb.getCanBeCookedRecipes().size();
		int recipeWeight = -1;
		Recipe bestRecipe = null;
		//find min weight recipe
		for (int i = 0; i < sizeCandidateList; i++) {
			Recipe recp = cb.getRecipeToCook(i);
			if (i == 0) {
				recipeWeight = recp.getWeight();
				bestRecipe = recp;
			}
			if (recp.getWeight() < recipeWeight) {
				recipeWeight = recp.getWeight();
				bestRecipe = recp;
			}
		}
		return bestRecipe;
	}

	public Recipe evaluateCandidateRecipesByPriceWeightRatio() {
		int sizeCandidateList = cb.getCanBeCookedRecipes().size();
		float recipePriceWeightRation = -1;
		Recipe bestRecipe = null;
		//find min weight recipe
		for (int i = 0; i < sizeCandidateList; i++) {
			Recipe recp = cb.getRecipeToCook(i);
			if (i == 0) {
				recipePriceWeightRation = recp.getPriceWeightRatio();
				bestRecipe = recp;
			}
			if (recp.getPriceWeightRatio() < recipePriceWeightRation) {
				if (!isTabu(recp)) {
					recipePriceWeightRation = recp.getPriceWeightRatio();
					bestRecipe = recp;
				}
			}
		}
//		tabulist.add(bestRecipe);
		return bestRecipe;
	}

	public Recipe evaluateCandidateRecipesByBasket() {
		int sizeCandidateList = cb.getCanBeCookedRecipes().size();
		float canBeCookedSize = -1;
		float price = 0;
		Recipe bestRecipe = null;
		for (int i = 0; i < sizeCandidateList; i++) {
			cb.fillCanBeCookedRecipes();
			Recipe recp = cb.getRecipeToCook(i);
			if (i == 0) {
				bestRecipe = recp;
				price = recp.getPrice();
				addRecipeToCookList(recp);
				cb.fillCanBeCookedRecipes();
				canBeCookedSize = cb.getCanBeCookedRecipes().size();
				removeRecipeFromCookList(recp);
			}

			addRecipeToCookList(recp);
			cb.fillCanBeCookedRecipes();

			if (!isTabu(recp)&&cb.getCanBeCookedRecipes().size() >= canBeCookedSize && recp.getPrice() < price ) {
				bestRecipe = recp;
				price = recp.getPrice();
				canBeCookedSize = cb.getCanBeCookedRecipes().size();
			}

			removeRecipeFromCookList(recp);
		}
//		tabulist.add(bestRecipe);
		return bestRecipe;
	}

	public boolean isTabu(Recipe recipe) {
		return tabulist.contains(recipe);
	}

	public Recipe evaluateCandidateRecipesByRandom() {
		int sizeCandidateList = cb.getCanBeCookedRecipes().size();
		int randomIndex = (int) (Math.random() * sizeCandidateList);
		return cb.getRecipeToCook(randomIndex);
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
