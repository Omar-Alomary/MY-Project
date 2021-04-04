package Classes;

import java.util.ArrayList;
import java.util.LinkedList;

public class Management {
    private LinkedList<Purchases> purchases;
    private LinkedList<Sales> sales;
    private LinkedList<Drug> listOrders;
    private LinkedList<ProductLine> productLines;
    private LinkedList<Drug> drugs;

    public Management() {
        this.purchases = new LinkedList<>();
        this.sales = new LinkedList<>();
        this.productLines = new LinkedList<>();
        this.drugs = new LinkedList<>();
        this.listOrders = new LinkedList<>();
    }

    /**
     * all orders that must be provided.
     * @return
     */
    public LinkedList<Drug> getOrderList(){
        return listOrders;
    }

    /**
     * get All purchases that exist.
     * @return
     */
    public LinkedList<Purchases> getPurchases(){
        return purchases;
    }

    /**
     * Get All sales that exist.
     * @return
     */
    public LinkedList<Sales> getSales(){
        return sales;
    }

    /**
     * return the last product line.
     * @return
     */
    public ProductLine getLastProductLine(){
        return productLines.getLast();
    }

    /**
     * Obtaining all product line of purchases by it's name and type.
     * @param name the name of the drug.
     * @param type the category of the drug.
     * @return
     */
    private LinkedList<ProductLine> getPurchasesWith(String name, String type){
        LinkedList<ProductLine> currentPL = new LinkedList<>();
        for (Purchases pur :purchases){
            ArrayList<ProductLine> pl = pur.contain(name,type);
            currentPL.addAll(pl);
        }
        return currentPL;
    }

    /**
     * Return the index of the drug if it's exist, otherwise return -1.
     * @param dr the Drug.
     * @return
     */
    public int containDrug(Drug dr){
        String name = dr.getName();
        String type = dr.getCategory().getType();
        for (int i = 0; i < drugs.size(); i++) {
            Drug drug =  drugs.get(i);
            Category ct = drug.getCategory();
            boolean contain = drug.getName().equals(name) && ct.getType().equals(type);
            if (contain){return i;}
        }
        return -1;
    }

    /**
     * Return the drug if it's exist this name and type, otherwise return null.
     * @param name the name of the drug.
     * @param type the category of the drug.
     * @return
     */
    public Drug containDrug(String name,String type){
        for (Drug drug : drugs){
            Category ct = drug.getCategory();
            boolean contain = drug.getName().equals(name) && ct.getType().equals(type);
            if (contain){return drug;}
        }
        return null;
    }

    /**
     * Check the order list if this exist, will return the index, otherwise return -1.
     * @param drug
     * @return
     */
    public int containOrder(Drug drug){
        String name = drug.getName();
        String type = drug.getCategory().getType();
        for (int i = 0; i < listOrders.size(); i++) {
            Drug d = listOrders.get(i);
            boolean found = d.getName().equals(name) && d.getCategory().getType().equals(type);
            if (found){return i;}
        }
        return -1;
    }

    /**
     * make a new sale.
     * @param pro
     * @param sl
     * @return
     */
    public String sale(ProductLine pro, Sales sl){

        String nameDrug = pro.getDrug().getName();
        String type     = pro.getDrug().getCategory().getType();
        int amount      = pro.getAmount();

        LinkedList<ProductLine> purchasesPL = getPurchasesWith(nameDrug, type);

        Drug drug = extract(purchasesPL, amount);
        if (drug != null){
            sl.addProductLines(pro);
            productLines.addLast(pro);
            return "The sale was successful.";
        }
        return "";
    }

    /**
     * make a new purchases.
     * @param prod
     * @return
     */
    public String purchases(ProductLine prod){

        int ind = containDrug(prod.getDrug());
        if (ind == -1){
            addDrug(prod.getDrug());
        }
        // remove the order if it purchases
        int index = containOrder(prod.getDrug());
        if (index != -1){listOrders.remove(index);}

        // add the new product to the management product line.
        addProductLine(prod);

        // get last purchases and add this product line.
        Purchases pur = purchases.getLast();
        pur.addProductLines(prod);
        return "The purchases was completed successfully";
    }

    /**
     * Extracting all required quantity from purchases.
     * @param purchasesPL all purchases that have this drug.
     * @param amount the of this product line.
     * @return
     */
    public Drug extract(LinkedList<ProductLine> purchasesPL, int amount){
        int currentAmount = amount;
        for (ProductLine pl:purchasesPL){
            int count = pl.getAmount();
            if (count > currentAmount){
                pl.decreaseAmount(currentAmount);
                addOrder(pl.getDrug());
                // pl.setAmount(count - currentAmount);
                return pl.getDrug();

            }else if(currentAmount == 0){
                return pl.getDrug();

            }else {
                currentAmount -= count;
                pl.setAmount(0);
                removeProductLineAt(pl.getId());
                updatePurchases();
            }
        }
        return null;
    }

    /**
     * Update purchases after any sale is made.
     */
    private void updatePurchases(){
        for (int i =0; i < purchases.size() ; i++){
            Purchases pur = purchases.get(i);
            if (pur.isEmpty()){
                purchases.remove(i);
            }
        }
    }

    /**
     * Check the existing quantity of this drug.
     * @param drug
     * @param amount
     * @return
     */
    public boolean haveEnoughAmount(Drug drug , int amount){
        String name = drug.getName();
        String type = drug.getCategory().getType();
        int count = 0;
        LinkedList<ProductLine> prd = getPurchasesWith(name,type);
        for (ProductLine p: prd) {
            count += p.getAmount();
            if (count >= amount){return true;}
        }
        return false;
    }

    /**
     * save a new order from this drug to the order list to provide it at a later time.
     * @param drug
     */
    private void addOrder(Drug drug){

        String name = drug.getName();
        String type = drug.getCategory().getType();
        LinkedList<ProductLine> pro = getPurchasesWith(name,type);
        // for make the min of the drug to make orders or not
        int size = 0;
        for (ProductLine p: pro) {
            size += p.getAmount();
            if (size > 5){break;}
        }
        if (size <= 5){
            for (Drug dr: listOrders) {
                if (dr.getName() == name && dr.getCategory().getType() == type){
                    return;
                }
            }
            listOrders.addLast(drug);
        }
    }

    /**
     * save a new drug to the drugs list.
     * @param dr
     */
    public void addDrug(Drug dr){
        drugs.addLast(dr);
    }
    /**
     * save a new purchases to the purchases list.
     * @param pur
     */
    public void addPurchases(Purchases pur){
        purchases.addLast(pur);
    }
    /**
     * save a new sale to the sales list.
     * @param sa
     */
    public void addSales(Sales sa){
        sales.addLast(sa);
    }
    /**
     * save a new product line to the list.
     * @param pl
     */
    public void addProductLine(ProductLine pl){
        productLines.addLast(pl);
    }

    /**
     * Get all available quantities
     * @return
     */
    public LinkedList<ProductLine> getAllAmounts(){
        LinkedList<ProductLine> pls = new LinkedList<>();
        for (Drug drug :drugs){
            Category ct = drug.getCategory();
            String name = drug.getName();
            String type = ct.getType();
            ProductLine prodLin = sum(getPurchasesWith(name,type));
            if (prodLin.getAmount() != 0){
                prodLin.setDrug(drug);
                pls.addLast(prodLin);
            }
        }
        return pls;
    }

    /**
     * remove Product line by it's ID.
     * @param id
     * @return
     */
    private boolean removeProductLineAt(int id){
        //remove the drug
        for (int i = 0; i < productLines.size(); i++) {

            if(productLines.get(i).getId() == id){
               // productLines.get(i).setDrug(null);
                productLines.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * calculate all products line amount in a single product line.
     * @param pl
     * @return
     */
    private ProductLine sum(LinkedList<ProductLine> pl){
        ProductLine currentPL = new ProductLine();
        for (ProductLine pL : pl){
            int amount = pL.getAmount();
            currentPL.increaseAmount(amount);
        }
        return currentPL;
    }
}