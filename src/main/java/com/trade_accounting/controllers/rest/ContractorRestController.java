package com.trade_accounting.controllers.rest;

import com.trade_accounting.models.Contractor;
import com.trade_accounting.models.dto.ContractorDto;
import com.trade_accounting.models.dto.fias.FiasAddressModelDto;
import com.trade_accounting.repositories.AddressRepository;
import com.trade_accounting.services.interfaces.AddressService;
import com.trade_accounting.services.interfaces.ContractorService;
import com.trade_accounting.services.interfaces.fias.FiasDbService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.LikeIgnoreCase;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;
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
@Tag(name = "Contractor Rest Controller", description = "CRUD  операции с контрагентами")
@Api(tags = "Contractor Rest Controller")
@RequestMapping("/api/contractor")
public class ContractorRestController {

    private final ContractorService contractorService;
    private final FiasDbService fiasDbService;
    private final AddressService addressService;

    public ContractorRestController(ContractorService contractorService, FiasDbService fiasDbService, AddressService addressService) {
            this.contractorService = contractorService;
            this.fiasDbService = fiasDbService;
            this.addressService = addressService;
        }

    @GetMapping
    @ApiOperation(value = "getAll", notes = "Получение списка всех контрагентов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешное получение списка контрагентов"),
            @ApiResponse(code = 404, message = "Данный контроллер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")}
    )
    public ResponseEntity<List<ContractorDto>> getAll() {
        List<ContractorDto> contractorDtoList = contractorService.getAll();
        return ResponseEntity.ok(contractorDtoList);
    }

    @GetMapping("/searchAddressByLevel/{level}")
    @ApiOperation(value = "level", notes = "Получение списка из БД Адресов по уровню")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешное получение списка адресов"),
            @ApiResponse(code = 404, message = "Данный контроллер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")}
    )
    public ResponseEntity<List<FiasAddressModelDto>> searchAddressByLevel(@ApiParam(name = "level",
            value = "Переданный в URL aolevel по которому необходимо получить список") @PathVariable(name = "level") Byte level) {
        List<FiasAddressModelDto> addressList = fiasDbService.findAllByLevel(String.valueOf(level));
        return ResponseEntity.ok(addressList);
    }

    @GetMapping("/searchAddressByAoguid/{aoguid}")
    @ApiOperation(value = "aoguid", notes = "Получение списка из БД Адресов по уровню")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешное получение списка адресов"),
            @ApiResponse(code = 404, message = "Данный контроллер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")}
    )
    public ResponseEntity<List<FiasAddressModelDto>> searchAddressByAoguid(@ApiParam(name = "aoguid",
            value = "Переданный в URL aolevel по которому необходимо получить список") @PathVariable(name = "aoguid") String level) {
        List<FiasAddressModelDto> addressList = fiasDbService.findAllByAoguid(level);
        return ResponseEntity.ok(addressList);
    }

    @GetMapping("/lite")
    @ApiOperation(value = "getAllLite", notes = "Получение лёгкого списка всех контрагентов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешное получение лёгкого списка контрагентов"),
            @ApiResponse(code = 404, message = "Данный контроллер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")}
    )
    public ResponseEntity<List<ContractorDto>> getAllLite() {
        List<ContractorDto> contractorDtoList = contractorService.getAll();
        return ResponseEntity.ok(contractorDtoList);
    }

    @GetMapping("/search/{searchTerm}")
    @ApiOperation(value = "searchTerm", notes = "Получение списка некоторых контрагентов")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Успешное получение отф. списка контрагентов"),
            @ApiResponse(code = 404, message = "Данный контроллер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")}
    )
    public ResponseEntity<List<ContractorDto>> getAll(@ApiParam(name = "searchTerm",
            value = "Переданный в URL searchTerm, по которому необходимо найти контрагента")
                                                      @PathVariable(name = "searchTerm") String searchTerm) {
        List<ContractorDto> contractorDtoList = contractorService.getAll(searchTerm);
        return ResponseEntity.ok(contractorDtoList);
    }

    @GetMapping("/searchContractor")
    @ApiOperation(value = "searchContractor", notes = "Получение списка контрактов по заданным параметрам")
    public ResponseEntity<List<ContractorDto>> getAllFilter(
            @And({
                    @Spec(path = "id", params = "id", spec = Equal.class),
                    @Spec(path = "name", params = "name", spec = LikeIgnoreCase.class),
                    @Spec(path = "sortNumber", params = "sortNumber", spec = LikeIgnoreCase.class),
                    @Spec(path = "phone", params = "phone", spec = LikeIgnoreCase.class),
                    @Spec(path = "fax", params = "fax", spec = LikeIgnoreCase.class),
                    @Spec(path = "email", params = "email", spec = LikeIgnoreCase.class),
                    @Spec(path = "address", params = "address", spec = LikeIgnoreCase.class),
                    @Spec(path = "commentToAddress", params = "commentToAddress", spec = LikeIgnoreCase.class),
                    @Spec(path = "comment", params = "comment", spec = LikeIgnoreCase.class),
//                    @Spec(path = "contractor.contractorGroup", params = "contractorGroupDto", spec = Like.class),
//                    @Spec(path = "contractor.typeOfContractor", params = "typeOfContractorDto", spec = LikeIgnoreCase.class),
//                    @Spec(path = "contractor.typeOfPrice", params = "typeOfPriceDto", spec = LikeIgnoreCase.class),
//                    @Spec(path = "contractor.bankAccounts", params = "bankAccountsDto", spec = LikeIgnoreCase.class),
//                    @Spec(path = "contractor.legalDetail", params = "legalDetail", spec = Equal.class),
            }) Specification<Contractor> spec) {
        return ResponseEntity.ok(contractorService.search(spec));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "getById", notes = "Получение контрагента по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Контрагент найден"),
            @ApiResponse(code = 404, message = "Данный контроллер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")}
    )
    public ResponseEntity<ContractorDto> getById(@ApiParam(name = "id",
            value = "Переданный в URL id по которому необходимо найти контрагента")
                                                 @PathVariable(name = "id") Long id) {
        ContractorDto contractorDto = contractorService.getById(id);
        return ResponseEntity.ok(contractorDto);
    }

    @PostMapping
    @ApiOperation(value = "create", notes = "Внесение нового контрагента")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Контрагент создан"),
            @ApiResponse(code = 201, message = "Запрос принят и контрагент добавлен"),
            @ApiResponse(code = 404, message = "Данный контроллер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")}
    )
    public ResponseEntity<ContractorDto> create(@ApiParam(name = "contractorDto",
            value = "DTO контрагента, которого необходимо создать")
                                                @RequestBody ContractorDto contractorDto) {
        addressService.create(contractorDto.getLegalDetailDto().getAddressDto());
        ContractorDto contractorDtoCreate = contractorService.create(contractorDto);
        return ResponseEntity.ok(contractorDtoCreate);
    }

    @PutMapping
    @ApiOperation(value = "update", notes = "Изменение информации о контрагенте")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Информация о контрагенте обновлена"),
            @ApiResponse(code = 201, message = "Запрос принят и данные о контрагенте обновлены"),
            @ApiResponse(code = 404, message = "Данный контроллер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")}
    )
    public ResponseEntity<ContractorDto> update(@ApiParam(name = "contractorDto",
            value = "DTO контрагента, которого необходимо обновить")
                                                @RequestBody ContractorDto contractorDto) {
       ContractorDto contractorDtoUpdated = contractorService.update(contractorDto);
      //  return ResponseEntity.ok().build();
        return ResponseEntity.ok(contractorDtoUpdated);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "deleteById", notes = "Удаление контрагента по id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Контрагент удален"),
            @ApiResponse(code = 204, message = "Запрос получен и обработан, данных для возврата нет"),
            @ApiResponse(code = 404, message = "Данный контроллер не найден"),
            @ApiResponse(code = 403, message = "Операция запрещена"),
            @ApiResponse(code = 401, message = "Нет доступа к данной операции")}
    )
    public ResponseEntity<ContractorDto> deleteById(@ApiParam(name = "id", type = "Long",
            value = "Переданный в URL id по которому необходимо удалить контрагента")
                                                    @PathVariable("id") Long id) {
        contractorService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
