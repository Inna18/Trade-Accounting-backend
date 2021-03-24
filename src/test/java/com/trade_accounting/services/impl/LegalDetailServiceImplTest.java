package com.trade_accounting.services.impl;

import com.trade_accounting.models.LegalDetail;
import com.trade_accounting.models.TypeOfContractor;
import com.trade_accounting.models.dto.LegalDetailDto;
import com.trade_accounting.repositories.LegalDetailRepository;
import com.trade_accounting.repositories.TypeOfContractorRepository;
import com.trade_accounting.services.impl.Stubs.DtoStubs;
import com.trade_accounting.services.impl.Stubs.ModelStubs;
import com.trade_accounting.utils.DtoMapperImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LegalDetailServiceImplTest {

    @Mock
    private LegalDetailRepository legalDetailRepository;

    @Mock
    private TypeOfContractorRepository typeOfContractorRepository;

    @Spy
    private DtoMapperImpl dtoMapper;

    @InjectMocks
    LegalDetailServiceImpl legalDetailService;


    @Test
    void getAll_shouldReturnListFilledLegalDetailDto() {
        when(legalDetailRepository.findAll())
                .thenReturn(
                        Stream.of(
                                ModelStubs.getLegalDetail(1L),
                                ModelStubs.getLegalDetail(2L),
                                ModelStubs.getLegalDetail(3L)
                        )
                                .collect(Collectors.toList())
                );

        List<LegalDetailDto> legalDetails = legalDetailService.getAll();

        assertNotNull(legalDetails, "Failure - expected that list of legalDetail not null");
        assertTrue(legalDetails.size() > 0, "failure - expected that size of list of legalDetail greater than 0");

        for(LegalDetailDto legalDetailDto : legalDetails){
            legalDetailDtoIsCorrectlyInited(legalDetailDto);
        }
    }

    @Test
    void getAll_shouldReturnEmptyListLegalDetailDto() {
        when(legalDetailRepository.findAll())
                .thenReturn(
                        new ArrayList<>()
                );

        List<LegalDetailDto> legalDetails = legalDetailService.getAll();

        assertNotNull(legalDetails, "Failure - expected that list of legalDetails not null");
        assertEquals(0, legalDetails.size(), "failure - expected that size of list of legalDetails equals 0");
    }

    @Test
    void getById_shouldReturnFilledLegalDetailDto() {
        Optional<LegalDetail> legalDetailFromRepo =
                Optional.of(ModelStubs.getLegalDetail(1L));

        when(legalDetailRepository.findById(anyLong()))
                .thenReturn(legalDetailFromRepo);

        LegalDetailDto legalDetailDto = legalDetailService.getById(1L);

        legalDetailDtoIsCorrectlyInited(legalDetailDto);
    }

    @Test
    void create_shouldPassInstructionsSuccessfulCreate() {
        legalDetailService.create(
                DtoStubs.getLegalDetailDto(1L)
        );

        verify(legalDetailRepository).save(any(LegalDetail.class));
        verify(typeOfContractorRepository).save(any(TypeOfContractor.class));
    }

    @Test
    void update_shouldPassInstructionsSuccessfulUpdate() {
        legalDetailService.update(
                DtoStubs.getLegalDetailDto(1L)
        );

        verify(legalDetailRepository).save(any(LegalDetail.class));
        verify(typeOfContractorRepository).findById(anyLong());
    }

    @Test
    void deleteById_shouldPassInstructionsSuccessfulDelete() {
        legalDetailService.deleteById(1L);
        verify(legalDetailRepository).deleteById(anyLong());
    }

    private void legalDetailDtoIsCorrectlyInited(LegalDetailDto legalDetailDto) {
        assertNotNull(legalDetailDto, "failure - fail in passed legalDetailDto");
        assertNotNull(legalDetailDto.getId(), "failure - fail in field 'id' into legalDetailDto");
        assertNotNull(legalDetailDto.getTypeOfContractorDto(), "failure - fail in field 'typeOfContractor' into legalDetailDto");
    }
}