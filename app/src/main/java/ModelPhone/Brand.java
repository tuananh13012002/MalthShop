package ModelPhone;

import java.io.Serializable;

public class Brand implements Serializable {
    private String BrandName;
    private String BrandPicture;
    private int IdType;

    public Brand(String brandName, String brandPicture, int idType) {
        BrandName = brandName;
        BrandPicture = brandPicture;
        IdType = idType;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public String getBrandPicture() {
        return BrandPicture;
    }

    public void setBrandPicture(String brandPicture) {
        BrandPicture = brandPicture;
    }

    public int getIdType() {
        return IdType;
    }

    public void setIdType(int idType) {
        IdType = idType;
    }
}
