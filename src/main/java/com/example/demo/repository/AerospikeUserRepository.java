package com.example.demo.repository;


import com.example.demo.model.User;
import org.springframework.data.aerospike.repository.AerospikeRepository;
import org.springframework.data.aerospike.repository.ReactiveAerospikeRepository;

public interface AerospikeUserRepository extends AerospikeRepository<User, Object> {
}