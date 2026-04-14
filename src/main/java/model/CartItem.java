package model;

public class CartItem {

    private Wine wine;
    private int quantity;

    public CartItem() {
    }

    public CartItem(Wine wine, int quantity) {
        this.wine = wine;
        this.quantity = quantity;
    }

    public Wine getWine() {
        return wine;
    }

    public void setWine(Wine wine) {
        this.wine = wine;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        if (wine == null) {
            return 0;
        }
        return wine.getPrice() * quantity;
    }
}
