/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cookingsolver.Basket;
import cookingsolver.Ingredient;
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
public class BasketTest {

	Basket basket;

	public BasketTest() {
		basket = new Basket();
	}

	@Test
	public void getIngredientByName() {
		Ingredient ing = basket.getIngredientByName("Ketchup");
		assertNotNull(ing);

		ing = basket.getIngredientByName("qsd");
		assertNull(ing);
	}

	@Test
	public void hasEnoughIngredient(){
		Ingredient ing = basket.getIngredientByName("Ketchup");
		boolean a = basket.hasEnoughIngredient(ing,1);
		assertEquals(a, true);

		boolean b = basket.hasEnoughIngredient(ing,100000000);
		assertEquals(b, false);
	}
}
