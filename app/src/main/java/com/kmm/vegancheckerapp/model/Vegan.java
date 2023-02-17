package com.kmm.vegancheckerapp.model;

import com.kmm.vegancheckerapp.dao.IngredientDAO;

import java.util.ArrayList;

public class Vegan {




    public ArrayList<String> containsAnimalIngredients(String[] prodIngredients) {




        String[] parsedIngredients = parseIngredients(prodIngredients);
        IngredientDAO ingredientDAO = new IngredientDAO();
         ArrayList<String> list = new ArrayList<>(ingredientDAO.getNonVeganIngredients());

        ArrayList<String> arr = new ArrayList<>();


        for(String ingredients: parsedIngredients){
           String lowerIngredients = ingredients.toLowerCase();
            if(list.contains (lowerIngredients)){
                arr.add(ingredients);
            }

        }
        return arr;









    }

    public static String[] parseIngredients(String[] ingredients){
        StringBuilder product = new StringBuilder();


        for (String ingred : ingredients) {
            product.append(ingred).append(" ");

        }

        return product.toString().trim().split(" ");





    }
}
