/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import cookingsolver.Chef;
import cookingsolver.Cookbook;
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
public class ChefTest {

	Chef chef;

	public ChefTest() {
		chef = new Chef();
	}

	@Test
	public void evaluateCandidateRecipes() {
		Cookbook cb = chef.getCookbook();
		cb.fillCanBeCookedRecipes();
		assertEquals(cb.getCanBeCookedRecipes().size(), 1);
		
		Recipe recp = chef.evaluateCandidateRecipes();
		assertNotNull(recp);
	}

	@Test
	public void findOptimalCookList() {
		chef.findOptimalCookList();
		int sizeWillCookList = chef.getWillCookList().size();
		assertEquals(1, sizeWillCookList);
	}
}
