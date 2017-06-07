package ru.bellintegrator.myapp.DatabaseService;

import ru.bellintegrator.myapp.view.*;

import java.util.List;

/**
 * Created by MADmitriev on 23.05.2017.
 */
public interface DBService {

    void createNewCardRequest(OwnerView owner, BankCardView card);
    List<CardRequestView> getAllRequests();
    CardRequestView getCardRequest(long requestId);
    List<CardRequestView> getAllRequestByOwner(short passportSerial, int pasportNum);
    List<AccountView> getAllAccountsByOwner(int passportSerial, int pasportNum);
    List<BankCardView> getAllCardsByOwner(int passportSerial, int pasportNum);
    BankCardView getCardById(long id);
    boolean signRequest(long requestId);
    boolean sendRequest(long requestId);
    boolean readyRequest(long requestId);
    boolean activateCard (long cardId);
    List<AccountView> getAllAccounts();
    List<OwnerView> getAllOwners();
    List<OwnerIdView> getAllOwnersId();
}
