package ru.bellintegrator.myapp.view;

import ru.bellintegrator.myapp.Model.OwnerBase;

/**
 * Created by MADmitriev on 01.06.2017.
 */
public class OwnerView {
    public String name;

    public String surname;

    public String patronymic;

    public int passportSerial;

    public int passportNum;

    public String issue;

    public String nationality;

    public OwnerView(){

    }

    public OwnerView(OwnerBase owner){
        name = owner.getName();
        surname = owner.getSurname();
        patronymic = owner.getPatronymic();
        passportSerial = owner.getPassportKey().getPassportSerial();
        passportNum = owner.getPassportKey().getPassportNum();
        issue = owner.getIssue();
        nationality = owner.getNationality();
    }

    @Override
    public String toString(){
        return "{name:" + name + ";surname:" + surname + ";patronymic:" + patronymic + ";passportSerial:" + passportSerial + ";passportNum:"
                + passportNum + ";issue:" + issue + "nationality" + nationality + "}";
    }
}
