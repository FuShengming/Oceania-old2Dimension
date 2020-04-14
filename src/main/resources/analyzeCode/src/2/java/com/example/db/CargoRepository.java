package com.example.db;

import com.example.domain.Cargo;

import java.util.List;

public interface CargoRepository {
    long count();

    List<Cargo> findRecent();

    List<Cargo> findRecent(int count);

    Cargo findOne(long id);

    Cargo save(Cargo cargo);

    List<Cargo> findByCustomerId(long customerId);

    void delete(long id);

    List<Cargo> findAll();
}