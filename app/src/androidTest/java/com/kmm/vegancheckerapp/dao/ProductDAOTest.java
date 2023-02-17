package com.kmm.vegancheckerapp.dao;


import com.kmm.vegancheckerapp.model.Product;
import com.kmm.vegancheckerapp.model.User;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.google.common.truth.Truth.assertThat;


public class ProductDAOTest {
    ProductDAO pdao;
    Product product;
    ArrayList<Product> alternativeProducts;


    @Before
    public void setUp()  {
        pdao =new ProductDAO();
        product = new Product();
        alternativeProducts = new ArrayList<>();
    }



    @Test
    public void getProductBarcodeTest() {

        String barcode = "5011096005881";
        product = pdao.getProductByBarcode(barcode);
        String name = "Hand Cooked Crisps";
        assertThat(product.getProductName()).isEqualTo(name);
    }

    @Test
    public void getVeganProductByBarcode() {

        String barcode = "25243123";
        product = pdao.getVeganFoodsByBarcode(barcode);
        String name = "Bacon Rashers (150g)";
        assertThat(product.getProductName()).isEqualTo(name);
    }
    @Test
    public void returnNullIfProductNotFound() {

        String barcode = "0101010101";
        product = pdao.getVeganFoodsByBarcode(barcode);
        assertThat(product).isNull();
    }

    @Test
    public void barcodeNullVeganTest()  {

        String barcode ="";
        product = pdao.getVeganFoodsByBarcode(barcode);
        assertThat(product).isNull();
    }

    @Test
    public void  barcodeNullProductTest()  {

        String barcode ="";
        product = pdao.getProductByBarcode(barcode);
        assertThat(product).isNull();
    }

    @Test
    public void incorrectBarcodeProductTest()  {
        String barcode = "0101010101";
        product = pdao.getProductByBarcode(barcode);
        assertThat(product).isNull();
    }

    @Test
    public void typeNullTest() throws SQLException, IOException, ClassNotFoundException {
        String type ="";
        alternativeProducts.addAll(pdao.getVeganProductsByType(type));
        int arraySize = alternativeProducts.size();
        assertThat(arraySize).isEqualTo(0);


    }

    @Test
    public void typeIncorrectTest() throws SQLException, IOException, ClassNotFoundException {
        String type ="Car";
        alternativeProducts.addAll(pdao.getVeganProductsByType(type));
        int arraySize = alternativeProducts.size();
        assertThat(arraySize).isEqualTo(0);
    }
    @Test
    public void typeSpecialCharactersTest() throws SQLException, IOException, ClassNotFoundException {
        String type ="%";
        alternativeProducts.addAll(pdao.getVeganProductsByType(type));
        int arraySize = alternativeProducts.size();
        assertThat(arraySize).isEqualTo(0);
    }

    @Test
    public void typeCorrectTest() throws SQLException, IOException, ClassNotFoundException {
        String type ="Bread";
        alternativeProducts.addAll(pdao.getVeganProductsByType(type));
        int arraySize = alternativeProducts.size();
        assertThat(arraySize).isNotEqualTo(0);
    }

    @Test
    public void getFoodTypesTest() {
        ArrayList<String> types = pdao.getProductTypes("Food");

        assertThat(types).contains("Bread");
        assertThat(types).contains("Burger");
        assertThat(types).doesNotContain("Vodka");
    }

    @Test
    public void getFoodTypesNotNullTest()  {
        ArrayList<String> types = pdao.getProductTypes("Food");
        assertThat(types).isNotEmpty();
    }
    @Test
    public void getAlcoholTypesTest() {
        ArrayList<String> types = pdao.getProductTypes("Alcohol");

        assertThat(types).contains("Gin");
        assertThat(types).contains("Vodka");
        assertThat(types).doesNotContain("Cheese");
    }

    @Test
    public void getAlcoholTypesNotNullTest(){
        ArrayList<String> types = pdao.getProductTypes("Alcohol");
        assertThat(types).isNotEmpty();
    }

    @Test
    public void insertProduct(){
        String[] ingredients ={"Testing"};
        User user = new User();
        user.setUserID(1);
        Product insertProduct = new Product( 1, "Test", "Test", "123456789", "Test","Test", ingredients, "Test", "Test", user, "Food");
        int i = pdao.uploadProduct(insertProduct);
        assertThat(i).isEqualTo(1);
    }
    @Test
    public void deleteProductTest() {
        String barcode = "123456789";
        int i = pdao.deleteProduct(barcode);
        assertThat(i).isEqualTo(1);
    }

    @Test
    public void incorrectProductDeleteTest() {
        String barcode = "123456788";
        int i = pdao.deleteProduct(barcode);
        assertThat(i).isEqualTo(0);
    }
    @Test
    public void CreateDelete()  {
        insertProduct();
        deleteProductTest();
    }

}