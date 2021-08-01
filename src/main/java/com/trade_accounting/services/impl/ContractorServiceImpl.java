package com.trade_accounting.services.impl;

import com.trade_accounting.models.Address;
import com.trade_accounting.models.BankAccount;
import com.trade_accounting.models.Contact;
import com.trade_accounting.models.Contractor;
import com.trade_accounting.models.dto.ContractorDto;
import com.trade_accounting.repositories.AccessParametersRepository;
import com.trade_accounting.repositories.AddressRepository;
import com.trade_accounting.repositories.BankAccountRepository;
import com.trade_accounting.repositories.ContactRepository;
import com.trade_accounting.repositories.ContractorGroupRepository;
import com.trade_accounting.repositories.ContractorRepository;
import com.trade_accounting.repositories.DepartmentRepository;
import com.trade_accounting.repositories.EmployeeRepository;
import com.trade_accounting.repositories.LegalDetailRepository;
import com.trade_accounting.repositories.TypeOfPriceRepository;
import com.trade_accounting.services.interfaces.ContractorService;
import com.trade_accounting.utils.DtoMapper;
import com.trade_accounting.utils.mapper.AddressMapper;
import com.trade_accounting.utils.mapper.BankAccountMapper;
import com.trade_accounting.utils.mapper.LegalDetailMapper;
import com.trade_accounting.utils.mapper.TypeOfPriceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ContractorServiceImpl implements ContractorService {

    private final ContractorRepository contractorRepository;
    private final ContractorGroupRepository contractorGroupRepository;
    private final TypeOfPriceRepository typeOfPriceRepository;
    private final LegalDetailRepository legalDetailRepository;
    private final AddressRepository addressRepository;
    private final ContactRepository contactRepository;
    private final AccessParametersRepository accessParametersRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final BankAccountRepository bankAccountRepository;
    private final DtoMapper dtoMapper;
    private final BankAccountMapper bankAccountMapper;
    private final AddressMapper addressMapper;
    private final LegalDetailMapper legalDetailMapper;
    private final TypeOfPriceMapper typeOfPriceMapper;

    @Override
    public List<ContractorDto> search(Specification<Contractor> specification) {
        return executeSearch(contractorRepository, dtoMapper::contractorToContractorDto, specification);
    }

    @Override
    public List<ContractorDto> getAll() {
        List<Contractor> list = contractorRepository.findAll();
        return list.stream()
                .map(dtoMapper::contractorToContractorDto)
                .collect(Collectors.toList());

    }

    @Override
    public List<ContractorDto> getAll(String searchTerm) {
        if ("null".equals(searchTerm) || searchTerm.isEmpty()) {
            List<Contractor> all = contractorRepository.findAll();
            return all.stream().map(dtoMapper::contractorToContractorDto).collect(Collectors.toList());
        } else {
            List<Contractor> list = contractorRepository.search(searchTerm);
            return list.stream().map(dtoMapper::contractorToContractorDto).collect(Collectors.toList());
        }
    }

    @Override
    public ContractorDto getById(Long id) {
        return dtoMapper.contractorToContractorDto(
                contractorRepository.findById(id).orElse(new Contractor())
        );
    }

    @Override
    public ContractorDto create(ContractorDto contractorDto) {
        Contractor contractor = dtoMapper.contractorDtoToContractor(contractorDto);

        Address address = addressMapper.toModel(contractorDto.getAddressDto());
        contractor.setAddress(addressRepository.save(address));

        List<Contact> contactList = dtoMapper.contactDtoListToContactList(contractorDto.getContactDto());
        contractor.setContact(contactRepository.saveAll(contactList));

        List<BankAccount> bankAccountList = bankAccountMapper.bankAccountDtoListToBankAccountList(contractorDto.getBankAccountDto());
        contractor.setBankAccounts(bankAccountRepository.saveAll(bankAccountList));

        contractor.setContractorGroup(
                contractorGroupRepository
                        .save(dtoMapper.contractorGroupDtoToContractorGroup(
                                contractorDto.getContractorGroupDto()
                        ))
        );

        contractor.setAccessParameters(
                accessParametersRepository.save(dtoMapper.accessParametersDtoToAccessParameters
                        (contractorDto.getAccessParametersDto()))
        );

        contractor.setTypeOfPrice(
                typeOfPriceRepository.save(typeOfPriceMapper.toModel(
                        contractorDto.getTypeOfPriceDto())
                )
        );

        contractor.setLegalDetail(
                legalDetailRepository.save(
                        legalDetailMapper.toModel(
                                contractorDto.getLegalDetailDto()
                        )
                )
        );

        return dtoMapper.contractorToContractorDto(contractorRepository.save(contractor));
    }


    @Override
    public ContractorDto update(ContractorDto contractorDto) {
        Contractor contractor = dtoMapper.contractorDtoToContractor(contractorDto);

        Address address = addressMapper.toModel(contractorDto.getAddressDto());
        addressRepository.save(address);
        contractor.setAddress(address);

        List<Contact> contactList = dtoMapper.contactDtoListToContactList(contractorDto.getContactDto());
        contactList.forEach(contactRepository::save);
        contractor.setContact(contactList);

        contractor.setContractorGroup(
                contractorGroupRepository.findById(contractorDto.getContractorGroupDto().getId()).orElse(null));

        contractor.setTypeOfPrice(
                typeOfPriceRepository.findById(contractorDto.getTypeOfPriceDto().getId()).orElse(null));

        contractor.setLegalDetail(
                legalDetailRepository.findById(contractorDto.getLegalDetailDto().getId()).orElse(null));

        contractorRepository.save(contractor);

        return contractorDto;
    }

    @Override
    public void deleteById(Long id) {
        contractorRepository.deleteById(id);
    }
}
