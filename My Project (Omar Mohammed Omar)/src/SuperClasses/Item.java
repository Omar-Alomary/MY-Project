package SuperClasses;

import java.util.Date;

public class Item {
    private int id;
    private Date date;
    private double totalPrice;

    public Item(){
        id = 0;
        date = null;

    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }
    public Date getDate() {
        return date;
    }
    public double getTotalPrice() {
        return totalPrice;
    }

}
