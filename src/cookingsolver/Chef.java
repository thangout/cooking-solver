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
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

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

	// Limiit for nutritions
	double proteinMin = 500;
	double carbMin = 100;
	double fatMin = 100;
	double proteinMax = 1000;
	double carbMax = 800;
	double fatMax = 700;

	ArrayList<Recipe> willCookList;

	HashSet<Recipe> tabulist;
	LinkedList<Integer> tabulistHash;

	Basket basket;
	Basket origBasket;
	Cookbook cb;

	public Chef() {
		origBasket = new Basket(INGREDIENTS_FILE);
		basket = origBasket;
		cb = new Cookbook(origBasket, RECIPE_FILE);
		tabulist = new HashSet<>();
		tabulistHash = new LinkedList<>();
		willCookList = new ArrayList<>();
	}

	public void findOptimalCookList() {
		ArrayList<Recipe> bestList = null;
		float bestTotalPrice = 0;
		for (int j = 0; j < 1000; j++) {
			basket.resetBasket();
			willCookList.clear();

			while (!calculateNutritions(willCookList)) {
				//fill the list in cookbook by recipes that Can be cooked,
				//neighbor creating
				cb.fillCanBeCookedRecipes();

				//adding cheapest recipe
				if (cb.getCanBeCookedRecipes().size() > 0) {
//				Recipe recp = evaluateCandidateRecipesByPrice();
//				Recipe recp = evaluateCandidateRecipesByWeight();
					Recipe recp = evaluateCandidateRecipesByRandom();
//					Recipe recp = evaluateCandidateRecipesByPriceWeightRatio();
//					Recipe recp = evaluateCandidateRecipesByBasket();
					if (!(recp == null)) {
//						System.out.println(recp.name + "= add: " + willCookList.hashCode());
						addRecipeToCookList(recp);
						tabulistHash.add(willCookList.hashCode());
					} else {
						if (tabulistHash.size() > 7) {
						}
					}
				} else {
					break;
				}
			}

			float totalPrice = 0;
			for (int i = 0; i < willCookList.size(); i++) {
				Recipe recp = willCookList.get(i);
//				recp.printIngredients();
				totalPrice += recp.getPrice();
			}

//			System.out.println("**********************************");
//			System.out.println("Can eat for " + willCookList.size() + " day/s");
//			System.out.println("Total money spend " + totalPrice + ",-");
//			System.out.println("Remain weight in basket: " + basket.getWeight() + "g");

			if (bestList == null) {
				bestList = (ArrayList<Recipe>) willCookList.clone();
				bestTotalPrice = totalPrice;
			} else {
				if (totalPrice < bestTotalPrice) {
					bestList = (ArrayList<Recipe>) willCookList.clone();
					bestTotalPrice = totalPrice;
				}
			}
		}
		System.out.println("**********************************");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("Can eat for " + bestList.size() + " day/s");
		System.out.println("Total money spend " + bestTotalPrice + ",-");
		System.out.println(calculateNutritions(bestList));
//		System.out.println("Remain weight in basket: " + basket.getWeight() + "g");
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("**********************************");
		for (int i = 0; i < bestList.size(); i++) {
			Recipe recp = bestList.get(i);
			recp.printIngredients();
			System.out.println("Recipe NO-" + i);
		}
		System.out.println(tabulistHash.size());
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
		double alpha = 1;
		double beta = 1;
		float canBeCookedSize = -1;
		double koef = 0;
		Recipe bestRecipe = null;
		ArrayList<Recipe> tabuRecipes = new ArrayList<>();
		for (int i = 0; i < sizeCandidateList; i++) {
			cb.fillCanBeCookedRecipes();
			Recipe recp = cb.getRecipeToCook(i);
			if (i == 0) {
				koef = calculateKoeficient(recp, alpha, beta);
				addRecipeToCookList(recp);
				cb.fillCanBeCookedRecipes();
				if (!isTabuHash(willCookList.hashCode())) {
					bestRecipe = recp;
					canBeCookedSize = cb.getCanBeCookedRecipes().size();
				} else {
					tabuRecipes.add(recp);
				}
				removeRecipeFromCookList(recp);
			}
			addRecipeToCookList(recp);
			cb.fillCanBeCookedRecipes();
//			System.out.println(isTabuHash(willCookList.hashCode()));
			if (!isTabuHash(willCookList.hashCode()) && cb.getCanBeCookedRecipes().size() >= canBeCookedSize
				&& koef >= calculateKoeficient(recp, alpha, beta)) {

				bestRecipe = recp;
				koef = calculateKoeficient(recp, alpha, beta);
				canBeCookedSize = cb.getCanBeCookedRecipes().size();
			} else {
				tabuRecipes.add(recp);
			}

			removeRecipeFromCookList(recp);
		}
		if (bestRecipe == null) {
			bestRecipe = pickTabuRecipe(tabuRecipes);
			tabulistHash.poll();
		}

		return bestRecipe;
	}

	public double calculateKoeficient(Recipe recp, double alpha, double beta) {
		double koef = 0;
		double nutritions = recp.getProteins() + recp.getCarbs() + recp.getFats();
		double price = recp.getPriceWeightRatio();
//		double price = recp.getPrice();
		koef = alpha * nutritions - beta * price;
		return koef;
	}

	public boolean calculateNutritions(ArrayList<Recipe> list) {
		double proteins = 0;
		double carbs = 0;
		double fats = 0;
		for (int i = 0; i < list.size(); i++) {
			Recipe recp = list.get(i);
			proteins += recp.getProteins();
			carbs += recp.getCarbs();
			fats += recp.getFats();
		}

		if (proteins >= proteinMin && carbs >= carbMin && fats >= fatMin
			&& proteins <= proteinMax && carbs <= carbMax && fats <= fatMax) {
//			System.out.println(proteins + ":" + carbs + ":" + fats);
			return true;
		}
		return false;
	}

	public Recipe pickTabuRecipe(ArrayList<Recipe> list) {
		int sizeCandidateList = list.size();
		float recipePriceWeightRation = -1;
		Recipe bestRecipe = null;
		//find min weight recipe
		for (int i = 0; i < sizeCandidateList; i++) {
			Recipe recp = list.get(i);
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
		return bestRecipe;

	}

	public boolean isTabu(Recipe recipe) {
		return tabulist.contains(recipe);
	}

	public boolean isTabuHash(int hash) {
		return tabulistHash.contains(hash);
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
