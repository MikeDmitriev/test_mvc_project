package ru.bellintegrator.myapp.Model;

import ru.bellintegrator.myapp.view.BankCardView;

import javax.persistence.*;

/**
 * Created by MADmitriev on 29.05.2017.
 */
@Entity
@Table
public class BankCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long cardNum;
    @Column
    private String name;
    @Column
    private String paySystem;
    @Column
    private byte type;
    @Column
    private int validTime;
    @Column
    private short sign;
    @Column
    private boolean activate;
    @ManyToOne(targetEntity = AccountBase.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "account")
    private AccountBase account;
    @OneToOne(targetEntity = CardRequest.class, cascade = CascadeType.ALL)
    @JoinColumn
    private CardRequest cardRequest;

    public  BankCard(){
    }

    public BankCard(BankCardView bankCard){
        //cardNum = bankCard.cardNum;
        name = bankCard.name;
        paySystem = bankCard.paySystem;
        type = bankCard.type;
        validTime = bankCard.validTime;
        sign = bankCard.sign;
        activate = false;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public AccountBase getAccount() {
        return account;
    }

    public void setAccount(AccountBase account) {
        this.account = account;
    }

    public String getPaySystem() {
        return paySystem;
    }

    public void setPaySystem(String paySystem) {
        this.paySystem = paySystem;
    }

    public int getValidTime() {
        return validTime;
    }

    public void setValidTime(int validTime) {
        this.validTime = validTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getSign() {
        return sign;
    }

    public void setSign(short sign) {
        this.sign = sign;
    }

    public long getCardNum() {
        return cardNum;
    }

    public boolean isActivate() {
        return activate;
    }

    public void setActivate(boolean activate) {
        this.activate = activate;
    }

    public CardRequest getCardRequest() {
        return cardRequest;
    }

    public void setCardRequest(CardRequest cardRequest) {
        this.cardRequest = cardRequest;
    }
}
