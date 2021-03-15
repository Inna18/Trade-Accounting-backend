package com.trade_accounting.controllers.rest;

import com.trade_accounting.models.dto.UnitDto;
import com.trade_accounting.services.interfaces.CheckEntityService;
import com.trade_accounting.services.interfaces.UnitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RestController
@Tag(name = "Unit Rest Controller", description = "CRUD операции с единицами измерения")
@Api(tags = "Unit Rest Controller")
@RequestMapping("/api/unit")
public class UnitRestController {

    private final UnitService unitService;
    private final CheckEntityService checkEntityService;

    public UnitRestController(UnitService unitService, CheckEntityService checkEntityService) {
        this.unitService = unitService;
        this.checkEntityService = checkEntityService;
    }

    @ApiOperation(value = "getAll", notes = "Возвращает список всех единиц измерения")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешное получение списка всех единиц измерения"),
            @ApiResponse(code = 404, message = "Данный контролер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")}
    )
    @GetMapping
    public ResponseEntity<List<UnitDto>> getAll() {
        List<UnitDto> units = unitService.getAll();
        log.info("Запрошен список UnitDto");
        return ResponseEntity.ok(units);
    }

    @ApiOperation(value = "getById", notes = "Возвращает единицу измерения по её Id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Единица измерения найдена"),
            @ApiResponse(code = 404, message = "Данный контролер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")}
    )
    @GetMapping("/{id}")
    public ResponseEntity<UnitDto> getById(@ApiParam(
            name = "id",
            type = "Long",
            value = "ID переданный в URL по которому необходимо найти единицу измерения") @PathVariable Long id) {
        checkEntityService.checkExistsUnitById(id);
        UnitDto unit = unitService.getById(id);
        log.info("Запрошен экземпляр UnitDto с id= {}", id);
        return ResponseEntity.ok(unit);
    }

    @ApiOperation(value = "create", notes = "Регистрация новой единицы измерения")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Единица измерения успешно создана"),
            @ApiResponse(code = 201, message = "Запрос принят и данные созданы"),
            @ApiResponse(code = 404, message = "Данный контролер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")}
    )
    @PostMapping
    public ResponseEntity<?> create(@ApiParam(
            name = "unitDto",
            value = "DTO единицы измерения, которую необходимо создать") @RequestBody UnitDto unitDto) {
        unitService.create(unitDto);
        log.info("Записан новый экземпляр {}", unitDto.toString());
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "update", notes = "Обновление информации о единицы измерения")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Информация о единицы измерения успешно обновлена"),
            @ApiResponse(code = 201, message = "Запрос принят и данные обновлены"),
            @ApiResponse(code = 404, message = "Данный контролер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")}
    )
    @PutMapping
    public ResponseEntity<?> update(@ApiParam(
            name = "unitDto",
            value = "DTO единицы измерения, которую необходимо обновить") @RequestBody UnitDto unitDto) {
        checkEntityService.checkExistsUnitById(unitDto.getId());
        unitService.update(unitDto);
        log.info("Обновлен экземпляр UnitDto с id= {}", unitDto.getId());
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "deleteById", notes = "Удаление единицы измерения по ее id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Единица измерения удалена"),
            @ApiResponse(code = 204, message = "Запрос получен и обработан, данных для возврата нет"),
            @ApiResponse(code = 404, message = "Данный контроллер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@ApiParam(
            name = "id",
            type = "Long",
            value = "ID переданный в URL по которому необходимо удалить единицу измерения") @PathVariable Long id) {
        unitService.deleteById(id);
        log.info("Удален экземпляр UnitDto с id= {}", id);
        return ResponseEntity.ok().build();
    }

}
