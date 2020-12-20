import Entity.Account;
import Entity.Client;
import Entity.Kurs;

import javax.persistence.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Assistent {
    private EntityManagerFactory emf;
    private EntityManager em;
    private EntityTransaction et;

    public Assistent() {
        emf= Persistence.createEntityManagerFactory("Myjp");
        em=emf.createEntityManager();


    }
    public void addClienToDB(Client client) throws SQLException {
        em.getTransaction().begin();
        em.persist(client);
        em.getTransaction().commit();
    }

    public void addAccountToDB(Account account) throws SQLException {
        em.getTransaction().begin();
        em.persist(account);
        em.getTransaction().commit();
    }

    public void addKursToDB(Kurs kurs){
       em.getTransaction().begin();
       em.persist(kurs);
       em.getTransaction().commit();
    }

    public void addMoneyOnAccount(int clientId, String currencyName,double amountMoney ){
        em.getTransaction().begin();
        double newAmount=0;
        Account account;
        Query query=em.createQuery(
        "SELECT A FROM Account A WHERE A.currencyName = :currencyName and A.client.clientId = :clientID",Account.class);
        query.setParameter("currencyName",currencyName);
        query.setParameter("clientID",clientId);
        account=(Account) query.getSingleResult();

        if(account!=null){
            newAmount=amountMoney+account.getAmountMoney();
        }else System.out.println("Client with id/currency account "+clientId+"doesnt exist");

        account.setAmountMoney(newAmount);

        em.getTransaction().commit();
    }

    public void transferMoney(int accountNumberFrom, int accountNumberTo, double amount){
        Account accountfrom;
        Account accountTo;
        em.getTransaction().begin();

        Query query1=em.createQuery("SELECT A FROM Account A WHERE A.number = :numberFrom",Account.class);
        query1.setParameter("numberFrom",accountNumberFrom);
        Query query2=em.createQuery("SELECT A FROM Account A WHERE A.number = :numberTo",Account.class);
        query2.setParameter("numberTo",accountNumberTo);

        accountfrom=(Account) query1.getSingleResult();
        accountTo=(Account) query2.getSingleResult();

        if(accountfrom!=null&&accountTo!=null){
            if (accountfrom.getCurrencyName().equals(accountTo.getCurrencyName())){
                if (accountfrom.getAmountMoney()>amount){
                    accountfrom.setAmountMoney(accountfrom.getAmountMoney()-amount);
                    accountTo.setAmountMoney(accountTo.getAmountMoney()+amount);
                }else {
                    System.out.println("not enough money on the account");
                }
            }else {
                System.out.println("different currency name");
            }
        }else {
            System.out.println("incorrect account number");
        }
        em.getTransaction().commit();
    }

    public void convertMoney(int clientID, String currencyFrom, String currencyTo, double amount){
        Account accountFrom;
        Account accountTo;
        double rate1;
        double rate2;

        em.getTransaction().begin();
        Query query=em.createQuery(
                "SELECT A FROM Account A WHERE A.currencyName = :cName and A.client.clientId = :id",Account.class);
        query.setParameter("cName",currencyFrom);
        query.setParameter("id",clientID);

        Query query2=em.createQuery("SELECT A FROM Account A WHERE A.currencyName = :cName and A.client.clientId = :id",Account.class);
        query2.setParameter("cName",currencyTo);
        query2.setParameter("id",clientID);

        Query query3=em.createQuery("SELECT K FROM Kurs K WHERE K.currencyName = :cName",Kurs.class);
        query3.setParameter("cName",currencyFrom);
        rate1=((Kurs)query3.getSingleResult()).getRate();

        Query query4=em.createQuery("SELECT K FROM Kurs K WHERE K.currencyName = :cName",Kurs.class);
        query4.setParameter("cName",currencyTo);
        rate2=((Kurs)query4.getSingleResult()).getRate();

        accountFrom=(Account)query.getSingleResult();
        accountTo=(Account) query2.getSingleResult();

        if(accountFrom!=null&&accountTo!=null){

            if (currencyFrom.equals("UAH")){
                accountFrom.setAmountMoney(accountFrom.getAmountMoney()-amount);
                accountTo.setAmountMoney(accountTo.getAmountMoney()+(amount/rate2));

            }else if (!currencyFrom.equals("UAH")){
                accountFrom.setAmountMoney(accountFrom.getAmountMoney()-amount);
                accountTo.setAmountMoney(accountTo.getAmountMoney()+(amount*rate1));
            }else System.out.println("Incorrect currency");
        }
        em.getTransaction().commit();
    }

    public double getAllMoneyInUAH(int clientId){
        double amount=0;
        List<Account> accounts;
        List<Kurs> kursList=getKursList();

        Query query=em.createQuery("SELECT kurs FROM Account kurs WHERE kurs.client.clientId = :id",Account.class);
        query.setParameter("id",clientId);
        accounts=(List<Account>) query.getResultList();

        for (Account account:accounts){
            for (Kurs kurs:kursList){

                if(account.getCurrencyName().equals(kurs.getCurrencyName())){
                    amount+=account.getAmountMoney()*kurs.getRate();
                }
            }
        }
        return amount;
    }


    public List<Kurs> getKursList(){
        em.getTransaction().begin();
        List<Kurs> list;

        Query query=em.createQuery("SELECT k FROM Kurs k",Kurs.class);
        list=(List<Kurs>) query.getResultList();

        return list;
    }

    public void getClose(){
        em.close();
        emf.close();

    }
}
