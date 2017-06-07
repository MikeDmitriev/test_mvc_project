package ru.bellintegrator.myapp.Model;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * Created by MADmitriev on 02.06.2017.
 */
@Entity
@Table
public class OwnerId {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long ownerId;
    @OneToOne(targetEntity = OwnerBase.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private OwnerBase owner;
    @OneToMany(mappedBy="account")
    private List<AccountBase> accounts;
    @OneToMany(mappedBy = "requestId")
    private Set<CardRequest> cardRequests;

    public OwnerId(){

    }

    public OwnerId(OwnerBase owner){
        this.owner = owner;
    }

    public long getOwnerId() {
        return ownerId;
    }

    public OwnerBase getOwner() {
        return owner;
    }

    public void setOwner(OwnerBase owner) {
        this.owner = owner;
    }

    public Set<CardRequest> getCardRequests() {
        return cardRequests;
    }

    public void setCardRequests(Set<CardRequest> cardRequests) {
        this.cardRequests = cardRequests;
    }

    public List<AccountBase> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountBase> accounts) {
        this.accounts = accounts;
    }
}
