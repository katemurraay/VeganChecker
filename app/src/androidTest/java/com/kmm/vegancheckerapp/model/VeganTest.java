package com.kmm.vegancheckerapp.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.google.common.truth.Truth.assertThat;

public class VeganTest {

        int index;
        Vegan vegan;

        @Before
        public void setUp(){
            vegan = new Vegan();
        }

        @Test
        public void containsAllAnimalIngredients() {
            String[] ingredients = {"Albumen", "bone char", "butterfat", "carmine", "casein", "gelatin", "lactose", "lard", "l-cysteine", "mono-glycerides", "di-glycerides", "shellac", "vitaminD3", "wheypowder"};
            ArrayList<String> isvegan = vegan.containsAnimalIngredients(ingredients);
            index = isvegan.size();
            assertThat(index).isNotEqualTo(0);
        }

        @Test
        public void noAnimalIngredients() {
            String[] ingredients = {"wheat semolina", "water"};
            ArrayList<String> isvegan = vegan.containsAnimalIngredients(ingredients);
            index = isvegan.size();
            assertThat(index).isEqualTo(0);
        }

        @Test
        public void containsAnAnimalIngredient() {
            String[] ingredients = {"cheese", "lactic ferments", "black pepper 1.5%", "salt", "preservative: potassium sorbate"};
            ArrayList<String> isvegan = vegan.containsAnimalIngredients(ingredients);
            index = isvegan.size();
            assertThat(index).isNotEqualTo(0);
        }

        @Test
        public void containsAParsedAnimalIngredient() {
            String[] ingredients = {"cheese powder", "lactic ferments", "black pepper 1.5%", "salt", "preservative: potassium sorbate"};
            ArrayList<String> isvegan = vegan.containsAnimalIngredients(ingredients);
            index = isvegan.size();
            assertThat(index).isNotEqualTo(0);
        }

        @Test
        public void eggTest(){
            String[] ingredients = {"egg yolk"};
            ArrayList<String> isvegan = vegan.containsAnimalIngredients(ingredients);
            index = isvegan.size();
            assertThat(index).isNotEqualTo(0);

        }




    }

