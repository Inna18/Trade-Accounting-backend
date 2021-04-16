package com.trade_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LegalDetailDto {

    private Long id;

    private String lastName;

    private String firstName;

    private String middleName;

    private String address;

    private String commentToAddress;

    private String inn;

    private String kpp;

    private String okpo;

    private String ogrn;

    private String numberOfTheCertificate;

    private String dateOfTheCertificate;

    private TypeOfContractorDto typeOfContractorDto;

    public LegalDetailDto(Long id,
                          String lastName,
                          String firstName,
                          String middleName,
                          String address,
                          String commentToAddress,
                          String inn,
                          String kpp,
                          String okpo,
                          String ogrn,
                          String numberOfTheCertificate,
                          LocalDate dateOfTheCertificate,
                          Long typeOfContractorId) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.address = address;
        this.commentToAddress = commentToAddress;
        this.inn = inn;
        this.kpp = kpp;
        this.okpo = okpo;
        this.ogrn = ogrn;
        this.numberOfTheCertificate = numberOfTheCertificate;
        this.dateOfTheCertificate = dateOfTheCertificate.toString();
        this.typeOfContractorDto = new TypeOfContractorDto();
        this.typeOfContractorDto.setId(typeOfContractorId);
    }
}
