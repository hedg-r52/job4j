package lsp.foods;

import java.util.Date;

/**
 * @author Andrei Soloviev (hedg.r52@gmail.com)
 * @since 17.03.2019
 * @version 0.1
 */
public class Food {
    protected final String name;
    protected final Date createDate;
    protected final Date expiredDate;
    protected float price;
    protected float discount;
    private boolean isSale = false;

    public Food(String name, Date createDate, Date expiredDate, float price, float discount) {
        this.name = name;
        this.createDate = createDate;
        this.expiredDate = expiredDate;
        this.price = price;
        this.discount = discount;
    }

    public void enableSale() {
        isSale = true;
    }

    public float getPrice() {
        return (this.isSale ? this.price - this.discount : this.price);
    }

    public int getDaysOfLifeInPercent(Date currentDate) {
        return Math.round((((float) (currentDate.getTime()  - this.createDate.getTime()))
                / (float) (this.expiredDate.getTime() - this.createDate.getTime())) * 100);
    }



}
