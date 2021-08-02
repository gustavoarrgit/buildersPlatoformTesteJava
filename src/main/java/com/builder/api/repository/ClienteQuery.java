package com.builder.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.builder.api.dto.ClienteConsultaDTO;
import com.builder.api.entidade.Cliente;

public interface ClienteQuery {

	Page<Cliente> listarCliente(ClienteConsultaDTO dto, Pageable pageable);

}
