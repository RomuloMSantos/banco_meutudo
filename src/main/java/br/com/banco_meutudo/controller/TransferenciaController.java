package br.com.banco_meutudo.controller;

import br.com.banco_meutudo.dto.TransferenciaDto;
import br.com.banco_meutudo.dto.TransferenciaFuturaDto;
import br.com.banco_meutudo.service.TransferenciaService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@RestController
@RequestMapping("/api/v1/transferencias")
@Validated
public class TransferenciaController {

    @Autowired
    private TransferenciaService transferenciaService;

    @ApiOperation(value = "Cria uma nova transferência para a conta.")
    @PostMapping
    public ResponseEntity criar(@RequestBody @Valid TransferenciaDto transferenciaDto) {
        transferenciaService.criar(transferenciaDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Estorna uma transferência.")
    @PostMapping("/{id}/estornar")
    public ResponseEntity estornar(@PathVariable @Min(value = 1, message = "Id inválido!") long id) {
        transferenciaService.estornar(id);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiOperation(value = "Cria transferências futuras para a conta parcelando o valor.")
    @PostMapping("/futura")
    public ResponseEntity criarFutura(@RequestBody @Valid TransferenciaFuturaDto transferenciaFuturaDto) {
        transferenciaService.criarFutura(transferenciaFuturaDto);
        return new ResponseEntity(HttpStatus.CREATED);
    }

}
