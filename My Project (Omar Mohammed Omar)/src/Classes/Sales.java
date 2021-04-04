package Classes;

import SuperClasses.Item;

import java.util.ArrayList;

public class Sales extends Item {
    private static int counter = 1;
    private String customer;
    private ArrayList<ProductLine> productLines;

    public Sales(){
        productLines = new ArrayList<>();
        setId(counter++);
        customer = "";
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public void addProductLines(ProductLine pl){
        productLines.add(pl);
    }

    public void setProductLines(ArrayList<ProductLine> productLines) {
        this.productLines = productLines;
    }
    public boolean isEmpty(){
        return getProductLines().size() == 0;
    }

    public ArrayList<ProductLine> getProductLines() {
        return productLines;
    }

    /**
     * making a copy of all contain of this product line to this sale.
     * @param pr
     */
    public void copy(ProductLine pr){
        Drug drug = pr.getDrug();
        Category category = drug.getCategory();
        Drug newDrug = new Drug(category.getType(),category.getNumber(),category.getDescribe(),
                            drug.getName(), drug.getPrice(),drug.getBarcode(),drug.getPro(),drug.getExpr());
        ProductLine productLine = new ProductLine(newDrug,pr.getAmount(),pr.getProfit());
        productLines.add(productLine);
    }
}
