package com.builder.api.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaginacaoDTO<T> {

	private List<T> itens;
	private Long totalItens;
	private Integer itensPorPagina;
	private Integer paginaAtual;
}
