package com.trade_accounting.controllers.rest;

import com.trade_accounting.models.dto.StagesProductionDto;
import com.trade_accounting.repositories.StagesProductionRepository;
import com.trade_accounting.services.interfaces.CheckEntityService;
import com.trade_accounting.services.interfaces.StagesProductionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Tag(name = "Role Rest Controller", description = "CRUD операции с этапами производства")
@Api(tags = "Role Rest Controller")
@RequestMapping("api/stagesproduction")
@RequiredArgsConstructor
public class StagesProductionRestController {

    private final StagesProductionService stagesProductionService;
    private final CheckEntityService checkEntityService;
    private final StagesProductionRepository stagesProductionRepository;

    @ApiOperation(value = "getAll", notes = "Возвращает список всех этапов производства")
    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешное получение списка всех этапов производства"),
            @ApiResponse(code = 404, message = "Данный контролер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")}
    )
    public ResponseEntity<List<StagesProductionDto>> getAll() {
        List<StagesProductionDto> stagesProductionDto = stagesProductionService.getAll();
        return ResponseEntity.ok(stagesProductionDto);
    }

    @ApiOperation(value = "getById", notes = "Возвращает определенный этап производства по Id")
    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Этап производства найден"),
            @ApiResponse(code = 404, message = "Данный контролер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")}
    )
    public ResponseEntity<StagesProductionDto> getById(@ApiParam(
            name = "id",
            type = "Long",
            value = "Переданный ID  в URL по которому необходимо найти этап производства",
            example = "1",
            required = true) @PathVariable("id") Long id) {
        checkEntityService.checkExists((JpaRepository) stagesProductionRepository, id);
        return ResponseEntity.ok(stagesProductionService.getById(id));
    }

    @ApiOperation(value = "create", notes = "Создает этап производства на основе переданных данных")
    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Этап производства успешно создан"),
            @ApiResponse(code = 201, message = "Запрос принят и данные созданы"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 404, message = "Данный контролер не найден")}
    )
    public ResponseEntity<?> create(@ApiParam(name = "stagesProductionDto",
            value = "DTO роли, которую необходимо создать") @RequestBody StagesProductionDto stagesProductionDto) {
        return ResponseEntity.ok().body(stagesProductionService.create(stagesProductionDto));
    }

    @ApiOperation(value = "update", notes = "Обновляет этап производства на основе переданных данных")
    @PutMapping
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Этап производства успешно обновлен"),
            @ApiResponse(code = 201, message = "Запрос принят и данные обновлены"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 404, message = "Данный контролер не найден")}
    )
    public ResponseEntity<?> update(@ApiParam(name = "stagesProductionDto",
            value = "DTO этапа производства, который необходимо обновить")
                                    @RequestBody StagesProductionDto stagesProductionDto) {
        return ResponseEntity.ok().body(stagesProductionService.update(stagesProductionDto));
    }

    @ApiOperation(value = "deleteById", notes = "Удаляет этап производства на основе переданного ID")
    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Этап производства успешно удален"),
            @ApiResponse(code = 204, message = "Запрос получен и обработан, данных для возврата нет"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 404, message = "Данный контролер не найден")}
    )
    public ResponseEntity<StagesProductionDto> deleteById(@ApiParam(
            name = "id",
            type = "Long",
            value = "Переданный ID  в URL по которому необходимо удалить этап производства",
            example = "1",
            required = true
    ) @PathVariable("id") Long id) {
        checkEntityService.checkExists((JpaRepository) stagesProductionRepository, id);
        stagesProductionService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
