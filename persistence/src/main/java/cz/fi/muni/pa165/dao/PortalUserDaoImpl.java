package cz.fi.muni.pa165.dao;

import cz.fi.muni.pa165.entity.PortalUser;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.regex.Pattern;
import java.util.List;

/**
 * @author Martin Podhora
 */
@Repository
public class PortalUserDaoImpl implements PortalUserDao {
    private static final String EMAIL_PATTERN = ".+@.+\\....?";
    private static final String PHONE_PATTERN = "\\+?\\d+";

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(PortalUser portalUser) {
        portalUser.setCreatedTimestamp(LocalDateTime.now());
        userValidation(portalUser);
        em.persist(portalUser);
    }

    @Override
    public PortalUser findById(Long id) {
        return em.find(PortalUser.class, id);
    }

    @Override
    public List<PortalUser> findAll() {
        return em.createQuery("SELECT pu FROM PortalUser pu", PortalUser.class).getResultList();
    }

    @Override
    public List<PortalUser> findAll(int page, int itemsCount) {
        TypedQuery<PortalUser> query = em.createQuery("SELECT pu FROM PortalUser pu", PortalUser.class);
        query.setFirstResult(page);
        query.setMaxResults(itemsCount);
        return query.getResultList();
    }

    @Override
    public Long getTotalUsersCount() {
        Query countQuery = em.createQuery("SELECT COUNT(pu.id) FROM PortalUser pu");
        return (Long) countQuery.getSingleResult();
    }

    @Override
    public PortalUser findByUserName(String userName) {
        try {
            return em.createQuery("SELECT pu FROM PortalUser pu where email = :userName", PortalUser.class)
                    .setParameter("userName", userName)
                    .getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public void update(PortalUser portalUser) {
        userValidation(portalUser);
        em.merge(portalUser);
    }

    @Override
    public void delete(PortalUser portalUser) {
        portalUser = em.merge(portalUser);
        em.remove(portalUser);
    }

    private void userValidation(PortalUser pu) {
        if (pu.getEmail() != null && !Pattern.matches(EMAIL_PATTERN, pu.getEmail()))  {
            throw new IllegalArgumentException("Email is not in correct format");
        }

        if (pu.getPhone() != null &&!Pattern.matches(PHONE_PATTERN, pu.getPhone()))  {
            throw new IllegalArgumentException("Phone is not in correct format");
        }
    }
}
