package ru.bellintegrator.myapp.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.myapp.model.OwnerBase;
import ru.bellintegrator.myapp.model.PassportKey;
import ru.bellintegrator.myapp.dao.BankDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by MADmitriev on 30.05.2017.
 */
@Repository
public class OwnerBaseDAO implements BankDAO<OwnerBase> {

    private final Logger log = LoggerFactory.getLogger(AccountBaseDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void create(OwnerBase ownerBase) {
        try {
            entityManager.persist(ownerBase);
        } catch (Exception e) {
            log.info("Creation error. Can not persist ownerBase\nException: ", e);
        }
    }

    @Transactional
    @Override
    public void insert(OwnerBase ownerBase) {
        try {
            entityManager.persist(ownerBase);
        } catch (Exception e) {
            log.info("Insertion error. Can not persist ownerBase\nException: ", e);
        }
    }

    @Transactional
    @Override
    public OwnerBase update(OwnerBase ownerBase) {
        try {
            return entityManager.merge(ownerBase);
        } catch (Exception e) {
            log.info("Update error. Can not merge ownerBase\nException: ", e);
            return null;
        }
    }

    @Override
    public List<OwnerBase> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OwnerBase> query = criteriaBuilder.createQuery(OwnerBase.class);
        Root<OwnerBase> root = query.from(OwnerBase.class);
        query.select(root);
        TypedQuery<OwnerBase> allOwners = entityManager.createQuery(query);
        return allOwners.getResultList();
    }

    @Override
    public void delete(Object id) {
        OwnerBase ownerBase = getById(id);
        if (ownerBase == null) {
            log.info("Deleting error. account not find");
        }

        entityManager.remove(ownerBase);
    }

    @Override
    public OwnerBase getById(Object id) {
        if (id instanceof PassportKey) {
            return entityManager.find(OwnerBase.class, id);
        }
        return null;
    }
}
