package ru.bellintegrator.myapp.model;

import javax.persistence.*;
import java.util.List;

/**
 * Created by MADmitriev on 29.05.2017.
 */
@Entity
@Table
public class AccountBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long account;

    @ManyToOne(targetEntity = OwnerId.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "ownerId")
    private OwnerId owner;

    @OneToMany(mappedBy = "cardNum")
    private List<BankCard> bankCards;

    public long getAccount() {
        return account;
    }

    public OwnerId getOwnerId() {
        return owner;
    }

    public void setOwnerId(OwnerId owner) {
        this.owner = owner;
    }

    public List<BankCard> getBankCards() {
        return bankCards;
    }

    public void setBankCards(List<BankCard> bankCards) {
        this.bankCards = bankCards;
    }
}
