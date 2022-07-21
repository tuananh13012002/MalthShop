package ModelHome;

public class Brand {
    private String img;
    private String brandName;

    public Brand(String img, String brandName) {
        this.img = img;
        this.brandName = brandName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
