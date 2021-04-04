package Classes;

public class Drug {
    private static int counter = 1;

    private Category category;
    private String name;
    private double price;
    private int barcode;
    private String pro;
    private String expr;
    private int id;



    /**
     *
     * @param type
     * @param number
     * @param describe
     * @param name
     * @param price
     * @param barcode
     * @param pro
     * @param expr
     */
    public Drug(String type,int number,String describe,
                String name, double price, int barcode,
                String pro, String expr) {
        this.category = new Category(type,number,describe);
        this.name = name;
        this.price = price;
        this.barcode = barcode;
        this.pro = pro;
        this.expr = expr;
        this.id = counter++;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setBarcode(int barcode) {
        this.barcode = barcode;
    }
    public void setPro(String pro) {
        this.pro = pro;
    }
    public void setExpr(String expr) {
        this.expr = expr;
    }

    public Category getCategory() {
        return category;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public int getBarcode() {
        return barcode;
    }
    public String getPro() {
        return pro;
    }
    public String getExpr() {
        return expr;
    }

    public Object[] format(){
        Object [] s = {name,category.getType()};
        return s;
    }
}
