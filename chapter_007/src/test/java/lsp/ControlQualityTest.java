package lsp;

import lsp.foods.Food;
import lsp.foods.Milk;
import lsp.foods.Pork;
import lsp.warehouses.AbstractStorage;
import lsp.warehouses.Shop;
import lsp.warehouses.Trash;
import lsp.warehouses.Warehouse;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class ControlQualityTest {
    private final ControlQuality controllQuality = new ControlQuality();
    private final Calendar calendar = Calendar.getInstance();
    private final Shop shop = new Shop();
    private final Trash trash = new Trash();
    private final Warehouse warehouse = new Warehouse();
    private Date currentDate;

    @Before
    public void init() {
        calendar.set(2019, 3, 17, 0, 0, 0);
        currentDate = calendar.getTime();
        controllQuality.addStorage(trash);
        controllQuality.addStorage(shop);
        controllQuality.addStorage(warehouse);
    }

    @Test
    public void testWarehouseRelocate() {
        calendar.set(Calendar.DAY_OF_MONTH, 16);
        Date createDate = calendar.getTime();
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 23);
        Date expireDate = calendar.getTime();
        float price = 50.0f;
        float discount = 10.0f;
        final Food food = new Milk("milk", createDate, expireDate, price, discount);
        final AbstractStorage storage = controllQuality.checkQuality(food, currentDate);
        assertTrue(storage != null && storage.equals(warehouse) && food.getPrice() == price);
    }

    @Test
    public void testShopRelocate() {
        calendar.set(Calendar.DAY_OF_MONTH, 13);
        Date createDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, 20);
        Date expireDate = calendar.getTime();
        float price = 50.0f;
        float discount = 10.0f;
        final Food food = new Pork("pork", createDate, expireDate, price, discount);
        final AbstractStorage storage = controllQuality.checkQuality(food, currentDate);
        assertTrue(storage != null && storage.equals(shop) && food.getPrice() == price);
    }

    @Test
    public void testShopRelocateAndLow() {
        calendar.set(Calendar.DAY_OF_MONTH, 11);
        Date createDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, 18);
        Date expireDate = calendar.getTime();
        float price = 50.0f;
        float discount = 10.0f;
        final Food food = new Pork("pork", createDate, expireDate, price, discount);
        final AbstractStorage storage = controllQuality.checkQuality(food, currentDate);
        assertTrue(storage != null && storage.equals(shop) && food.getPrice() == price - discount);
    }

    @Test
    public void testTrashReallocate() {
        calendar.set(Calendar.DAY_OF_MONTH, 8);
        Date createDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, 16);
        Date expaireDate = calendar.getTime();
        float price = 50.0f;
        float discount = 10.0f;
        final Food food = new Pork("pork", createDate, expaireDate, price, discount);
        final AbstractStorage storage = controllQuality.checkQuality(food, currentDate);
        assertTrue(storage != null && storage.equals(trash) && food.getPrice() == price);
    }



}