package ru.bellintegrator.myapp.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.bellintegrator.myapp.Model.OwnerBase;
import ru.bellintegrator.myapp.Model.OwnerId;

/**
 * Created by MADmitriev on 05.06.2017.
 */
public class OwnerIdView {

    private long ownerId;

    private OwnerView owner;

    public OwnerIdView(){

    }

    public OwnerIdView(OwnerId ownerId){
        this.ownerId = ownerId.getOwnerId();
        this.owner = new OwnerView(ownerId.getOwner());
    }

    public long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(long ownerId) {
        this.ownerId = ownerId;
    }

    public OwnerView getOwner() {
        return owner;
    }

    public void setOwner(OwnerView ownerView) {
        this.owner = ownerView;
    }

    @Override
    public String toString() {
        return "{ownerId:" + ownerId + ";owner:" + owner + "}";
    }
}
