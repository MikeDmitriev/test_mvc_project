package ru.bellintegrator.myapp.model;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created by MADmitriev on 02.06.2017.
 */
@Embeddable
public class PassportKey implements Serializable {
    private int passportSerial;
    private int passportNum;

    public int getPassportSerial() {
        return passportSerial;
    }

    public void setPassportSerial(int passportSerial) {
        this.passportSerial = passportSerial;
    }

    public int getPassportNum() {
        return passportNum;
    }

    public void setPassportNum(int passportNum) {
        this.passportNum = passportNum;
    }

    public PassportKey(){

    }

    public PassportKey(int passportSerial, int passportNum){
        this.passportSerial = passportSerial;
        this.passportNum = passportNum;
    }
}
