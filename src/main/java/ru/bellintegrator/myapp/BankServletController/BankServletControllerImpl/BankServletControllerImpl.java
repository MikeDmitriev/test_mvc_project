package ru.bellintegrator.myapp.BankServletController.BankServletControllerImpl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bellintegrator.myapp.BankServletController.BankServletController;
import ru.bellintegrator.myapp.DatabaseService.DBService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bellintegrator.myapp.view.*;

import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Created by MADmitriev on 22.05.2017.
 */
@RestController()
public class BankServletControllerImpl implements BankServletController {
    Logger log = LoggerFactory.getLogger(BankServletControllerImpl.class);

    private final DBService dbService;

    @Autowired
    public BankServletControllerImpl(DBService dbService){
        this.dbService = dbService;
    }

    @Override
    @RequestMapping(value = "/new_request", consumes = APPLICATION_JSON_VALUE, method = POST)
    public String createCardRequest(@RequestBody CardRequestView cardRequestView) {
        dbService.createNewCardRequest(cardRequestView.owner, cardRequestView.bankCard);
        return "ok";
    }

    @Override
    @RequestMapping(value = "/all_request", produces = APPLICATION_JSON_VALUE, method = GET)
    public List<CardRequestView> viewAllCardRequest() {
        return dbService.getAllRequests();
    }

    @Override
    @RequestMapping(value = "/request", produces = APPLICATION_JSON_VALUE, method = GET)
    public CardRequestView viewCardRequest(@RequestParam long requestId) {
        return dbService.getCardRequest(requestId);
    }

    @Override
    @RequestMapping(value = "/owner_request", produces = APPLICATION_JSON_VALUE, method = GET)
    public List<CardRequestView> viewAllOwnersCardRequests(@RequestParam short passportSerial, @RequestParam int passportNum) {
        return dbService.getAllRequestByOwner(passportSerial, passportNum);
    }

    @Override
    @RequestMapping(value = "/owner_account", produces = APPLICATION_JSON_VALUE, method = GET)
    public List<AccountView> viewOwnersAccounts(@RequestParam short passportSerial, @RequestParam int passportNum) {
        return dbService.getAllAccountsByOwner(passportSerial, passportNum);
    }

    @Override
    @RequestMapping(value = "/owner_card", produces = APPLICATION_JSON_VALUE, method = GET)
    public List<BankCardView> viewOwnersCards(@RequestParam short passportSerial, @RequestParam int passportNum) {
        return dbService.getAllCardsByOwner(passportSerial, passportNum);
    }

    @Override
    @RequestMapping(value = "/card", produces = APPLICATION_JSON_VALUE, method = GET)
    public BankCardView viewCard(@RequestParam long cardId) {
        return dbService.getCardById(cardId);
    }

    @Override
    @RequestMapping(value = "/sign_request", method = GET)
    public boolean signCardRequest(@RequestParam long requestId) {
        return dbService.signRequest(requestId);
    }

    @Override
    @RequestMapping(value = "/send_request", method = GET)
    public boolean sendCardRequest(@RequestParam long requestId) {
        return dbService.sendRequest(requestId);
    }

    @Override
    @RequestMapping(value = "/ready_request", method = GET)
    public boolean readyCardRequest(@RequestParam long requestId) {
        return dbService.readyRequest(requestId);
    }

    @Override
    @RequestMapping(value = "/activate", method = GET)
    public boolean activateCard(@RequestParam long cardId) {
        return dbService.activateCard(cardId);
    }

    @Override
    @RequestMapping(value = "/accounts", produces = APPLICATION_JSON_VALUE, method = GET)
    public List<AccountView> getAllAccounts(){
        return dbService.getAllAccounts();
    }

    @Override
    @RequestMapping(value = "/owners", produces = APPLICATION_JSON_VALUE, method = GET)
    public List<OwnerView> getAllOwners(){
        return dbService.getAllOwners();
    }

    @RequestMapping(value = "/owners_id", produces = APPLICATION_JSON_VALUE, method = GET)
    public List<OwnerIdView> getAllOwnersIds(){
        return dbService.getAllOwnersId();
    }
}
