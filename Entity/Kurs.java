package Entity;


import javax.persistence.*;

@Entity
@Table(name="CurrencyRates")
public class Kurs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int currencyId;
    private String currencyName;
    private double rate;


    public Kurs() {
    }

    public Kurs(String currencyName, double rate) {
        this.currencyName = currencyName;
        this.rate = rate;
    }

    public int getCurrencyId() {
        return currencyId;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public double getRate() {
        return rate;
    }

    @Override
    public String toString() {
        return "Kurs{" +
                "currencyId=" + currencyId +
                ", currencyName='" + currencyName + '\'' +
                ", rate=" + rate +
                '}';
    }
}
