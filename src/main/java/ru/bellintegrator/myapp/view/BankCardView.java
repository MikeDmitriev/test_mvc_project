package ru.bellintegrator.myapp.view;

import ru.bellintegrator.myapp.model.BankCard;

/**
 * Created by MADmitriev on 01.06.2017.
 */
public class BankCardView {
    public long cardNum;

    public String name;

    public String paySystem;

    public byte type;

    public int validTime;

    public short sign;

    public long account;

    public boolean activate;

    public BankCardView(){

    }

    public BankCardView(BankCard bankCard){
        cardNum = bankCard.getCardNum();
        name = bankCard.getName();
        paySystem = bankCard.getPaySystem();
        type = bankCard.getType();
        validTime = bankCard.getValidTime();
        sign = bankCard.getSign();
        account = bankCard.getAccount().getAccount();
        activate = bankCard.isActivate();
    }

    @Override
    public String toString(){
        return "{cardNum:" + cardNum + ";name:" + name + ";paySystem:" + paySystem + ";type:" + type
                + ";validTime:" + validTime + ";sign:" + sign + ";account:" + account + "}";
    }
}
