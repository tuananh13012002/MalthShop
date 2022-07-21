package ModelLaptop;

public class Brand {
    private String brandName;
    private String brandPicture;
    private int idType;

    public Brand(String brandName, String brandPicture, int idType) {
        this.brandName = brandName;
        this.brandPicture = brandPicture;
        this.idType = idType;
    }

    public Brand(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandPicture() {
        return brandPicture;
    }

    public void setBrandPicture(String brandPicture) {
        this.brandPicture = brandPicture;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }
}
