package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.AerospikeUserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.el.parser.AstGreaterThan;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class UserService{

    AerospikeUserRepository aerospikeUserRepository;

    public Optional<User> readUserById(int id) {
        return aerospikeUserRepository.findById(id);
    }

    public void addUser(User user) {
        aerospikeUserRepository.save(user);
    }

    public void removeUserById(int id)
    {
        aerospikeUserRepository.deleteById(id);
    }
    public long checkBalance(int id)
    {
       User usr =  aerospikeUserRepository.findById(id).get();
       return usr.getBalance();
    }
    public ArrayList<User> getAllUsers(){
        return (ArrayList<User>) aerospikeUserRepository.findAll();
    }
    public String withdrawMoney(int id ,int amount)
    {
        User usr =  aerospikeUserRepository.findById(id).get();
        if(usr.getBalance()>=amount)
        {
            usr.setBalance(usr.getBalance()-amount);
            String transaction = "You withdrew "+amount+"$ from your account";
            ArrayList<String> transactionHistory = usr.getTransHistory();
            ArrayList<Integer> transactions= usr.getTransactions();

            if(transactions == null){
                transactions = new ArrayList<>();
            }
            if(transactionHistory == null){
                transactionHistory = new ArrayList<>();
            }

            transactions.add(-1*amount);
            transactionHistory.add(transaction);
            usr.setTransHistory(transactionHistory);
            usr.setTransactions(transactions);
            aerospikeUserRepository.save(usr);
            return "Withdraw transaction done successfully";
        }
        return "You don't have enough balance";
    }
    public String depositMoney(int id ,int amount)
    {
        User usr =  aerospikeUserRepository.findById(id).get();
        long newBalance = amount + usr.getBalance();

        String transaction = "You added "+amount+"$ to your account";
        ArrayList<String> transactionHistory = usr.getTransHistory();
        ArrayList<Integer> transactions = usr.getTransactions();

        if(transactionHistory== null){
            transactionHistory = new ArrayList<>();
        }
        transactionHistory.add(transaction);

        if(transactions == null){
            transactions = new ArrayList<>();
        }
        transactions.add(amount);

        usr.setTransactions(transactions);
        usr.setTransHistory(transactionHistory);
        usr.setBalance(newBalance);
        aerospikeUserRepository.save(usr);
        return "Deposit transaction done successfully";
    }
    public String getDetailedTransactionHistory(int id){
        User usr = aerospikeUserRepository.findById(id).get();
        return getTransactionHistoryGeneral(usr.getTransHistory());
    }
    public String getBriefTransactionHistory(int id){
        User usr = aerospikeUserRepository.findById(id).get();
        return getTransactionHistoryGeneral(usr.getTransactions());
    }

    public <E> String getTransactionHistoryGeneral(ArrayList<E> GenericTransactions){
        String transactionHistory = new String();
        for(E element:GenericTransactions){
            transactionHistory+=element+"\n";
        }
        return transactionHistory;
    }
    public ArrayList<String> getStatistics(int id){
        ArrayList<String> UserStatistics = new ArrayList<>();
        User usr = aerospikeUserRepository.findById(id).get();
        ArrayList<Integer> transactions = usr.getTransactions();
        if(transactions == null){
            UserStatistics.add("No transactions so far");
            return UserStatistics;
        }
        IntSummaryStatistics stats = transactions.stream().mapToInt((x) -> x).summaryStatistics();
        UserStatistics.add("largest deposit : " + stats.getMax());
        UserStatistics.add("largest withdraw : " + stats.getMin()*-1);
        UserStatistics.add("Sum of all transactions made: " + stats.getMax());
        UserStatistics.add("You made "+ stats.getCount()+" transactions so far");
        return UserStatistics;
    }

    @AllArgsConstructor
    @Getter
    @Setter

    static class Pair{
        long balance;
        int id;
    }
    public String getBankStatistics() throws InterruptedException {
        ArrayList<User> users = (ArrayList<User>) aerospikeUserRepository.findAll();

        ArrayList<Pair> StatsArrayFirstHalf = new ArrayList<>();
        Statistics stat1 = new Statistics(aerospikeUserRepository,0,users.size()/2,StatsArrayFirstHalf);
        stat1.start();

        ArrayList<Pair> StatsArraySecondHalf = new ArrayList<>();
        Statistics stat2 = new Statistics(aerospikeUserRepository,users.size()/2,users.size(),StatsArraySecondHalf);
        stat2.start();

        ArrayList<Pair> StatsResultFirstHalf = stat1.getStatsArray();
        ArrayList<Pair> StatsResultSecondHalf = stat2.getStatsArray();

        int maxBalanceIndex  = 0;
        int minBalanceIndex  = 1;
        int mostActiveIndex  = 2;
        int leastActiveIndex = 3;

        TimeUnit.SECONDS.sleep(10);

        System.out.println(StatsResultFirstHalf.size());

        Pair maxBalance = StatsResultFirstHalf.indexOf(maxBalanceIndex)>StatsResultSecondHalf.indexOf(maxBalanceIndex)?StatsResultFirstHalf.get(maxBalanceIndex):StatsResultSecondHalf.get(maxBalanceIndex);
        Pair minBalance = StatsResultFirstHalf.indexOf(minBalanceIndex)<StatsResultSecondHalf.indexOf(minBalanceIndex)?StatsResultFirstHalf.get(minBalanceIndex):StatsResultSecondHalf.get(minBalanceIndex);
        Pair mostActive = StatsResultFirstHalf.indexOf(mostActiveIndex)>StatsResultSecondHalf.indexOf(mostActiveIndex)?StatsResultFirstHalf.get(mostActiveIndex):StatsResultSecondHalf.get(mostActiveIndex);
        Pair leasActive = StatsResultFirstHalf.indexOf(leastActiveIndex)<StatsResultSecondHalf.indexOf(leastActiveIndex)?StatsResultFirstHalf.get(leastActiveIndex):StatsResultSecondHalf.get(leastActiveIndex);

        String StatsFinalResult = "The highest balance account in the bank has "+maxBalance.getBalance()+" and it belongs to user"+maxBalance.getId()+"\n"+
                                  "The lowest balance account in the bank has "+minBalance.getBalance()+" and it belongs to user"+minBalance.getId()+"\n"+
                                  "The most active account in the bank has "+mostActive.getBalance()+" and it belongs to user"+mostActive.getId()+"\n"+
                                  "The least active account in the bank has "+leasActive.getBalance()+" and it belongs to user"+leasActive.getId();
        return StatsFinalResult;



    }


}
