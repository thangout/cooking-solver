/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cookingsolver;

/**
 *
 * @author Thang Do
 */
public class Ingredient {

	private String name;
	int weight;
	int price;
	
	/* Amount in 100g */
	double protein;
	double carbohydrate;
	double fat;

	public Ingredient(String name, int weight, int price) {
		this.name = name;
		this.weight = weight;
		this.price = price;
	}

	public Ingredient(String name, int weight, int price,double protein,
		double carb, double fat) {
		this.name = name;
		this.weight = weight;
		this.price = price;
		this.protein = protein;
		this.carbohydrate = carb;
		this.fat = fat;
	}

	/**
	 *
	 * @return a price for 1g
	 */
	public double getPrice() {
		return price / (float) weight;
	}

	public String getName() {
		return name;
	}

	public int getWeight() {
		return weight;
	}

	public double getProtein() {
		return protein;
	}

	public double getCarbohydrate() {
		return carbohydrate;
	}

	public double getFat() {
		return fat;
	}
}
