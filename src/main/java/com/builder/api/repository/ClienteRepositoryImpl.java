package com.builder.api.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.builder.api.dto.ClienteConsultaDTO;
import com.builder.api.entidade.Cliente;

public class ClienteRepositoryImpl implements ClienteQuery {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public Page<Cliente> listarCliente(ClienteConsultaDTO dto, Pageable pageable) {

		Long count = (Long) consultar(dto, true).getSingleResult();

		return new PageImpl<>(criarListaPageable(dto, pageable), pageable, count);
	}

	private List<Cliente> criarListaPageable(ClienteConsultaDTO dto, Pageable pageable) {

		Query query = consultar(dto, false);

		int paginaAtual = pageable.getPageNumber();
		int totalRegistrosPorPagina = pageable.getPageSize();
		int primeiroRegistroDaPagina = paginaAtual * totalRegistrosPorPagina;

		query.setFirstResult(primeiroRegistroDaPagina);
		query.setMaxResults(totalRegistrosPorPagina);

		return query.getResultList();
	}

	private Query consultar(ClienteConsultaDTO dto, boolean count) {
		Map<String, Object> paramBuilder = new HashMap<>();
		StringBuilder hql = new StringBuilder();

		if (count) {
			hql.append(" SELECT COUNT(*)                              ");
		}

		hql.append(" FROM Cliente ent                                 ");
		hql.append(" WHERE ent.id is not null                         ");

		
		if(dto.getId() != null) {
			hql.append(" AND   ent.id = :id                         ");
			paramBuilder.put("id", dto.getId());
		}
		
		if (StringUtils.isNotBlank(dto.getNome())) {
			hql.append(" AND   upper(ent.nome) like upper(:nome)       ");
			paramBuilder.put("nome", "%" + dto.getNome().trim() + "%");
		}

		if (dto.getIdade() != null) {
			hql.append(" AND   ent.idade = :idade                         ");
			paramBuilder.put("idade", dto.getIdade());

		}

		if (StringUtils.isNotBlank(dto.getCpf())) {
			hql.append(" AND   ent.cpf = :cpf                             ");
			paramBuilder.put("cpf", dto.getCpf());

		}

		if (dto.getDataNascimento() != null) {
			hql.append(" AND   ent.dataNascimento = :dataNascimento       ");
			paramBuilder.put("dataNascimento", dto.getDataNascimento());
		}

		Query query = entityManager.createQuery(hql.toString());

		for (String param : paramBuilder.keySet()) {
			query.setParameter(param, paramBuilder.get(param));
		}

		return query;

	}

}
