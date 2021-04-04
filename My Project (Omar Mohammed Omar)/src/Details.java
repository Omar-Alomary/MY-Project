import Classes.Drug;
import Classes.ProductLine;
import Classes.Purchases;
import Classes.Sales;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.LinkedList;

public class Details {
    private JPanel panel1;
    private JTable table1;

    String head [] = {"|Name|","|Type|","|Amount|","|price per one|","|Total Price|"};

    String headSales [] = {"|Customer|","|Name|","|Type|","|Amount|","|Total Price|"};

    String headPurchases [] = {"|Supplier|","|Name|","|Type|","|Amount|","|price|","|Pro|","|Exp|"};

    String headOrders [] = {"|Name|","|Type|"};

    private void createUIComponents() {
        // TODO: place custom component creation code here

    }

    public void runPurchasesDetails(Purchases purchases){
        JFrame frame = new JFrame("Last Purchases");
        frame.setSize(700,400);
        frame.setVisible(true);
        frame.add(new Details().panel1);

        DefaultTableModel model = new DefaultTableModel(0,headPurchases.length);
        model.setColumnIdentifiers(headPurchases);
        table1 = new JTable(model);
        model.addRow(headPurchases);

        for (ProductLine pl :purchases.getProductLines()){
            Object row[] = pl.formatP(purchases.getSupplier());
            model.addRow(row);
        }
        frame.add(table1);
    }

    public void runSalesDetails(Sales sales){
        JFrame frame = new JFrame("Last Sales");
        frame.setSize(600,400);
        frame.setVisible(true);
        frame.add(new Details().panel1);

        DefaultTableModel model = new DefaultTableModel(0,headSales.length);
        model.setColumnIdentifiers(headSales);
        table1 = new JTable(model);
        model.addRow(headSales);

        for (ProductLine pl :sales.getProductLines()){
            Object row[] = pl.formatS(sales.getCustomer());
            model.addRow(row);
        }
        frame.add(table1);
    }

    public  void runDetails(LinkedList<ProductLine> prd) {

        JFrame frame = new JFrame("All Purchases");
        frame.setSize(500,300);
        frame.setVisible(true);
        frame.add(new Details().panel1);

        DefaultTableModel model = new DefaultTableModel(0,4);
        model.setColumnIdentifiers(head);
        table1 = new JTable(model);
        model.addRow(head);

        for (ProductLine pl :prd){
            Object row[] = pl.format();
            model.addRow(row);
        }
        frame.add(table1);
    }

    public void runListOrder(LinkedList<Drug> drugs){
        JFrame frame = new JFrame("List Orders");
        frame.setSize(300,300);
        frame.setVisible(true);
        frame.add(new Details().panel1);

        DefaultTableModel model = new DefaultTableModel(0,2);
        model.setColumnIdentifiers(headOrders);
        table1 = new JTable(model);
        model.addRow(headOrders);

        for (Drug pl :drugs){
            Object row[] = pl.format();
            model.addRow(row);
        }
        frame.add(table1);
    }
















//    public  void runDetails(ArrayList<ProductLine> prd) {
//        JFrame frame = new JFrame("Details Purchases");
//        frame.setSize(500,300);
//        frame.setVisible(true);
//        frame.add(new Details().panel1);
//
//        DefaultTableModel model = new DefaultTableModel(0,5);
//        model.setColumnIdentifiers(head);
//        table1 = new JTable(model);
//        model.addRow(head);
//
//        for (ProductLine pl :prd){
//            Object row[] = pl.format();
//            model.addRow(row);
//        }
//        frame.add(table1);
//    }
}
