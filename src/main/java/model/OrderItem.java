package model;

public class OrderItem {

    private int id;
    private int orderId;
    private int wineId;
    private String wineName;   // preenchido via JOIN ao listar pedidos
    private int quantity;
    private double unitPrice;

    public OrderItem() {}

    public OrderItem(int wineId, int quantity, double unitPrice) {
        this.wineId    = wineId;
        this.quantity  = quantity;
        this.unitPrice = unitPrice;
    }

    public double getSubtotal() {
        return unitPrice * quantity;
    }

    // Getters e Setters
    public int getId()                      { return id; }
    public void setId(int id)               { this.id = id; }

    public int getOrderId()                 { return orderId; }
    public void setOrderId(int orderId)     { this.orderId = orderId; }

    public int getWineId()                  { return wineId; }
    public void setWineId(int wineId)       { this.wineId = wineId; }

    public String getWineName()             { return wineName; }
    public void setWineName(String wineName){ this.wineName = wineName; }

    public int getQuantity()                { return quantity; }
    public void setQuantity(int quantity)   { this.quantity = quantity; }

    public double getUnitPrice()            { return unitPrice; }
    public void setUnitPrice(double unitPrice) { this.unitPrice = unitPrice; }
}