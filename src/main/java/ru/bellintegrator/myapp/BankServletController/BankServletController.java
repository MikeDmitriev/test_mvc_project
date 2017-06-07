package ru.bellintegrator.myapp.BankServletController;

import ru.bellintegrator.myapp.view.AccountView;
import ru.bellintegrator.myapp.view.BankCardView;
import ru.bellintegrator.myapp.view.CardRequestView;
import ru.bellintegrator.myapp.view.OwnerView;

import java.util.List;

/**
 * Created by MADmitriev on 22.05.2017.
 */
public interface BankServletController {

    String createCardRequest(CardRequestView cardRequestView);

    List<CardRequestView> viewAllCardRequest();

    CardRequestView viewCardRequest(long requestId);

    List<CardRequestView> viewAllOwnersCardRequests(short passportSerial, int passportNum);

    List<AccountView> viewOwnersAccounts(short passportSerial, int passportNum);

    List<BankCardView> viewOwnersCards(short passportSerial, int passportNum);

    BankCardView viewCard(long cardId);

    boolean signCardRequest(long requestId);

    boolean sendCardRequest(long requestId);

    boolean readyCardRequest(long requestId);

    boolean activateCard(long cardId);

    List<AccountView> getAllAccounts();

    List<OwnerView> getAllOwners();
}
