package ru.bellintegrator.myapp.model;

import ru.bellintegrator.myapp.view.OwnerView;

import javax.persistence.*;

/**
 * Created by MADmitriev on 29.05.2017.
 */
@Entity
@Table
public class OwnerBase {
    @EmbeddedId
    private PassportKey passportKey;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String patronymic;
    @Column
    private String issue;
    @Column
    private String nationality;
    @OneToOne(targetEntity = OwnerId.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private OwnerId ownerId;

    public OwnerBase(){
        this.passportKey = new PassportKey();
    }

    public OwnerBase(OwnerView owner){
        this.name = owner.name;
        this.surname = owner.surname;
        this.patronymic = owner.patronymic;
        this.passportKey = new PassportKey(owner.passportSerial, owner.passportNum);
        this.issue = owner.issue;
        this.nationality = owner.nationality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(String issue) {
        this.issue = issue;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public PassportKey getPassportKey() {
        return passportKey;
    }

    public void setPassportKey(PassportKey passportKey) {
        this.passportKey = passportKey;
    }

    public OwnerId getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(OwnerId ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public int hashCode(){
        byte[] bytes = (name.concat(surname.concat(patronymic.concat(issue.concat(nationality))))).getBytes();
        int result = passportKey.getPassportNum() ^ (passportKey.getPassportSerial() << 16);
        for (int i = 0; i < bytes.length; i++)
            result ^= bytes[i] << (i%4)*8;
        return result;
    }
    @Override
    public boolean equals(Object o){
        if (o instanceof OwnerBase){
            boolean result;
            result = (passportKey.getPassportNum() == ((OwnerBase) o).passportKey.getPassportNum()) &&
                    (passportKey.getPassportSerial() == ((OwnerBase) o).passportKey.getPassportSerial()) &&
                    (name.compareTo(((OwnerBase) o).name) == 0) &&
                    (surname.compareTo(((OwnerBase) o).surname) == 0) &&
                    (patronymic.compareTo(((OwnerBase) o).patronymic) == 0) &&
                    (issue.compareTo(((OwnerBase) o).issue) == 0) &&
                    (nationality.compareTo(((OwnerBase) o).nationality) == 0);
            return result;
        }else{
            return false;
        }
    }
}
