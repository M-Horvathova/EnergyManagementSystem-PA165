package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class HouseDaoImpl implements HouseDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(House house) {
        em.persist(house);
    }

    @Override
    public House findById(Long id) {
        return em.find(House.class, id);
    }

    @Override
    public House findByName(String name) {
        return em.createQuery("select h from House h where h.name = :name", House.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<House> findByAddress(Address address) {
        return em.createQuery("select h from House h where h.address = :address", House.class)
                .setParameter("address", address)
                .getResultList();
    }

    @Override
    public List<House> findAll() {
        return em.createQuery("select h from House h", House.class).getResultList();
    }

    @Override
    public void delete(House house) {
        em.remove(house);
    }
}