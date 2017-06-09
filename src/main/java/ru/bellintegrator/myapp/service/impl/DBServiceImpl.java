package ru.bellintegrator.myapp.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bellintegrator.myapp.model.*;
import ru.bellintegrator.myapp.dao.BankDAO;
import ru.bellintegrator.myapp.dao.impl.*;
import ru.bellintegrator.myapp.service.DBService;
import ru.bellintegrator.myapp.view.*;

import java.util.*;

/**
 * Created by MADmitriev on 23.05.2017.
 */
@Service
@Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
public class DBServiceImpl implements DBService {
    private final Logger log = LoggerFactory.getLogger(DBServiceImpl.class);

    private final BankDAO<AccountBase> accountBaseDAO;
    private final BankDAO<BankCard> bankCardDAO;
    private final BankDAO<CardRequest> cardRequestDAO;
    private final BankDAO<OwnerBase> ownerBaseDAO;
    private final BankDAO<OwnerId> ownerIdDAO;

    @Autowired
    public DBServiceImpl(AccountBaseDAO accountBaseDAO,
                         BankCardDAO bankCardDAO,
                         CardRequestDAO cardRequestDAO,
                         OwnerBaseDAO ownerBaseDAO,
                         OwnerIdDAO ownerIdDAO) {

        this.accountBaseDAO = accountBaseDAO;
        this.bankCardDAO = bankCardDAO;
        this.cardRequestDAO = cardRequestDAO;
        this.ownerBaseDAO = ownerBaseDAO;
        this.ownerIdDAO = ownerIdDAO;
    }

//    @PostConstruct
//    private void init() {
//        PassportKey passportKey = new PassportKey((short)0, 0);
//        AccountBase accountBase = new AccountBase();
//        BankCard bankCard = new BankCard();
//        CardRequest cardRequest = new CardRequest();
//        OwnerBase ownerBase = new OwnerBase();
//        OwnerId ownerId = new OwnerId();
//
//        ownerBase.setPassportKey(passportKey);
//        ownerId.setOwner(ownerBase);
//        accountBase.setOwnerId(ownerId);
//        bankCard.setAccount(accountBase);
//        cardRequest.setCardId(bankCard);
//        cardRequest.setOwnerId(ownerId);
//        cardRequest.setStatus("null");
//
//        ownerIdDAO.create(ownerId);
//        ownerBaseDAO.create(ownerBase);
//        accountBaseDAO.create(accountBase);
//        bankCardDAO.create(bankCard);
//        cardRequestDAO.create(cardRequest);
//    }

    private void addNewCardRequest(BankCard card, OwnerId ownerId) {
        bankCardDAO.insert(card);
        CardRequest request = new CardRequest();
        request.setCardId(card);
        request.setOwnerId(ownerId);
        request.setStatus("CREATED");
        cardRequestDAO.insert(request);
    }

    private void addNewCardRequestNewOwner(OwnerBase owner, BankCard card) {
        OwnerId ownerId = new OwnerId(owner);
        owner.setOwnerId(ownerId);
        ownerIdDAO.insert(ownerId);
        ownerBaseDAO.insert(owner);
        AccountBase account = new AccountBase();
        account.setOwnerId(ownerId);
        accountBaseDAO.insert(account);
        card.setAccount(account);
        addNewCardRequest(card, ownerId);
    }

    @Transactional
    private boolean changeCardRequestStatus(long requestId, String oldStatus, String newStatus) {
        CardRequest request = cardRequestDAO.getById(requestId);
        if (!oldStatus.equals(request.getStatus())) {
            return false;
        }

        request.setStatus(newStatus);
        try {
            cardRequestDAO.update(request);
            return true;
        } catch (Exception e) {
            log.info("Error signing request\nException:", e);
            return false;
        }
    }

    @Override
    public void createNewCardRequest(OwnerView ownerView, BankCardView cardView) {
        if (ownerView == null || cardView == null) {
            throw new IllegalArgumentException("ownerView or cardView is null");
        }

        OwnerBase owner = new OwnerBase(ownerView);
        BankCard card = new BankCard(cardView);

        OwnerBase baseOwner = ownerBaseDAO.getById(owner.getPassportKey());
        if (baseOwner == null) {

            addNewCardRequestNewOwner(owner, card);
            return;
        }
        List<AccountBase> ownersAccounts = baseOwner.getOwnerId().getAccounts();
        OwnerId ownerId = baseOwner.getOwnerId();
        for (AccountBase account : ownersAccounts) {
            if (account.getAccount() == cardView.account) {

                addNewCardRequest(card, ownerId);
                return;
            }
        }
        AccountBase account = new AccountBase();
        accountBaseDAO.insert(account);
        account.setOwnerId(ownerId);

        addNewCardRequest(card, ownerId);
    }

    @Override
    public List<CardRequestView> getAllRequests() {
        List<CardRequestView> result = new ArrayList<>();
        List<CardRequest> allCardRequests = cardRequestDAO.getAll();
        for (CardRequest cardRequest : allCardRequests) {
            BankCard card = cardRequest.getCardId();
            OwnerId ownerId = cardRequest.getOwnerId();
            OwnerBase owner = ownerId.getOwner();
            result.add(new CardRequestView(new OwnerView(owner), new BankCardView(card), cardRequest));
        }
        return result;
    }

    @Override
    public CardRequestView getCardRequest(long requestId) {
        if (requestId == 0) {
            throw new IllegalArgumentException("requestId is null");
        }
        CardRequest request = cardRequestDAO.getById(requestId);
        BankCard card = request.getCardId();
        OwnerId ownerId = request.getOwnerId();
        OwnerBase owner = ownerId.getOwner();
        return new CardRequestView(new OwnerView(owner), new BankCardView(card), request);
    }

    @Override
    public List<CardRequestView> getAllRequestByOwner(short passportSerial, int passportNum) {
        if (passportSerial == 0 || passportNum == 0) {
            throw new IllegalArgumentException("passportSerial or passportNum is null");
        }
        PassportKey passportKey = new PassportKey(passportSerial, passportNum);
        OwnerBase owner = ownerBaseDAO.getById(passportKey);
        OwnerId ownerId = owner.getOwnerId();

        List<CardRequestView> result = new ArrayList<>();
        for (CardRequest request : ownerId.getCardRequests()) {
            BankCard card = request.getCardId();
            result.add(new CardRequestView(new OwnerView(owner), new BankCardView(card), request));
        }
        return result;
    }

    @Override
    public List<AccountView> getAllAccountsByOwner(int passportSerial, int passportNum) {
        if (passportSerial == 0 || passportNum == 0) {
            throw new IllegalArgumentException("passportSerial or passportNum is null");
        }
        PassportKey passportKey = new PassportKey(passportSerial, passportNum);
        OwnerBase owner = ownerBaseDAO.getById(passportKey);
        OwnerId ownerId = owner.getOwnerId();

        List<AccountView> result = new ArrayList<>();
        for (AccountBase account : ownerId.getAccounts()) {
            result.add(new AccountView(account));
        }
        return result;
    }

    @Override
    public List<BankCardView> getAllCardsByOwner(int passportSerial, int passportNum) {
        if (passportSerial == 0 || passportNum == 0) {
            throw new IllegalArgumentException("passportSerial or passportNum is null");
        }
        PassportKey passportKey = new PassportKey(passportSerial, passportNum);
        OwnerBase owner = ownerBaseDAO.getById(passportKey);
        OwnerId ownerId = owner.getOwnerId();

        List<AccountBase> accounts = ownerId.getAccounts();
        List<BankCardView> result = new ArrayList<>();
        for (AccountBase account : accounts) {
            List<BankCard> cards = account.getBankCards();
            for (BankCard card : cards) {
                result.add(new BankCardView(card));
            }
        }
        return result;
    }

    @Override
    public BankCardView getCardById(long id) {
        if (id == 0) {
            throw new IllegalArgumentException("id is null");
        }

        return new BankCardView(bankCardDAO.getById(id));
    }

    @Override
    public boolean signRequest(long requestId) {
        if (requestId == 0) {
            throw new IllegalArgumentException("requestId is null");
        }

        return changeCardRequestStatus(requestId, "CREATED", "SIGNED");

    }

    @Override
    public boolean sendRequest(long requestId) {
        if (requestId == 0) {
            throw new IllegalArgumentException("requestId is null");
        }

        return changeCardRequestStatus(requestId, "SIGNED", "SENT");
    }

    @Override
    public boolean readyRequest(long requestId) {
        if (requestId == 0) {
            throw new IllegalArgumentException("requestId is null");
        }

        return changeCardRequestStatus(requestId, "SENT", "READY");
    }

    @Transactional
    @Override
    public boolean activateCard(long cardId) {
        if (cardId == 0) {
            throw new IllegalArgumentException("cardId is null");
        }

        BankCard card = bankCardDAO.getById(cardId);
        if (card == null) {
            return false;
        }
        if (!card.isActivate()) {
            card.setActivate(true);
        }

        try {
            bankCardDAO.update(card);
            return true;
        } catch (Exception e) {
            log.info("Error signing request\nExeption:", e);
            return false;
        }
    }

    @Override
    public List<AccountView> getAllAccounts() {
        List<AccountView> allAccounts = new ArrayList<>();
        List<AccountBase> allBaseAccounts = accountBaseDAO.getAll();
        for (AccountBase accountBase : allBaseAccounts) {
            AccountView accountView = new AccountView(accountBase);
            allAccounts.add(accountView);

        }
        return allAccounts;
    }

    @Override
    public List<OwnerView> getAllOwners() {
        List<OwnerView> allOwners = new ArrayList<>();
        List<OwnerBase> allBaseOwners = ownerBaseDAO.getAll();
        for (OwnerBase ownerBase : allBaseOwners) {
            if (ownerBase != null) {
                allOwners.add(new OwnerView(ownerBase));
            }
        }
        return allOwners;
    }

    @Override
    public List<OwnerIdView> getAllOwnersId() {
        List<OwnerIdView> allOwners = new ArrayList<>();
        List<OwnerId> allOwnersId = ownerIdDAO.getAll();
        for (OwnerId ownersId : allOwnersId) {
            if (ownersId != null) {
                allOwners.add(new OwnerIdView(ownersId));
            }
        }
        return allOwners;
    }
}
