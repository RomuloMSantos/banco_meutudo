package br.com.banco_meutudo.controller;

import br.com.banco_meutudo.dto.ContaDto;
import br.com.banco_meutudo.dto.ContaRetornoDto;
import br.com.banco_meutudo.dto.TransferenciaFuturaRetornoDto;
import br.com.banco_meutudo.dto.TransferenciaRetornoDto;
import br.com.banco_meutudo.service.ContaService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping("/api/v1/contas")
@Validated
public class ContaController {

    @Autowired
    private ContaService contaService;

    @ApiOperation(value = "Retorna o saldo de uma conta.")
    @GetMapping("/{id}/saldo")
    public ResponseEntity<Double> getSaldoById(@PathVariable @Min(value = 1, message = "Id inválido!") long id) {
        return new ResponseEntity<>(contaService.getSaldoById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna uma conta a partir de seus dados.")
    @GetMapping
    public ResponseEntity<ContaRetornoDto> getBy(@Valid ContaDto contaDto) {
        ContaRetornoDto retorno = contaService.getBy(contaDto);

        if (retorno == null)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(contaService.getBy(contaDto), HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna a lista de transferências realizadas de uma conta.")
    @GetMapping("/{id}/transferencias")
    public ResponseEntity<List<TransferenciaRetornoDto>> getTransferenciasById(@PathVariable @Min(value = 1, message = "Id inválido!") long id) {
        return new ResponseEntity<>(contaService.getTransferenciasById(id), HttpStatus.OK);
    }

    @ApiOperation(value = "Retorna a lista de transferências futuras de uma conta.")
    @GetMapping("/{id}/transferencias/futuras")
    public ResponseEntity<List<TransferenciaFuturaRetornoDto>> getTransferenciasFuturasById(@PathVariable @Min(value = 1, message = "Id inválido!") long id) {
        return new ResponseEntity<>(contaService.getTransferenciasFuturasById(id), HttpStatus.OK);
    }

}
