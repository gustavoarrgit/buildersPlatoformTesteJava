package com.builder.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.builder.api.entidade.Cliente;

public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Long>, ClienteQuery {

	Cliente findByCpf(String cpf);
	
	Optional<Cliente> findByCpfAndIdNotIn(String cpf, List<Long> list);

}
