package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Address;
import cz.fi.muni.pa165.entity.House;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Patrik Valo
 */
@Repository
@Transactional
public class HouseDaoImpl implements HouseDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(House house) {
        em.persist(house);
    }

    @Override
    public House update(House house) {
        return em.merge(house);
    }

    @Override
    public House findById(Long id) {
        return em.find(House.class, id);
    }

    @Override
    public House findByName(String name) {
        return em.createQuery("SELECT h FROM House h WHERE h.name = :name", House.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<House> findByAddress(Address address) {
        return em.createQuery("SELECT h FROM House h WHERE h.address = :address", House.class)
                .setParameter("address", address)
                .getResultList();
    }

    @Override
    public List<House> findAll() {
        return em.createQuery("SELECT h FROM House h", House.class).getResultList();
    }

    @Override
    public void delete(House house) {
        house = em.merge(house);
        em.remove(house);
    }
}
