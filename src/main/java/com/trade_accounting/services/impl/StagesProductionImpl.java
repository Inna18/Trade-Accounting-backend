package com.trade_accounting.services.impl;

import com.trade_accounting.models.StagesProduction;
import com.trade_accounting.models.dto.StagesProductionDto;
import com.trade_accounting.repositories.StagesProductionRepository;
import com.trade_accounting.services.interfaces.StagesProductionService;
import com.trade_accounting.utils.mapper.StagesProductionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StagesProductionImpl implements StagesProductionService {

    private final StagesProductionRepository stagesProductionRepository;

    private final StagesProductionMapper stagesProductionMapper;


    @Override
    public List<StagesProductionDto> getAll() {
        return stagesProductionRepository.findAll().stream()
                .map(stagesProductionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public StagesProductionDto getById(Long id) {
        StagesProduction stagesProduction = stagesProductionRepository.getOne(id);
        return stagesProductionMapper.toDto(stagesProduction);
    }

    @Override
    public StagesProductionDto create(StagesProductionDto dto) {
        StagesProduction stagesProduction = stagesProductionRepository.save(
                stagesProductionMapper.toModel(dto)
        );
        dto.setId(stagesProduction.getId());
        return stagesProductionMapper.toDto(stagesProduction);
    }

    @Override
    public StagesProductionDto update(StagesProductionDto dto) {
        return create(dto);
    }

    @Override
    public void deleteById(Long id) {
        stagesProductionRepository.deleteById(id);
    }

    @Override
    public List<StagesProductionDto> search(Specification<StagesProduction> spec) {
        List<StagesProduction> stagesProductionList = stagesProductionRepository.findAll(spec);

        List<StagesProductionDto> stagesProductionDtoList = new ArrayList<>();
        for (StagesProduction sp : stagesProductionList) {
            stagesProductionDtoList.add(stagesProductionMapper.toDto(sp));
        }

        return stagesProductionDtoList;
    }

    @Override
    public List<StagesProductionDto> search(String searchTerm) {

        if(searchTerm.equals("null") || searchTerm.isEmpty()) {
            List<StagesProduction> all = stagesProductionRepository.findAll();
            return all.stream().map(stagesProductionMapper::toDto).collect(Collectors.toList());
        } else {
            List<StagesProduction> list = stagesProductionRepository.search(searchTerm);
            return list.stream().map(stagesProductionMapper::toDto).collect(Collectors.toList());
        }
    }
}

