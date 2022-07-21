package ModelLaptop;

public class TypeProduct {
    private int id;
    private String nameProduct;
    private String imageProduct;

    public TypeProduct(int id, String nameProduct, String imageProduct) {
        this.id = id;
        this.nameProduct = nameProduct;
        this.imageProduct = imageProduct;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public String getImageProduct() {
        return imageProduct;
    }

    public void setImageProduct(String imageProduct) {
        this.imageProduct = imageProduct;
    }
}
