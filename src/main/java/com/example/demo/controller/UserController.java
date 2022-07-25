package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users/{id}")
    public Optional<User> readUserById(@PathVariable int  id) {
        return userService.readUserById(id);
    }

    @PostMapping("/users")
    public void addUser(@RequestBody User user) {
        userService.addUser(user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable int id) {
        userService.removeUserById(id);
    }
    @GetMapping("/users/getBalance/{id}")
    public String  checkBalance(@PathVariable int id ){
        return "Your current balance is "+userService.checkBalance(id)+"$";
    }
    @PutMapping("/users/deposit/{id}")
    public String depositMoney(@PathVariable int id ,@RequestBody int amount){
        return userService.depositMoney(id,amount);
    }
    @GetMapping("/users/BriefTransactions/{id}")
    public String getBriefTransaction(@PathVariable int id ){
        return userService.getBriefTransactionHistory(id);
    }
    @GetMapping("/users/DetailedTransactions/{id}")
    public String getDetailedTransaction(@PathVariable int id ){
        return userService.getDetailedTransactionHistory(id);
    }
    @PutMapping("/users/withdraw/{id}")
    public String withdrawMoney(@PathVariable int id ,@RequestBody int amount){
        return userService.withdrawMoney(id,amount);
    }
    @GetMapping("/users/statistics/{id}")
    public ArrayList<String> getStatistics(@PathVariable int id ){
        return userService.getStatistics(id);
    }
    @GetMapping("/stats")
    public String getBankStats() throws InterruptedException {
        return userService.getBankStatistics();
    }
    @GetMapping("/all")
    public ArrayList<User> getAll(){
        return userService.getAllUsers();
    }

}