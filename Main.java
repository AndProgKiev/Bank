import Entity.Account;
import Entity.Client;
import Entity.Kurs;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
       Assistent assistent=new Assistent();

        Kurs kursUSD=new Kurs("USD",28.5);
        Kurs kursUAH=new Kurs("UAH",1);
        Kurs kursUER=new Kurs("EUR",34.1);
            assistent.addKursToDB(kursUAH);
            assistent.addKursToDB(kursUSD);
            assistent.addKursToDB(kursUER);

        Client client=new Client("Bobbi","Root",18);
        Client client2=new Client("Tom","Ford",46);
            assistent.addClienToDB(client);
            assistent.addClienToDB(client2);

        Account account=new Account(262055550,"USD",10.0,client);
        Account account1=new Account(262055510,"UAH",1000.0,client2);
        Account account2 =new Account(262055520,"UAH",300.12,client);

        assistent.addAccountToDB(account);
        assistent.addAccountToDB(account1);
        assistent.addAccountToDB(account2);

    //    assistent.addMoneyOnAccount(2,"UAH",50.0); // поповнення рахунку
    //    assistent.transferMoney(262055510,262055520,500.0); // переказ коштів з одного рахунку на інший

    //    System.out.println("All money,by clientID: "+1+" = "+assistent.getAllMoneyInUAH(1)); // стан всіх рахунків в грн.

        assistent.convertMoney(1,"USD","UAH",7); // конверація грошей(купівля/продаж валюти)

        assistent.getClose();

    }
}

