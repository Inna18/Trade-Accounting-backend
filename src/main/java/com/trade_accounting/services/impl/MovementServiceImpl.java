package com.trade_accounting.services.impl;

import com.trade_accounting.models.Company;
import com.trade_accounting.models.Employee;
import com.trade_accounting.models.Movement;
import com.trade_accounting.models.MovementProduct;
import com.trade_accounting.models.Project;
import com.trade_accounting.models.Warehouse;
import com.trade_accounting.models.dto.MovementDto;
import com.trade_accounting.repositories.CompanyRepository;
import com.trade_accounting.repositories.MovementProductRepository;
import com.trade_accounting.repositories.MovementRepository;
import com.trade_accounting.repositories.ProjectRepository;
import com.trade_accounting.repositories.WarehouseRepository;
import com.trade_accounting.services.interfaces.MovementProductService;
import com.trade_accounting.services.interfaces.MovementService;
import com.trade_accounting.utils.mapper.MovementMapper;
import com.trade_accounting.utils.mapper.WarehouseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MovementServiceImpl implements MovementService {

    private final MovementRepository movementRepository;
    private final WarehouseRepository warehouseRepository;
    private final CompanyRepository companyRepository;
    private final MovementProductRepository movementProductRepository;
    private final MovementProductService movementProductService;
    private final WarehouseMapper warehouseMapper;
    private final ProjectRepository projectRepository;
    private final MovementMapper movementMapper;

    @Override
    public List<MovementDto> getAll() {
        return movementRepository.getAll().stream()
                .map(movementMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public MovementDto getById(Long id) {
        return movementMapper.toDto(movementRepository.getMovementById(id));
    }

    @Override
    public MovementDto create(MovementDto dto) {
        return saveOrUpdate(dto);
    }

    @Override
    public MovementDto update(MovementDto dto) {
        return saveOrUpdate(dto);
    }

    @Override
    public void deleteById(Long id) {
        movementRepository.deleteById(id);
    }

    private MovementDto saveOrUpdate(MovementDto dto) {
        Movement movement = movementMapper.toModel(dto);
        Warehouse warehouseFrom = warehouseMapper.toModel(warehouseRepository.getById(dto.getWarehouseFromId()));
        Warehouse warehouseTo = warehouseMapper.toModel(warehouseRepository.getById(dto.getWarehouseToId()));
        Company company = companyRepository.getCompaniesById(dto.getCompanyId());
        Project project = projectRepository.getOne(dto.getProjectId());
        LocalDateTime date = LocalDateTime.parse(dto.getDate(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        List<MovementProduct> movementProducts = dto.getMovementProductsIds().stream()
                .map(id -> movementProductRepository.findById(id).orElse(null)).collect(Collectors.toList());

        movement.setWarehouseFrom(warehouseFrom);
        movement.setWarehouseTo(warehouseTo);
        movement.setCompany(company);
        movement.setDate(date);
        movement.setMovementProducts(movementProducts);
        movement.setProject(project);
        movement.setWhenСhangedDate(LocalDate.now());

        //Что работало в Postman, закомментить следующую строчку
        movement.setEmployeeChanged((Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal());

        return movementMapper.toDto(movementRepository.save(movement));
    }
}
