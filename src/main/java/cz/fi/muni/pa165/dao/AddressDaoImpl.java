package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.Address;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
        address = em.merge(address);
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
        return em.createQuery("SELECT a FROM Address a", Address.class).getResultList();
    }

    @Override
    public Address find(String street, String code, String city, String postCode, String country) {
        String sqlQuery = "SELECT a FROM Address a " +
                "WHERE a.city = :city " +
                "AND a.postCode = :postCode " +
                "AND a.country = :country";

        sqlQuery += street == null ? " AND a.street IS NULL" : " AND a.street = :street";
        sqlQuery += code == null ? " AND a.code IS NULL" : " AND a.code = :code";

        var query = em.createQuery(sqlQuery, Address.class)
                .setParameter("city", city)
                .setParameter("postCode", postCode)
                .setParameter("country", country);

        query = street != null ? query.setParameter("street", street) : query;
        query = code != null ? query.setParameter("code", code) : query;

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void delete(Address address) {
        address = em.merge(address);
        em.remove(address);
    }
}
