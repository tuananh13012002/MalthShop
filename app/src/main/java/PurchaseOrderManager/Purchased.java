package PurchaseOrderManager;

import java.io.Serializable;

public class Purchased implements Serializable {
    private int id;
    private String name;
    private String image;
    private int quantity;
    private int type;
    private double price;
    private double totalPrice;

    public Purchased(int id, String name, String image, int quantity, int type, double price, double totalPrice) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.type = type;
        this.price = price;
        this.totalPrice = totalPrice;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
