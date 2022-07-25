package com.example.demo.service;

import com.example.demo.model.User;
import com.example.demo.repository.AerospikeUserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
@Setter
public class Statistics extends Thread {
    AerospikeUserRepository aerospikeUserRepository;
    int start ;
    int end;
    ArrayList<UserService.Pair> StatsArray;

    public void run(){

        ArrayList<User> users =(ArrayList<User>) aerospikeUserRepository.findAll();
        User maxBalance  = users.get(start);
        User minBalance = users.get(start);
        User mostActive = users.get(start);
        User leastActive = users.get(start);

        start++;
        for (int i = start; i <end ; i++) {

            if(users.get(i).getTransactions()==null){
                users.get(i).setTransactions(new ArrayList<>());
            }

            if(users.get(i).getBalance()>maxBalance.getBalance()){
                maxBalance = users.get(i);
            }
            if(users.get(i).getBalance()<minBalance.getBalance()){
                minBalance = users.get(i);
            }
            if(users.get(i).getTransactions().size()>mostActive.getTransactions().size()){
                mostActive = users.get(i);
            }
            if(users.get(i).getTransactions().size()<leastActive.getTransactions().size()){
                leastActive = users.get(i);
            }
        }
        StatsArray.add(new UserService.Pair(maxBalance.getBalance(),maxBalance.getId()));
        StatsArray.add(new UserService.Pair(minBalance.getBalance(),minBalance.getId()));
        StatsArray.add(new UserService.Pair(mostActive.getBalance(),mostActive.getId()));
        StatsArray.add(new UserService.Pair(leastActive.getBalance(),leastActive.getId()));

    }
}
