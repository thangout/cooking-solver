/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cookingsolver.Basket;
import cookingsolver.Cookbook;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Thang Do
 */
public class CookbookTest {

	Cookbook cb;

	public CookbookTest() {
		Basket basket = new Basket("ingredients_test.txt");
		cb = new Cookbook(basket);
	}

	@Test
	public void canICookRecipe() {
		boolean canCook = cb.canICookRecipe(cb.getRecipe(0));
		assertTrue(canCook);

		canCook = cb.canICookRecipe(cb.getRecipe(1));
		assertFalse(canCook);
	}

	@Test
	public void fillCanBeCookedRecipes(){
		assertNull(cb.getCanBeCookedRecipes());
		cb.fillCanBeCookedRecipes();
		int sizeOfCanBeCookedRecipes = cb.getCanBeCookedRecipes().size();
		assertEquals(sizeOfCanBeCookedRecipes,1);

	}
}
