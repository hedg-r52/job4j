package lsp;

import lsp.foods.*;
import lsp.warehouses.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.Assert.*;

public class ControlQualityTest {
    private final ControlQuality controllQuality = new ControlQuality();
    private final Calendar calendar = Calendar.getInstance();
    private final Shop shop = new Shop(new Storage());
    private final Trash trash = new Trash(new Storage());
    private final Warehouse warehouse = new Warehouse(new Storage());
    private final ColdWarehouse coldWarehouse = new ColdWarehouse(new Storage());
    private final FixCapacityStorage fixWarehouse = new FixCapacityStorage(new Warehouse(new Storage()), 1);
    private final RecyclingStorage recyclingStorage = new RecyclingStorage(new Storage());

    private Date currentDate;

    @Before
    public void init() {
        calendar.set(2019, 3, 17, 0, 0, 0);
        currentDate = calendar.getTime();
        controllQuality.addStorage(coldWarehouse);
        controllQuality.addStorage(fixWarehouse);
        controllQuality.addStorage(warehouse);
        controllQuality.addStorage(shop);
        controllQuality.addStorage(recyclingStorage);
        controllQuality.addStorage(trash);
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
        final IStorage storage = controllQuality.checkQuality(food, currentDate);
        assertTrue(storage != null && storage.equals(fixWarehouse) && food.getPrice() == price);
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
        final IStorage storage = controllQuality.checkQuality(food, currentDate);
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
        final IStorage storage = controllQuality.checkQuality(food, currentDate);
        assertTrue(storage != null && storage.equals(shop) && food.getPrice() == price - discount);
    }

    @Test
    public void testTrashReallocate() {
        calendar.set(Calendar.DAY_OF_MONTH, 8);
        Date createDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, 16);
        Date expireDate = calendar.getTime();
        float price = 50.0f;
        float discount = 10.0f;
        final Food food = new Pork("pork", createDate, expireDate, price, discount);
        final IStorage storage = controllQuality.checkQuality(food, currentDate);
        assertTrue(storage != null && storage.equals(trash) && food.getPrice() == price);
    }

    @Test
    public void testFixCapacityWarehouse() {
        calendar.set(Calendar.DAY_OF_MONTH, 16);
        Date createDate = calendar.getTime();
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 23);
        Date expireDate = calendar.getTime();
        float price = 50.0f;
        float discount = 10.0f;
        final Food milk = new Milk("milk", createDate, expireDate, price, discount);
        final IStorage storageOne = controllQuality.checkQuality(milk, currentDate);
        final Food pork = new Pork("pork", createDate, expireDate, price, discount);
        final IStorage storageTwo = controllQuality.checkQuality(pork, currentDate);
        assertTrue(storageOne != null && storageOne.equals(fixWarehouse) && milk.getPrice() == price);
        assertTrue(storageTwo != null && storageTwo.equals(warehouse) && pork.getPrice() == price);
    }

    @Test
    public void testRecyclingStorage() {
        calendar.set(Calendar.DAY_OF_MONTH, 8);
        Date createDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, 16);
        Date expaireDate = calendar.getTime();
        float price = 50.0f;
        float discount = 10.0f;
        final IFood pork = new ReproductFood(new Pork("pork", createDate, expaireDate, price, discount));
        final IStorage storage = controllQuality.checkQuality(pork, currentDate);
        assertTrue(storage != null && storage.equals(recyclingStorage) && pork.getPrice() == price);
    }

    @Test
    public void testColdStorage() {
        calendar.set(Calendar.DAY_OF_MONTH, 16);
        Date createDate = calendar.getTime();
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 23);
        Date expireDate = calendar.getTime();
        float price = 50.0f;
        float discount = 10.0f;
        final IFood food = new ColdStoreFood(new Milk("milk", createDate, expireDate, price, discount));
        final IStorage storage = controllQuality.checkQuality(food, currentDate);
        assertTrue(storage != null && storage.equals(coldWarehouse) && food.getPrice() == price);
    }

    @Test
    public void testDay16WarehouseAndAfterResortDay13ShopReallocate() {
        calendar.set(Calendar.DAY_OF_MONTH, 16);
        final Date createDate = calendar.getTime();
        calendar.set(Calendar.MONTH, 3);
        calendar.set(Calendar.DAY_OF_MONTH, 23);
        final Date expireDate = calendar.getTime();
        final float price = 50.0f;
        final float discount = 10.0f;
        final Food food = new Milk("milk", createDate, expireDate, price, discount);
        assertTrue(shop.getSize() == 0 && fixWarehouse.getSize() == 0);
        final IStorage storage = controllQuality.checkQuality(food, currentDate);
        assertTrue(shop.getSize() == 0 && fixWarehouse.getSize() == 1);
        calendar.set(Calendar.DAY_OF_MONTH, 20);
        final Date resortDate = calendar.getTime();
        controllQuality.resort(resortDate);
        assertTrue(shop.getSize() == 1 && fixWarehouse.getSize() == 0);
    }

}