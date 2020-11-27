package com.trade_accounting.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountDto {

    private Long id;

    private String rcbic;

    private String bank;

    private String address;

    private String correspondentAccount;

    private String account;

    private Boolean mainAccount;

    private String sortNumber;
}
