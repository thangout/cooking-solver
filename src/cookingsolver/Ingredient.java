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

	public Ingredient(String name, int weight, int price) {
		this.name = name;
		this.weight = weight;
		this.price = price;
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
}
