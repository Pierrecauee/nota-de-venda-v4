package model;

import java.util.ArrayList;
import java.util.List;

public class Sale {
    private List<SaleItem> items = new ArrayList<>();
    private PaymentMethod paymentMethod;

    public void setPaymentMethod(String payment) {
        paymentMethod = DataBase.selectPayment(payment);
    }

    public String getPaymentMethod() {
        return paymentMethod.toString();
    }

    public boolean createSaleItem(String code, int quantity) {
        Product product = DataBase.selectProduct(code);

        if (product == null) {
            System.out.println("Erro: Produto não encontrado para o código " + code);
            return false;
        }

        if (!DataBase.hasStock(code, quantity)) {
            System.out.println("Erro: Estoque insuficiente para o produto " + product.getDescription());
            return false;
        }

        if (DataBase.reduceStock(code, quantity)) {
            items.add(new SaleItem(product, quantity));
            return true;
        }

        return false;
    }

    public double getTotal() {
        return items.stream().mapToDouble(SaleItem::getSubtotal).sum();
    }

    public List<String> getItems() {
        List<String> itemsStr = new ArrayList<>();
        for (SaleItem item : items) {
            itemsStr.add(item.toString());
        }
        return itemsStr;
    }

    public double getFinalPrice() {
        return paymentMethod.calculate(getTotal());
    }
}