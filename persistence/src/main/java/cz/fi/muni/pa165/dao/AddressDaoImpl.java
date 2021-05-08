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
        if (street == null && code == null) {
            return this.findWithoutNullable(city, postCode, country);
        }

        if (street == null) {
            return this.findWithoutStreet(code, city, postCode, country);
        }

        if (code == null) {
            return this.findWithoutCode(street, city, postCode, country);
        }

        try {
            return em.createQuery("SELECT a FROM Address a " +
                    "WHERE a.street = :street AND a.code = :code AND a.city = :city " +
                    "AND a.postCode = :postCode AND a.country = :country", Address.class)
                    .setParameter("street", street)
                    .setParameter("code", code)
                    .setParameter("city", city)
                    .setParameter("postCode", postCode)
                    .setParameter("country", country)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    private Address findWithoutStreet(String code, String city, String postCode, String country) {
        try {
            return em.createQuery("SELECT a FROM Address a " +
                    "WHERE a.street IS NULL AND a.code = :code AND a.city = :city " +
                    "AND a.postCode = :postCode AND a.country = :country", Address.class)
                    .setParameter("code", code)
                    .setParameter("city", city)
                    .setParameter("postCode", postCode)
                    .setParameter("country", country)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    private Address findWithoutCode(String street, String city, String postCode, String country) {
        try {
            return em.createQuery("SELECT a FROM Address a " +
                    "WHERE a.street = :street AND a.code IS NULL AND a.city = :city " +
                    "AND a.postCode = :postCode AND a.country = :country", Address.class)
                    .setParameter("street", street)
                    .setParameter("city", city)
                    .setParameter("postCode", postCode)
                    .setParameter("country", country)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    private Address findWithoutNullable(String city, String postCode, String country) {
        try {
            return em.createQuery("SELECT a FROM Address a " +
                    "WHERE a.street IS NULL and a.code IS NULL and a.city = :city " +
                    "AND a.postCode = :postCode AND a.country = :country", Address.class)
                    .setParameter("city", city)
                    .setParameter("postCode", postCode)
                    .setParameter("country", country)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void delete(Address address) {
        address = em.merge(address);
        em.remove(address);
    }
}
