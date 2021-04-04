package Classes;

import SuperClasses.Item;

import java.util.ArrayList;

public class Purchases extends Item {
    private static int counter = 1;
    private String supplier;
    private ArrayList<ProductLine> productLines;

    public Purchases(){
        productLines = new ArrayList<>();
        setId(counter++);
        supplier = "";
    }
    public boolean isEmpty(){
        return getProductLines().size() == 0;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public void addProductLines(ProductLine pl){
        getProductLines().add(pl);
    }

    public void setProductLines(ArrayList<ProductLine> productLines) {
        this.productLines = productLines;
    }

    private void update(){
        for (int i =0; i < productLines.size(); i++){
            ProductLine pl = productLines.get(i);
            if (pl.isEmpty()){productLines.remove(i);}
        }
    }

    public ArrayList<ProductLine> getProductLines() {
        update();
        return productLines;
    }



    public void increaseTotalPrice(double price){
        double currentPrice = getTotalPrice() + price;
        setTotalPrice(currentPrice);
    }
    /**
     *
     * @param name
     * @param type
     * @return
     */
    public ArrayList<ProductLine> contain(String name, String type){
        ArrayList<ProductLine> currentProLine = new ArrayList<>();
        for (ProductLine productLine:getProductLines()){
            Drug drug = productLine.getDrug();

            if (drug.getName().equals(name)){

                Category cate = drug.getCategory();
                if (cate.getType().equals(type)){
                    currentProLine.add(productLine);
                }
            }
        }
        return currentProLine;
    }

    /**
     * remove the product line by it's id.
     * @param id the id of the product line.
     */
    public void removeAt(int id){
        for (int i=0; i < productLines.size(); i++){
            ProductLine pr = productLines.get(i);
            if (pr.getId() == id){productLines.remove(i);}
        }
    }


    public void copy(ProductLine pr){
        Drug drug = pr.getDrug();
        Category category = drug.getCategory();
        Drug newDrug = new Drug(category.getType(),category.getNumber(),category.getDescribe(),
                drug.getName(), drug.getPrice(),drug.getBarcode(),drug.getPro(),drug.getExpr());
        ProductLine productLine = new ProductLine(newDrug,pr.getAmount(),pr.getProfit());
        productLines.add(productLine);
    }
}
