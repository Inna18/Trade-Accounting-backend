package com.trade_accounting.services.impl;

import com.trade_accounting.models.Correction;
import com.trade_accounting.models.dto.CorrectionDto;
import com.trade_accounting.repositories.CompanyRepository;
import com.trade_accounting.repositories.CorrectionProductRepository;
import com.trade_accounting.repositories.CorrectionRepository;
import com.trade_accounting.repositories.WarehouseRepository;
import com.trade_accounting.services.impl.Stubs.DtoStubs;
import com.trade_accounting.services.impl.Stubs.ModelStubs;
import com.trade_accounting.utils.DtoMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CorrectionServiceImplTest {

    @Mock
    CorrectionRepository correctionRepository;

    @Mock
    CorrectionProductRepository correctionProductRepository;

    @Mock
    WarehouseRepository warehouseRepository;

    @Mock
    CompanyRepository companyRepository;

    @Spy
    DtoMapperImpl dtoMapper;

    @InjectMocks
    CorrectionServiceImpl correctionService;

    @Test
    void getAll() {
        when(correctionRepository.getAll()).thenReturn(
                List.of(ModelStubs.getCorrection(1L),
                        ModelStubs.getCorrection(2L),
                        ModelStubs.getCorrection(3L))
        );

        List<CorrectionDto> correctionDtos = correctionService.getAll();

        assertEquals(3, correctionDtos.size());
    }

    @Test
    void getById() {
        when(correctionRepository.getCorrectionById(anyLong())).thenReturn(ModelStubs.getCorrection(1L));

        CorrectionDto correctionDto = correctionService.getById(1L);

        assertEquals(1, correctionDto.getId());
    }

    @Test
    void create() {
        saveOrUpdate();
    }

    @Test
    void update() {
        saveOrUpdate();
    }

    @Test
    void deleteById() {
        correctionService.deleteById(anyLong());
        verify(correctionRepository).deleteById(anyLong());
    }

    private void saveOrUpdate() {
        when(correctionRepository.save(any(Correction.class))).thenReturn(ModelStubs.getCorrection(1L));
        CorrectionDto correctionDto = correctionService.create(DtoStubs.getCorrectionDto(1L));
        assertEquals(1, correctionDto.getId());
        verify(correctionRepository).save(any(Correction.class));
    }
}