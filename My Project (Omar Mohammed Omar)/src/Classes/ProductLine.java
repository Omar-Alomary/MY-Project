package Classes;


public class ProductLine {
    private static int counter = 1;

    private int id;
    private Drug drug;
    private int amount;
    private double ProfitPrice;

    /**
     *
     * @param drug the drug of this product line.
     * @param amount the amount of this drug in the product line.
     */
    public ProductLine(Drug drug, int amount,double ProfitPrice) {
        this.drug = drug;
        this.amount = amount;
        this.ProfitPrice = ProfitPrice;
        this.id = counter++;
    }

    public ProductLine(){
        this(null,0,0.0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * return the total price of all drug after calculate the profit.
     * @return
     */
    public double getPrice() {
        double totalPrice = (getSinglePrice() * amount);
        return totalPrice;
    }

    /**
     * return a single drug price after calculate the profit.
     * @return
     */
    public double getSinglePrice(){
        return getProfit() + drug.getPrice();
    }

    /**
     * return a single drug profit.
     * @return
     */
    public double getProfit(){
        return (ProfitPrice * drug.getPrice());
    }

    public void setPrice(double price) {
        this.ProfitPrice = price;
    }

    public void setDrug(Drug drug) {
        this.drug = drug;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Drug getDrug() {
        return drug;
    }
    public int getAmount() {
        return amount;
    }

    public void increaseAmount(int num){
        amount += num;
    }

    public void decreaseAmount(int num){
        amount -= num;
    }

    public boolean isEmpty(){return (amount == 0) || drug == null;}


    public Object [] format(){
        Category ct = drug.getCategory();
        Object [] s = {drug.getName(),ct.getType(),amount ,getSinglePrice(),getPrice()};
        return s;
    }

    /**
     * Format of product line info in the form of purchases.
     * @param name
     * @return
     */
    public Object [] formatP(String name){
        Category ct = drug.getCategory();
        Object [] s = {name,drug.getName(),ct.getType(),amount ,getSinglePrice(),drug.getPro(),drug.getExpr()};
        return s;
    }

    /**
     * Format of product line info in the form of sales.
     * @param name
     * @return
     */
    public Object [] formatS(String name){
        Category ct = drug.getCategory();
        Object [] s = {name,drug.getName(),ct.getType(),amount ,getPrice()};
        return s;
    }
    @Override
    public String toString() {
        Category ct = drug.getCategory();
        String info = "Name:"    + drug.getName() + "\t\t "  +
                      "Type:"    + ct.getType()   + "\t\t "  +
                      "Amount:"  + amount         + "\t\t "  +
                      "Price:"   + getPrice()     + "\t\t "  +
                      "Pro:"     + drug.getPro()  + "\t\t "  +
                      "Exp:"     + drug.getExpr();
        return info;
    }
}
