package shit.htmlforms.models;

public class User {
    private Integer id;
    private String names;
    private String email;
    private Double money;

    public User(Integer id, String names, String email, Double money) {
        this.id = id;
        this.names = names;
        this.email = email;
        this.money = money;
    }

    public User(String names, String email, Double money) {
        this.names = names;
        this.email = email;
        this.money = money;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", names='" + names + '\'' +
                ", email='" + email + '\'' +
                ", money=" + money +
                '}';
    }
}
