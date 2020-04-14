
package com.example.db.jpa;

import com.example.db.CustomerRepository;
import com.example.domain.Customer;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Repository
public class JpaCustomerRepository implements CustomerRepository {

    @PersistenceContext  //并不会真正注入EntityManager，因为它不是线程安全的
    private EntityManager entityManager;

//    @PersistenceUnit
//    private EntityManagerFactory emf;
//    emf.createEntityManager();

    @Override
    public long count() {
        return findAll().size();
    }

    @Override
    public Customer save(Customer customer) {
        entityManager.persist(customer);
        return customer;
    }

    @Override
    public Customer findOne(long id) {
        return entityManager.find(Customer.class, id);
    }

    @Override
    public Customer findByName(String name) {
        return (Customer) entityManager.createQuery("select c from Customer c where c.name=?")
                .setParameter(1, name).getSingleResult();
    }

    @Override
    public List<Customer> findAll() {
        return (List<Customer>) entityManager.createQuery("select c from Customer c")
                .getResultList();
    }

}