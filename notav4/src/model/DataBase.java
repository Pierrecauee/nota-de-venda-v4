package model;

import java.util.HashMap;
import java.util.Map;

public class DataBase {
    private static Map<String, Integer> stock = new HashMap<>();
    private static Map<String, Product> products = new HashMap<>();

    static {
        Product p1 = new Product();
        p1.setDescription("Produto A");
        p1.setPrice(10.0);
        products.put("at", p1);
        stock.put("at", 5);

        Product p2 = new Product();
        p2.setDescription("Produto B");
        p2.setPrice(15.0);
        products.put("ff", p2);
        stock.put("ff", 2);

        Product p3 = new Product();
        p3.setDescription("Produto C");
        p3.setPrice(20.0);
        products.put("msa", p3);
        stock.put("msa", 3);
    }

    public static Product selectProduct(String code) {
        return products.get(code);
    }

    public static boolean hasStock(String code, int quantity) {
        return stock.getOrDefault(code, 0) >= quantity;
    }

    public static boolean reduceStock(String code, int quantity) {
        if (hasStock(code, quantity)) {
            stock.put(code, stock.get(code) - quantity);
            return true;
        }
        return false;
    }

    public static PaymentMethod selectPayment(String paymentCode) {
        switch (paymentCode) {
            case "c": // Cartão de Crédito
                return new CreditCard(5.0); // Exemplo de taxa: 5%
            case "d": // Cartão de Débito
                return new DebitCard(2.0); // Exemplo de taxa: 2%
            case "p": // Pix ou Dinheiro
                return new Cash(10.0); // Exemplo de desconto: 10%
            default:
                throw new IllegalArgumentException("Forma de pagamento inválida: " + paymentCode);
        }
    }
}