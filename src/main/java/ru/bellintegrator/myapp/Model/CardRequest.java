package ru.bellintegrator.myapp.Model;

import javax.persistence.*;

/**
 * Created by MADmitriev on 29.05.2017.
 */
@Entity
@Table
public class CardRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long requestId;

    @Column
    private String status;

    @OneToOne(targetEntity = BankCard.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "cardRequest")
    private BankCard card;

    @ManyToOne(targetEntity = OwnerId.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "ownerId")
    private OwnerId ownerId;

    public BankCard getCardId() {
        return card;
    }

    public void setCardId(BankCard card) {
        this.card = card;
    }

    public OwnerId getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(OwnerId ownerId) {
        this.ownerId = ownerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getRequestId() {
        return requestId;
    }
}
