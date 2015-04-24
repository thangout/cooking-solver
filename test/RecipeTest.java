/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cookingsolver.Basket;
import cookingsolver.Ingredient;
import cookingsolver.Recipe;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Thang Do
 */
public class RecipeTest {

	public RecipeTest() {
	}

	@Test
	public void price() {
		Ingredient in1 = new Ingredient("test", 100, 100);
		Ingredient in2 = new Ingredient("test2", 100, 100);

		Recipe rec = new Recipe("testname");
		rec.addIngredient(in1, 100);
		rec.addIngredient(in2, 100);
		assertEquals(200, rec.getPrice(), 0.0002f);
	}

	@Test
	public void getPriceWeightRatio(){
		int weight = 100;
		int price = 150;
		Ingredient in1 = new Ingredient("test",  weight,price);
		Ingredient in2 = new Ingredient("test2",  weight,price);
		Recipe rec = new Recipe("testname");

		rec.addIngredient(in1, weight);
		rec.addIngredient(in2, weight);
		float ratio = rec.getPriceWeightRatio();
		assertEquals((((float)price/weight)*(weight*2))/(2*weight), ratio, 0.000f);
	}
}
