package com.trade_accounting.services.interfaces;

import com.trade_accounting.models.dto.DepartmentDto;
import java.util.List;

public interface DepartmentService {

    List<DepartmentDto> getAll();

    DepartmentDto getById(Long id);

    DepartmentDto getByName(String name);

    DepartmentDto create(DepartmentDto departmentDto);

    DepartmentDto update(DepartmentDto departmentDto);

    void deleteById(Long id);
}
