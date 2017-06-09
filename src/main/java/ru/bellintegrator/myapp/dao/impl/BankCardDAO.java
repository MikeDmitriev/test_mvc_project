package ru.bellintegrator.myapp.dao.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.myapp.model.BankCard;
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
public class BankCardDAO implements BankDAO<BankCard> {

    private final Logger log = LoggerFactory.getLogger(BankCardDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void create(BankCard bankCard) {
        try{
            entityManager.persist(bankCard);
        }catch (Exception e){
            log.info("Creation error. Can not persist BankCardBase\nException: " + e.toString());
        }
    }

    @Transactional
    @Override
    public void insert(BankCard bankCard) {
        try{
            entityManager.persist(bankCard);
        }catch (Exception e){
            log.info("Insertion error. Can not persist BankCardBase\nException: " + e.toString());
        }
    }

    @Override
    public BankCard update(BankCard bankCard) throws Exception{
            return entityManager.merge(bankCard);
    }

    @Override
    public List<BankCard> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<BankCard> query = criteriaBuilder.createQuery(BankCard.class);
        Root<BankCard> root = query.from(BankCard.class);
        query.select(root);
        TypedQuery<BankCard> allCards = entityManager.createQuery(query);
        return allCards.getResultList();
    }

    @Override
    public void delete(Object id) {
        BankCard bankCard = getById(id);
        if (bankCard != null) {
            entityManager.remove(bankCard);
        }else{
            log.info("Deleting error. Card not find");
        }
    }

    @Override
    public BankCard getById(Object id) {
        if (id instanceof Long) {
            return entityManager.find(BankCard.class, id);
        }
        return null;
    }

}
