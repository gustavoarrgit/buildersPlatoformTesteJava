package com.builder.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.builder.api.dto.ClienteConsultaDTO;
import com.builder.api.dto.ClienteDTO;
import com.builder.api.service.ClienteService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/cliente")
@Api(value = "API REST Cliente Builder")
public class ClienteController {

	@Autowired
	private ClienteService service;

	@ApiOperation(value = "Recupera o cliente por Id", response = ClienteDTO.class)
	@GetMapping("/{id}")
	public ResponseEntity<?> pesquisaPorId(
			@ApiParam(name = "id", value = "Identificador utilizado para recuperar o cliente", required = true, example = "1") 
			@PathVariable(required = true) Long id) {

		ClienteDTO dto = service.pesquisaPorId(id);

		if (dto != null) {
			return ResponseEntity.status(HttpStatus.OK).body(dto);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não localizado");
	}

	@ApiOperation(value = "Adiciona um cliente" , response = ClienteDTO.class)
	@PostMapping
	public ResponseEntity<?> salvar(
			@ApiParam(name = "clienteDTO", value = "Representação do cliente a ser adicionado", required = true) 
			@RequestBody ClienteDTO clienteDTO) {

		ClienteDTO dto = service.salvar(clienteDTO);

		return ResponseEntity.status(HttpStatus.CREATED).body(dto);
	}

    @ApiOperation(value = "Atualiza um cliente", response = ClienteDTO.class)
	@PutMapping
	public ResponseEntity<?> atualizar(
			  @ApiParam(name = "clienteDTO", value = "Representação do cliente a ser alterado")
			@RequestBody ClienteDTO clienteDTO) {

		ClienteDTO dto = service.alterar(clienteDTO);

		return ResponseEntity.ok(dto);
	}

    @ApiOperation(value = "Exclui um cliente")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> excluir(
            @ApiParam(name = "id", value = "Identificador utilizado para excluir o cliente", required = true, example = "1")
			@PathVariable Long id) {

		service.excluir(id);

		return ResponseEntity.status(HttpStatus.OK).body("Cliente removido com sucesso");

	}

    @ApiOperation(value = "Recuperar os clientes", response = ClienteDTO[].class)
	@PostMapping("/listar")
	public ResponseEntity<?> listar(
			  @ApiParam(name = "clienteConsultaDTO", value = "Filtro utilizado para pesquisar e paginar a lista de clientes")
			@RequestBody ClienteConsultaDTO clienteConsultaDTO) {

		return ResponseEntity.status(HttpStatus.OK).body(service.listar(clienteConsultaDTO));

	}

}
