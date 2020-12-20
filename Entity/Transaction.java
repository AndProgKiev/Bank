package Entity;


import javax.persistence.*;

@Entity
@Table(name="Transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;
    private String transactionName;
    @ManyToOne
    @JoinColumn(name = "clientId")
    Client client;

    public Transaction() {
    }

    public Transaction(String transactionName, Client client) {
        this.transactionName = transactionName;
        this.client = client;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", transactionName='" + transactionName + '\'' +
                ", client=" + client +
                '}';
    }
}
