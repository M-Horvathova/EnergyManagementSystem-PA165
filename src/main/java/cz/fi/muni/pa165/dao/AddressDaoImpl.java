package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Address;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author Patrik Valo
 */
@Repository
public class AddressDaoImpl implements AddressDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Address address) {
        em.persist(address);
    }

    @Override
    public Address update(Address address) {
        return em.merge(address);
    }

    @Override
    public Address findById(Long id) {
        return em.find(Address.class, id);
    }

    @Override
    public List<Address> findAll() {
        return em.createQuery("select a from Address a", Address.class).getResultList();
    }

    @Override
    public void delete(Address address) {
        address = em.merge(address);
        em.remove(address);
    }
}
