package PayManager;

public class PayProduct{
    private String nameProduct;
    private String imgProduct;
    private String orderDate;
    private int quantity;
    private double price;
    private double totalPrice;
    private int type;



    public PayProduct(String nameProduct, String imgProduct, String orderDate, int quantity, double price, int type) {
        this.nameProduct = nameProduct;
        this.imgProduct = nameProduct;
        this.imgProduct = imgProduct;
        this.orderDate = orderDate;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = quantity * price;
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getImgProduct() {
        return imgProduct;
    }

    public void setImgProduct(String imgProduct) {
        this.imgProduct = imgProduct;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
