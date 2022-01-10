package es.unex.giiis.asee.project.data.model;


import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Intent;

import es.unex.giiis.asee.project.data.model.Ingredient;

public class IngredientTests {

    public static Ingredient ing1;
    public static Ingredient ing2;

    @BeforeClass
    public static void initClass() {
        ing1 = new Ingredient(0, "Pimientos de padrón", "Unos pican y otros non.", "Spicy, Vegetable", 21, new byte[] {0x00, 0x01, 0x02, 0x03});
    }

    @Test
    public void testIng1() {
        assertNotNull(ing1);
    }

    @Test
    public void testIng2() {
        assertNull(ing2);
    }

    @Test
    public void testGetId() {
        assertNotNull(ing1.getId());
        assertNotEquals(ing1.getId(), 23);
        assertEquals(ing1.getId(), 0);
    }

    @Test
    public void testSetId() throws NoSuchFieldException, IllegalAccessException {
        long newId = 12;
        Ingredient instance = new Ingredient();
        instance.setId(newId);
        final Field field = instance.getClass().getDeclaredField("id");
        field.setAccessible(true);
        assertEquals(field.get(instance), newId);
    }

    @Test
    public void testGetName() {
        assertNotNull(ing1.getName());
        assertNotEquals(ing1.getName(), "Arroz");
        assertEquals(ing1.getName(), "Pimientos de padrón");
    }

    @Test
    public void testSetName() throws NoSuchFieldException, IllegalAccessException {
        String newName = "Pimientos";
        Ingredient instance = new Ingredient();
        instance.setName(newName);
        final Field field = instance.getClass().getDeclaredField("name");
        field.setAccessible(true);
        assertEquals(field.get(instance), newName);
    }

    @Test
    public void testGetDescription() {
        assertNotNull(ing1.getDescription());
        assertNotEquals(ing1.getDescription(), "Sabor muy fuerte.");
        assertEquals(ing1.getDescription(), "Unos pican y otros non.");
    }

    @Test
    public void testSetDescription() throws NoSuchFieldException, IllegalAccessException {
        String newDescription = "Sabor muy fuerte.";
        Ingredient instance = new Ingredient();
        instance.setDescription(newDescription);
        final Field field = instance.getClass().getDeclaredField("description");
        field.setAccessible(true);
        assertEquals(field.get(instance), newDescription);
    }

    @Test
    public void testGetCategories() {
        assertNotNull(ing1.getCategories());
        assertNotEquals(ing1.getCategories(), "Fruit");
        assertEquals(ing1.getCategories(), "Spicy, Vegetable");
    }

    @Test
    public void testSetCategories() throws NoSuchFieldException, IllegalAccessException {
        String newCategories = "Fruit";
        Ingredient instance = new Ingredient();
        instance.setCategories(newCategories);
        final Field field = instance.getClass().getDeclaredField("categories");
        field.setAccessible(true);
        assertEquals(field.get(instance), newCategories);
    }

    @Test
    public void testGetNRV() {
        assertNotNull(ing1.getNRV());
        assertNotEquals(ing1.getNRV(), 32);
        assertEquals(ing1.getNRV(), 21);
    }

    @Test
    public void testSetNRV() throws NoSuchFieldException, IllegalAccessException {
        int newNRV = 43;
        Ingredient instance = new Ingredient();
        instance.setNRV(newNRV);
        final Field field = instance.getClass().getDeclaredField("NRV");
        field.setAccessible(true);
        assertEquals(field.get(instance), newNRV);
    }

    @Test
    public void testGetPhoto() {
        assertNotNull(ing1.getPhoto());
        assertArrayEquals(ing1.getPhoto(), new byte[] {0x00, 0x01, 0x02, 0x03});
    }

    @Test
    public void testSetPhoto() throws NoSuchFieldException, IllegalAccessException {
        byte[] newPhoto = new byte[] {0x03, 0x01, 0x06, 0x05};
        Ingredient instance = new Ingredient();
        instance.setPhoto(newPhoto);
        final Field field = instance.getClass().getDeclaredField("photo");
        field.setAccessible(true);
        assertArrayEquals((byte[]) field.get(instance), newPhoto);
    }

    @Test
    public void testPackageIntent() {
        Intent intent = new Intent();

        Ingredient instance;

        intent.putExtra(Ingredient.INGREDIENT_NAME, "Arroz bomba");
        intent.putExtra(Ingredient.INGREDIENT_DESCRIPTION, "Perfecto para paellas.");
        intent.putExtra(Ingredient.INGREDIENT_CATEGORIES, "Cereal");
        intent.putExtra(Ingredient.INGREDIENT_NRV, 121);
        intent.putExtra(Ingredient.INGREDIENT_PHOTO, new byte[] {0x00, 0x01, 0x02, 0x03});


        // intent.getStringExtra(Ingredient.INGREDIENT_NAME, );
    }

    @Test
    public void testToString() {

    }
}
