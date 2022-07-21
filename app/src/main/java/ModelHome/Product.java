package ModelHome;

import java.io.Serializable;

public class Product implements Serializable {
        private int id;
        private String productName;
        private String brand;
        private double price;
        private int status;
        private String description;
        private String imgProduct;
        private int type;

        public Product(int id, String productName, String brand, double price, int status, String description, String imgProduct, int type) {
            this.id = id;
            this.productName = productName;
            this.brand = brand;
            this.price = price;
            this.status = status;
            this.description = description;
            this.imgProduct = imgProduct;
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getBrand() {
            return brand;
        }

        public void setBrand(String brand) {
            this.brand = brand;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getImgProduct() {
            return imgProduct;
        }

        public void setImgProduct(String imgProduct) {
            this.imgProduct = imgProduct;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
}
