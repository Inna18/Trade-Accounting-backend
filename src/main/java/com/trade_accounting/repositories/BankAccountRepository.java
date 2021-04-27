package com.trade_accounting.repositories;

import com.trade_accounting.models.BankAccount;
import com.trade_accounting.models.TypeOfPrice;
import com.trade_accounting.models.dto.BankAccountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {


    @Query("select new com.trade_accounting.models.dto.BankAccountDto(" +
            "ba.id, " +
            "ba.rcbic, " +
            "ba.bank, " +
            "ba.address, " +
            "ba.correspondentAccount, " +
            "ba.account, " +
            "ba.mainAccount, " +
            "ba.sortNumber) from BankAccount ba")
    List<BankAccountDto> getAll();

    @Query("select new com.trade_accounting.models.dto.BankAccountDto(" +
            "ba.id, " +
            "ba.rcbic, " +
            "ba.bank, " +
            "ba.address, " +
            "ba.correspondentAccount, " +
            "ba.account, " +
            "ba.mainAccount, " +
            "ba.sortNumber) from BankAccount ba " +
            "where ba.id = :id")
    BankAccountDto getById(@Param("id") Long id);

    @Query("select contr.bankAccounts from Contractor contr where contr.id = :id")
    List<BankAccount> getBankAccountByContractorId(@Param("id") Long id);

    @Query("select comp.bankAccounts from Company comp where comp.id = :id")
    List<BankAccount> getBankAccountByCompanyId(@Param("id") Long id);

    @Query("select distinct rcbic from BankAccount")
    List<String> getListBankUniqueBic();

    @Query("select new com.trade_accounting.models.dto.BankAccountDto(" +
            "ba.id, " +
            "ba.rcbic, " +
            "ba.bank, " +
            "ba.address, " +
            "ba.correspondentAccount, " +
            "ba.account, " +
            "ba.mainAccount, " +
            "ba.sortNumber) from BankAccount ba " +
            "where ba.rcbic = :rcbic")
    BankAccountDto getBank(@Param("rcbic") String rcbic);

}
