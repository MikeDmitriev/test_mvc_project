package ru.bellintegrator.myapp.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.myapp.model.CardRequest;
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
public class CardRequestDAO implements BankDAO<CardRequest> {

    private final Logger log = LoggerFactory.getLogger(CardRequestDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void create(CardRequest cardRequest) {
        try{
            entityManager.persist(cardRequest);
        }catch (Exception e){
            log.info("Creation error. Can not persist cardRequest\nException: " + e.toString());
        }
    }

    @Transactional
    @Override
    public void insert(CardRequest cardRequest) {
        try{
            entityManager.persist(cardRequest);
        }catch (Exception e){
            log.info("Insertion error. Can not persist cardRequest\nException: " + e.toString());
        }
    }

    @Transactional
    @Override
    public CardRequest update(CardRequest cardRequest) {
        try{
            return entityManager.merge(cardRequest);
        }catch (Exception e) {
            log.info("Update error. Can not merge cardRequest\nException: " + e.toString());
            return null;
        }
    }

    @Override
    public List<CardRequest> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CardRequest> query = criteriaBuilder.createQuery(CardRequest.class);
        Root<CardRequest> root = query.from(CardRequest.class);
        query.select(root);
        TypedQuery<CardRequest> allRequests = entityManager.createQuery(query);
        return allRequests.getResultList();
    }

    @Override
    public void delete(Object id) {
        CardRequest cardRequest = getById(id);
        if (cardRequest != null) {
            entityManager.remove(cardRequest);
        }else{
            log.info("Deleting error. Card not find");
        }
    }

    @Override
    public CardRequest getById(Object id) {
        if (id instanceof Long){
            return entityManager.find(CardRequest.class, id);
        }
        return null;
    }
}
