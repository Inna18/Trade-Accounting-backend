package com.trade_accounting.models.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReturnsToSupplierDto {

    @NotNull
    private Long id;

    @NotNull
    private String date;

    @NotNull
    private Long warehouseId;

    @NotNull
    private Long companyId;

    @NotNull
    private Long contractorId;

    @NotNull
    private Long contractId;

    private Boolean isSend;

    private Boolean isPrint;

    private String comment;

}
