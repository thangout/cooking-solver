/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cookingsolver.Ingredient;
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
public class IngredientTest {

	public IngredientTest() {
	}

	@Test
	public void price() {
		int weight = 200;
		int price = 100;
		Ingredient in = new Ingredient("test", weight, price);
		assertEquals((float) price / weight, in.getPrice(), 0.0002);
	}
}
