import Classes.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class PharMainPage {
    private final static String PILLS = "Pills";
    private final static String LIQUID = "Liquid";
    private final static String LAST_SALES = "Last Sales:";
    private final static String LAST_PURCHASES = "Last Purchases:";

    Management management = new Management();

    private JPanel p1;

    private JButton purchasesButton;
    private JButton salesButton;
    private JButton detailsPurchasesButton;


    private JPanel MainPanel;
    private JPanel StartPage;

    // sales contents
    private JTextField txtCustomerS;
    private JPanel SalesPanel;

    private JTextField txtDrugS;
    private JTextField txtAmountS;
    private JCheckBox pillsCheckBoxS;
    private JCheckBox liquidCheckBoxS;

    private JButton buyButtonS;
    private JButton cancelButtonS;


    // purchases contents
    private JPanel PurchasesPanel;

    private JTextField txtNameDrugP;
    private JTextField txtAmountP;
    private JTextField txtPriceP;
    private JTextField txtExP;
    private JTextField txtProP;

    private JCheckBox pillCheckBoxP;
    private JCheckBox liquidCheckBoxP;

    private JButton cancelButtonP;
    private JButton saveButtonP;

    // Last Adding Contents.
    private JPanel LastAdding;
    private JLabel Filed;
    private JLabel LineOne;

    private JButton deleteButton;
    private JButton editButton;

    private JPanel infoLastPage;
    private JPanel startLast;
    private JButton listOrdersButton;
    private JTextField txtSupplierP;
    private JButton lastPurchasesButton;
    private JButton lastSaleButton;
    private JButton viewLastButton;

    private JLabel filed;

    private Purchases purchases;
    private Sales sales;
    private Drug lastDrug;
    private Purchases lastPurchases;
    private Sales lastSales;
    private Stack<ProductLine> productsLines ;

    Details dG;

    public PharMainPage() {
        restGUI(StartPage);
        restLastGUI(startLast);
        productsLines = new Stack<>();
        dG = new Details();

        detailsPurchasesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAnyCurrentProduct();
                LinkedList<ProductLine> p = management.getAllAmounts();
                if (p.size() == 0){
                    showWarning("The Purchases is Empty.!!");
                }else {
                    dG.runDetails(p);
                }
                // restGUI(Details);
            }
        });
        purchasesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAnyCurrentProduct();
                //Purchases p = management.getLastPurchases();
//                if (p.getProductLines().size() == 0){
//                    management.removePurchasesAt(p.getId());
//                }
                System.out.println("Purchases cont = " + management.getPurchases().size());
                lastPurchases = new Purchases();
                purchases = new Purchases();
                management.addPurchases(purchases);
                restGUI(PurchasesPanel);
                restLastGUI(infoLastPage);
                Filed.setText("Last Purchases:");
                setVisibleED(false);
                resetP();
            }
        });
        salesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAnyCurrentProduct();
                System.out.println("Sales cont = " + management.getSales().size());
                restGUI(SalesPanel);
                restLastGUI(infoLastPage);
                resetP();
                lastSales = new Sales();
                sales = new Sales();
                management.addSales(sales);
                Filed.setText(LAST_SALES);
                setVisibleED(false);
                resetS();
            }
        });


        cancelButtonP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetP();
            }
        });
        cancelButtonS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetS();
            }
        });

        /**
         * Edited Button.
         */
        saveButtonP.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addLastProductToPurchases();
                String amountP = txtAmountP.getText();
                String priceP = txtPriceP.getText();
                boolean alo = (!amountP.isEmpty() && !priceP.isEmpty()) &&
                              (!amountP.equals("") && !priceP.equals(""));
                if (alo) {
                    String supplier = txtSupplierP.getText();
                    String drugName = txtNameDrugP.getText();
                    int amount = Integer.parseInt(amountP);
                    double price = Double.parseDouble(priceP);
                    String type = "null";
                    if (pillCheckBoxP.isSelected()) {
                        type = PILLS;
                    } else if (liquidCheckBoxP.isSelected()) {
                        type = LIQUID;
                    }

                    lastDrug = new Drug(type, 23, "",
                            drugName, price, 333, txtProP.getText(), txtExP.getText());

                    // Before edit:
                    //lastProductLine = new ProductLine(lastDrug, amount, 0.0);

                    // Edit: add new product_Line to the Stack.
                    ProductLine pr = new ProductLine(lastDrug, amount, 0.0);
                    productsLines.push(pr);
                    // return the top of the product's.
                    String s = productsLines.peek().toString();

                    LineOne.setText(s);
                    setVisibleED(true);
                    lastPurchases.setSupplier(supplier);
                    purchases.setSupplier(supplier);
                }
                String name = txtSupplierP.getText();
                resetP();
                txtSupplierP.setText(name);
            }
        });
        /**
         * Edited Button.
         */
        buyButtonS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addLastProductToSales();
                String amountS = txtAmountS.getText();

                if (!amountS.equals("") && !amountS.isEmpty()){
                    String nameCustomer = txtCustomerS.getText();
                    String nameDrug = txtDrugS.getText();
                    int amount = Integer.parseInt(txtAmountS.getText());
                    String type = "";

                    if (pillsCheckBoxS.isSelected()){ type = PILLS;}
                    else if(liquidCheckBoxS.isSelected()){type = LIQUID;}

                    lastDrug = management.containDrug(nameDrug,type);
                    if (lastDrug == null){
                        showWarning("this drug ' " +  nameDrug + " 'it's not exit in your purchases ");
                    }else {
                        boolean enough = management.haveEnoughAmount(lastDrug,amount);
                        if (enough){
                            sales.setCustomer(nameCustomer);
                            lastSales.setCustomer(nameCustomer);
                            // Before edit:
//                            lastProductLine = new ProductLine(lastDrug,amount,0);
//                            String s = lastProductLine.toString();

                            // Edit:
                             ProductLine product = new ProductLine(lastDrug,amount,0);
                             productsLines.push(product);
                             // return the top  of the Stack product_Line.
                             String s = productsLines.peek().toString();
                            LineOne.setText(s);
                            setVisibleED(true);
                        }else{
                            showWarning("you don't have this amount ' " + amount + " ' of this drug '" + nameDrug +"' !!");
                        }
                    }
                }
                String name = txtCustomerS.getText();
                resetS();
                txtCustomerS.setText(name);
            }
        });
        /**
         * Edited Button.
         */
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LineOne.setText("Editing Last Operation...");
                if (Filed.getText().equals(LAST_PURCHASES)) {
                    resetP();
                    txtSupplierP.setText(purchases.getSupplier());
                    txtNameDrugP.setText(lastDrug.getName());
                    // Before Edit:
//                    txtAmountP.setText(lastProductLine.getAmount() + "");
//                    txtPriceP.setText(lastProductLine.getSinglePrice() + "");

                    // Edit:
                    ProductLine topProduct = productsLines.peek();
                    txtAmountP.setText(topProduct.getAmount() + "");
                    txtPriceP.setText(topProduct.getSinglePrice() + "");

                    txtProP.setText(lastDrug.getPro());
                    txtExP.setText(lastDrug.getExpr());
                    String type = lastDrug.getCategory().getType();
                    if (type.equals(PILLS)) {pillCheckBoxP.setSelected(true);}
                    else if (type.equals(LIQUID)) {liquidCheckBoxP.setSelected(true);}

                    /**
                     * Before Edit:
                     */
                   // lastProductLine = null;

                    /**
                     * Edit: pop()
                     */
                    productsLines.pop();
                    lastDrug = null;
                }
                else if (Filed.getText().equals(LAST_SALES)) {
                    resetS();
                    txtCustomerS.setText(sales.getCustomer());
                    txtDrugS.setText(lastDrug.getName());
                    /**
                     * Before Edit:
                     */
//                    txtAmountS.setText(lastProductLine.getAmount() + "");
                    /**
                     * Edit: peek()
                     */
                    txtAmountS.setText(productsLines.peek().getAmount() + "");

                    String type = lastDrug.getCategory().getType();
                    if (type.equals(PILLS)) {pillsCheckBoxS.setSelected(true);}
                    else if (type.equals(LIQUID)) {liquidCheckBoxS.setSelected(true);}

                    /**
                     * Before Edit:
                     */
                    // lastProductLine = null;

                    /**
                     * Edit: pop()
                     */
                    productsLines.pop();

                    lastDrug = null;
                }
            }
        });
        /**
         * Edited Button.
         */
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lastDrug = null;
                /**
                 * Before edit:
                 */
                //lastProductLine = null;

                /**
                 * Edit: pop()
                 */
                productsLines.pop();
                LineOne.setText("");
                setVisibleED(false);
            }
        });

        listOrdersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAnyCurrentProduct();
                LinkedList<Drug> drugs = management.getOrderList();
                if (drugs.size() == 0){
                    showWarning("list Order is Empty.!!");
                }else {
                    dG.runListOrder(drugs);
                }
            }
        });
        lastPurchasesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAnyCurrentProduct();
              //  Purchases p = management.getLastPurchases();
                if (lastPurchases == null || lastPurchases.isEmpty()){
                    showWarning("Nothing to show.!");
                }else {
                    dG.runPurchasesDetails(lastPurchases);
                }
            }
        });
        lastSaleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addAnyCurrentProduct();
//                Sales s = management.getLastSales();
                if (lastSales == null || lastSales.isEmpty()){
                        showWarning("Nothing to show.!");
                    }else {
                    dG.runSalesDetails(lastSales);
                }
            }
        });
    }

    private void showWarning(String message){
        JOptionPane.showMessageDialog(null,message,"Warning",0);
        resetS();
    }
    private void setVisibleED(boolean val){
        editButton.setVisible(val);
        deleteButton.setVisible(val);
    }

    /**
     * Edited method.
     */
    private void addLastProductToPurchases() {
        /**
         * Before edit:
         * if (lastProductLine != null && lastDrug != null)
         */
        if (productsLines.size() > 0 && lastDrug != null) {
            /**
             * Before edit:
             */
            //lastPurchases.copy(lastProductLine);
            //management.purchases(lastProductLine);

            /**
             * Edit: peek()
             */
            ProductLine pr = productsLines.peek();
            lastPurchases.copy(pr);
            management.purchases(pr);
            lastDrug = null;

            //lastProductLine = null;
            productsLines.pop();
            LineOne.setText("");
            setVisibleED(false);
            // And add to dateBase.
        }
    }
    /**
     * Edited method.
     */
    private void addLastProductToSales(){
        /**
         * Before edit:
         * if (lastProductLine != null && lastDrug != null)
         */
        if (productsLines.size() > 0 && lastDrug != null) {
            /**
             * Before edit:
             */
            //lastSales.copy(lastProductLine);
            // how it work:search all purchases that contain this drug and extract the amount.
            //management.sale(lastProductLine,sales);
            /**
             * Edit: peek()
             */
            ProductLine pr = productsLines.peek();
            lastSales.copy(pr);
            management.sale(pr,sales);

            lastDrug = null;
            // lastProductLine = null;
            productsLines.pop();
            LineOne.setText("");
            setVisibleED(false);
            // And add to dateBase.
        }
    }
    private void addAnyCurrentProduct(){
        if (Filed.getText().equals(LAST_SALES)){
            addLastProductToSales();
        }else if (Filed.getText().equals(LAST_PURCHASES)){
            addLastProductToPurchases();
        }
    }

    private void resetP() {
        txtSupplierP.setText("");
        txtNameDrugP.setText("");
        txtAmountP.setText("");
        txtPriceP.setText("");
        txtProP.setText("");
        txtExP.setText("");
        pillCheckBoxP.setSelected(false);
        liquidCheckBoxP.setSelected(false);
    }
    private void resetS(){
        txtCustomerS.setText("");
        txtDrugS.setText("");
        txtAmountS.setText("");
        pillsCheckBoxS.setSelected(false);
        liquidCheckBoxS.setSelected(false);
    }
    private void restGUI(JPanel jp) {
        MainPanel.removeAll();
        MainPanel.add(jp);
        MainPanel.repaint();
        MainPanel.revalidate();
    }
    private void restLastGUI(JPanel jp) {
        LastAdding.removeAll();
        LastAdding.add(jp);
        LastAdding.repaint();
        LastAdding.revalidate();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Main");
        frame.setContentPane(new PharMainPage().p1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(900, 500);
        frame.setVisible(true);
    }
}


//if (Filed.getText().equals("Last Purchases:")){
//        ProductLine productLine = management.getLastProductLine();
//        int id = productLine.getId();
//        // remove from purchases
//        if(management.removeProductLineAt(id)){
//        reset();
//        purchases.removeAt(id);
//        // fill all filed
//        txtNameDrugP.setText(productLine.getDrug().getName());
//        txtAmountP.setText(productLine.getAmount() + "");
//        txtPriceP.setText(productLine.getSinglePrice() + "");
//        String type = productLine.getDrug().getCategory().getType();
//        if (type.equals("Pill")){pillCheckBoxP.setSelected(true);}
//        else if (type.equals("Liquid")){liquidCheckBoxP.setSelected(true);}
//        }
//
//        }else if (Filed.getText().equals("Last Sales:")){
//
//        }