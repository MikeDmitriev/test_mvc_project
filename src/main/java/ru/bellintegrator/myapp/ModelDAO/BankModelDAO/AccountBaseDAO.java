package ru.bellintegrator.myapp.ModelDAO.BankModelDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.bellintegrator.myapp.Model.AccountBase;
import ru.bellintegrator.myapp.ModelDAO.BankDAO;

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
public class AccountBaseDAO implements BankDAO<AccountBase>{
    private final Logger log = LoggerFactory.getLogger(AccountBaseDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public void create(AccountBase accountBase) {
        try {
            entityManager.persist(accountBase);
        }catch (Exception e){
            log.info("Creation error. Can not persist accountBase\nException: " + e.toString());
        }
    }

    @Transactional
    @Override
    public void insert(AccountBase accountBase){
        try {
            entityManager.persist(accountBase);
        }catch (Exception e){
            log.info("Insertion error. Can not persist accountBase\nException: " + e.toString());
        }
    }

    @Override
    public AccountBase update(AccountBase accountBase) throws Exception{
            return entityManager.merge(accountBase);
    }

    @Override
    public List<AccountBase> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<AccountBase> query = criteriaBuilder.createQuery(AccountBase.class);
        Root<AccountBase> root = query.from(AccountBase.class);
        query.select(root);
        TypedQuery<AccountBase> allAccounts = entityManager.createQuery(query);
        return allAccounts.getResultList();
    }

    @Override
    public void delete(Object id) {
        AccountBase accountBase = getById(id);
        if (accountBase != null) {
            entityManager.remove(accountBase);
        }else{
            log.info("Deleting error. account not find");
        }
    }

    @Override
    public AccountBase getById(Object id) {
        if (id instanceof Long){
            return entityManager.find(AccountBase.class, id);
        }
        return null;
    }

}
