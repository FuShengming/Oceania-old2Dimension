
package com.example.db.jpa;

import com.example.db.CargoRepository;
import com.example.domain.Cargo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class JpaCargoRepository implements CargoRepository {

    @PersistenceContext  //并不会真正注入EntityManager，因为它不是线程安全的
    private EntityManager entityManager;

    @Override
    public long count() {
        return findAll().size();
    }

    @Override
    public List<Cargo> findRecent() {
        return findRecent(10);
    }

    @Override
    public List<Cargo> findRecent(int count) {
        return (List<Cargo>) entityManager.createQuery("select cg from Cargo cg order by cg.orderDate desc")
                .setMaxResults(count).getResultList();
    }

    @Override
    public Cargo findOne(long id) {
        return entityManager.find(Cargo.class, id);
    }

    @Override
    public Cargo save(Cargo cargo) {
        entityManager.persist(cargo);
        return cargo;
    }

    @Override
    public List<Cargo> findByCustomerId(long customerId) {
        return (List<Cargo>) entityManager.createQuery("select cg from Cargo cg, Customer c where cg.customer = c and c.id=? order by cg.orderDate desc")
                .setParameter(1, customerId).getResultList();
    }

    @Override
    public void delete(long id) {
        entityManager.remove(findOne(id));
    }

    @Override
    public List<Cargo> findAll() {
        return (List<Cargo>) entityManager.createQuery("select cg from Cargo cg").getResultList();
    }
}