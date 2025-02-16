package br.com.susqfree.patient_management.domain.model;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Builder
public class Patient {

    private UUID id;
    private String name;
    private String gender;
    private LocalDate birthDate;
    private String cpf;
    private String susNumber;
    private String phoneNumber;
    private String mail;
    private Address address;

    public void updateName(String newName) {
        if (!this.name.equals(newName)) {
            this.name = newName;
        }
    }

    public void updateGender(String newGender) {
        if (!this.gender.equals(newGender)) {
            this.gender = newGender;
        }
    }

    public void updatePhoneNumber(String newPhoneNumber) {
        if (!this.phoneNumber.equals(newPhoneNumber)) {
            this.phoneNumber = newPhoneNumber;
        }
    }

    public void updateMail(String newMail) {
        if (!this.mail.equals(newMail)) {
            this.mail = newMail;
        }
    }

    public void updateAddress(Address newAddress) {
        if (!this.address.equals(newAddress)) {
            this.address = newAddress;
        }
    }

}
