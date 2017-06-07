package ru.bellintegrator.myapp.view;

import ru.bellintegrator.myapp.Model.AccountBase;
import ru.bellintegrator.myapp.Model.OwnerBase;
import ru.bellintegrator.myapp.Model.OwnerId;

/**
 * Created by MADmitriev on 01.06.2017.
 */
public class AccountView {
    public long account;

    public OwnerIdView ownerId;

    public AccountView(){

    }

    public AccountView(long account, OwnerIdView ownerId){
        this.account = account;
        this.ownerId = ownerId;
    }

    public AccountView(AccountBase accountBase){
        account = accountBase.getAccount();
        this.ownerId = new OwnerIdView(accountBase.getOwnerId());
    }

    @Override
    public String toString(){
        return "{ account:" + account + ";ownerId:" + ownerId + "}";
    }
}
