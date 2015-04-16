/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

	@BeforeClass
	public static void setUpClass() {
	}

	@AfterClass
	public static void tearDownClass() {
	}

	@Before
	public void setUp() {
	}

	@After
	public void tearDown() {
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
}
