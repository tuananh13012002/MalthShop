package CartManager;

import java.io.Serializable;

public class Cart implements Serializable {
    private int id;
    private String name;
    private String image;
    private int quantity;
    private double totalPrice;
    private double price;
    private int type;
    private int idBill;

    public Cart(int id, String name, String image, int quantity, double totalPrice, double price, int type, int idBill) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.price = price;
        this.type = type;
        this.idBill = idBill;
    }

    public int getIdBill() {
        return idBill;
    }

    public void setIdBill(int idBill) {
        this.idBill = idBill;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
