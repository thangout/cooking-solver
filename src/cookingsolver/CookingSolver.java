/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cookingsolver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 *
 * @author Thang Do
 */
public class CookingSolver {

	static ArrayList<Ingredient> ingredients; 
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws IOException {
		ingredients = new ArrayList<>();
		parseIngredients();
	}

	public static void parseIngredients() throws IOException{
		FileInputStream fstream = new FileInputStream("ingredients.txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String line = "";
		Recipe recp = new Recipe();
		while ((line = br.readLine()) != null) {
			if ("#".equals(line)) {
				break;		
			}else{
				String[] temp = line.trim().split(",");
				String name = temp[0]; 
//				System.out.println(name);
				String[] values = temp[1].trim().split(" ");
				int weight = Integer.parseInt(values[0]);
				int price  = Integer.parseInt(values[1]);
				Ingredient ing = new Ingredient(name,weight,price);
				ingredients.add(ing);
				
			}
		}
	}
}
