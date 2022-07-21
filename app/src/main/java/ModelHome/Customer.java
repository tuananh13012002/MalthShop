package ModelHome;

public class Customer {
    private String username;
    private String password;
    private String fullname;
    private int age;
    private String address;

    public Customer() {
    }

    public Customer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Customer(String username, String password, String fullname, int age, String address) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.age = age;
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
