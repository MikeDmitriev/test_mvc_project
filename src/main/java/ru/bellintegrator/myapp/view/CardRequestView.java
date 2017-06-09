package ru.bellintegrator.myapp.view;

import ru.bellintegrator.myapp.model.CardRequest;

/**
 * Created by MADmitriev on 01.06.2017.
 */
public class CardRequestView {

    public long requestId;

    public String status;

    public OwnerView owner;

    public BankCardView bankCard;

    public CardRequestView(){

    }

    public CardRequestView(long requestId, String status, OwnerView owner, BankCardView bankCard){
        this.requestId = requestId;
        this.status = status;
        this.owner = owner;
        this.bankCard = bankCard;
    }

    public CardRequestView(OwnerView ownerView, BankCardView bankCardView, CardRequest cardRequest){
        owner = ownerView;
        bankCard = bankCardView;
        requestId = cardRequest.getRequestId();
        status = cardRequest.getStatus();
    }

    @Override
    public String toString(){
        return "{requestId:" + requestId + ";card:" + bankCard + ";owner:" + owner + ";status:" + status + "}";
    }
}
