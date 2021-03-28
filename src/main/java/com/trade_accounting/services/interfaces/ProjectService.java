package com.trade_accounting.services.interfaces;

import com.trade_accounting.models.dto.ProjectDto;

import java.util.List;

public interface ProjectService {

    List<ProjectDto> getAll();

    ProjectDto getById(Long id);

    ProjectDto create(ProjectDto projectDto);

    ProjectDto update(ProjectDto projectDto);

    void deleteById(Long id);
}
