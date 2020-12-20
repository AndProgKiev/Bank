package Entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clientId;
    private String name;
    private String surname;
    private int age;
@OneToMany(mappedBy="client", cascade = CascadeType.ALL)
private List<Account> accounts;
@OneToMany(mappedBy = "client",cascade = CascadeType.ALL)
private List<Transaction> transactions;

    public Client() {
    }

    public Client(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
       accounts=new ArrayList<>();
       transactions=new ArrayList<>();
    }

      public List<Account> getAccounts() {
            return accounts;
        }

      public List<Transaction> getTransactions() {
            return transactions;
        }



    public int getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAge(int age) {
        this.age = age;
    }

     public void setAccounts(List<Account> accounts) {
         this.accounts = accounts;
     }

     public void  addAccount(Account account){
        account.setClient(this);
        accounts.add(account);
     }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }


}
