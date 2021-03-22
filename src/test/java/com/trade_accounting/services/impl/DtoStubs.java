package com.trade_accounting.services.impl;


import com.trade_accounting.models.dto.BankAccountDto;
import com.trade_accounting.models.dto.TypeOfPriceDto;
import com.trade_accounting.models.dto.AttributeOfCalculationObjectDto;
import com.trade_accounting.models.dto.CompanyDto;
import com.trade_accounting.models.dto.TaxSystemDto;
import com.trade_accounting.models.dto.TypeOfContractorDto;
import com.trade_accounting.utils.DtoMapper;
import org.mapstruct.factory.Mappers;

public class DtoStubs {
    private static final DtoMapper dtoMapper = Mappers.getMapper(DtoMapper.class);

    public static TypeOfPriceDto getTypeOfPriceDto(Long id) {
        return dtoMapper.typeOfPriceToTypeOfPriceDto(ModelStubs.getTypeOfPrice(id));
    }

    public static BankAccountDto getBankAccountDto(Long id) {
        return dtoMapper.bankAccountToBankAccountDto(
                ModelStubs.getBankAccount(id)
        );
    }

    public static CompanyDto getCompanyDto(Long id) {
        return dtoMapper.companyToCompanyDto(
                ModelStubs.getCompany(id)
        );
    }

    public static TypeOfContractorDto getTypeOfContractorDto(Long id){
        return dtoMapper.typeOfContractorToTypeOfContractorDto(
                ModelStubs.getTypeOfContractor(id)
        );
    }

    public static AttributeOfCalculationObjectDto getAttributeOfCalculationObjectDto(Long id) {
        return dtoMapper.attributeOfCalculationObjectToAttributeOfCalculationObjectDto(
                ModelStubs.getAttributeOfCalculationObject(id)
        );
    }

    public static TaxSystemDto getTaxSystemDto(Long id){
        return dtoMapper.taxSystemToTaxSystemDto(
                ModelStubs.getTaxSystem(id)
        );
    }
}
