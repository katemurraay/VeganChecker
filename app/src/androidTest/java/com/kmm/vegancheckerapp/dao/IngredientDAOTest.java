package com.kmm.vegancheckerapp.dao;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static com.google.common.truth.Truth.assertThat;

public class IngredientDAOTest {
    IngredientDAO iDAO;
    ArrayList<String> ingredients;
@Before
    public  void setUp(){
        iDAO = new IngredientDAO();
        ingredients = new ArrayList<>(iDAO.getNonVeganIngredients());
    }
    @Test
    public void checkValuesAddedTest(){
        assertThat(ingredients).isNotNull();
    }
@Test
public void checkAllValuesAddedTest(){
        assertThat(ingredients.size()).isEqualTo(42);
}

@Test
    public void checkContentsTest(){
        assertThat(ingredients).contains("eggs");
        assertThat(ingredients).contains("milk");
        assertThat(ingredients).contains("d3");
}




}