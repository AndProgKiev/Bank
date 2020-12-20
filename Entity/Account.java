package Entity;


import javax.persistence.*;

@Entity
@Table(name = "Accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int accountId;
    private int number;
    private String currencyName;
    private Double amountMoney;
    @ManyToOne
    @JoinColumn(name ="clientId")
    private Client client;

    public Account() {
    }

    public Account(int number, String currency,Double amountMoney, Client client) {
        this.number = number;
        this.currencyName = currency;
        this.client = client;
        this.amountMoney=amountMoney;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getNumber() {
        return number;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public Client getClient() {
        return client;
    }

    public Double getAmountMoney() {
        return amountMoney;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setCurrencyName(String currency) {
        this.currencyName = currency;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setAmountMoney(Double amountMoney) {
        this.amountMoney = amountMoney;
    }

    @Override
    public String toString() {
        return "Account{" +
                "number=" + number +
                ", currency='" + currencyName + '\'' +
                ", amountMoney=" + amountMoney +
                ", client=" + client +
                '}';
    }
}