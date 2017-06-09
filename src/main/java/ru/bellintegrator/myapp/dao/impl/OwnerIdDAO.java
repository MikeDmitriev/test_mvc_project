package ru.bellintegrator.myapp.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.myapp.model.OwnerId;
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
 * Created by MADmitriev on 02.06.2017.
 */
@Repository
public class OwnerIdDAO implements BankDAO<OwnerId>{

    private final Logger log = LoggerFactory.getLogger(AccountBaseDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void create(OwnerId ownerId) {
        try{
            entityManager.persist(ownerId);
        }
        catch (Exception e){
            log.info("Creation error. Can not persist ownerId\nException: " + e.toString());
        }
    }

    @Transactional
    @Override
    public void insert(OwnerId ownerId) {
        try{
            entityManager.persist(ownerId);
        }
        catch (Exception e){
            log.info("Insertion error. Can not persist ownerId\nException: " + e.toString());
        }
    }

    @Transactional
    @Override
    public OwnerId update(OwnerId ownerId) {
        try{
            return entityManager.merge(ownerId);
        }catch (Exception e) {
            log.info("Update error. Can not merge ownerId\nException: " + e.toString());
            return null;
        }
    }

    @Override
    public List<OwnerId> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<OwnerId> query = criteriaBuilder.createQuery(OwnerId.class);
        Root<OwnerId> root = query.from(OwnerId.class);
        query.select(root);
        TypedQuery<OwnerId> allOwners = entityManager.createQuery(query);
        return allOwners.getResultList();
    }

    @Override
    public void delete(Object id) {
        OwnerId ownerId = getById(id);
        if (ownerId != null) {
            entityManager.remove(ownerId);
        }else{
            log.info("Deleting error. account not find");
        }
    }

    @Override
    public OwnerId getById(Object id) {
        if (id instanceof Long){
            return entityManager.find(OwnerId.class, id);
        }
        return null;
    }
}
