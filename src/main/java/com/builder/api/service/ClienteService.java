package com.builder.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.builder.api.core.BusinessException;
import com.builder.api.dto.ClienteConsultaDTO;
import com.builder.api.dto.ClienteDTO;
import com.builder.api.entidade.Cliente;
import com.builder.api.mapper.ClienteMapper;
import com.builder.api.repository.ClienteRepository;

@Service
@Transactional(readOnly = true)
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Autowired
    private ClienteMapper mapper;

    @Autowired
    private MessageSource messageSource;

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public ClienteDTO pesquisaPorId(Long id) {
        Cliente cliente = repository.findById(id).orElse(null);

        return cliente == null ? null : mapper.toDto(cliente);

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public ClienteDTO salvar(ClienteDTO dto) {

        validacao(dto);

        Cliente cliente = repository.save(mapper.toEntity(dto));

        dto = mapper.toDto(cliente);

        return dto;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public ClienteDTO alterar(ClienteDTO dto) {

        validacao(dto, dto.getId());

        repository.save(mapper.toEntity(dto));

        return dto;

    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
    public void excluir(Long id) {

      Cliente cliente =  repository.findById(id).orElseThrow(
                () -> new BusinessException(
                        messageSource.getMessage("mensagem.cliente.nao.encontrado", null, LocaleContextHolder.getLocale())));

        repository.delete(cliente);
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Cliente> listar(ClienteConsultaDTO clienteConsultaDTO) {

        Pageable pageable = PageRequest.of(clienteConsultaDTO.getPageNo(), clienteConsultaDTO.getPageSize());

        Page<Cliente> result = repository.listarCliente(clienteConsultaDTO, pageable);

        if (result.hasContent()) {
            return result.getContent();
        } else {
            return new ArrayList<Cliente>();
        }

    }

    private void validacao(ClienteDTO dto, Long id) {

        if (id == null) {
            throw new BusinessException(
                    messageSource.getMessage("mensagem.campo.obrigatorio", new Object[] {"id"}, LocaleContextHolder.getLocale()));
        }
        if (StringUtils.isBlank(dto.getCpf())) {
            throw new BusinessException(
                    messageSource.getMessage("mensagem.campo.obrigatorio", new Object[] {"CPF"},
                            LocaleContextHolder.getLocale()));
        } else {
        	 Optional<Cliente> cliente = repository.findByCpfAndIdNotIn(dto.getCpf(), List.of(dto.getId()));
        	 if(cliente.isPresent()) {
        		 throw new BusinessException(
                         messageSource.getMessage("mensagem.cpf.existe", new Object[] {"Id"},
                                 LocaleContextHolder.getLocale()));
        	 }
        }

        validacao(dto);
    }

    private void validacao(ClienteDTO dto) {

        if (StringUtils.isBlank(dto.getNome())) {
            throw new BusinessException(
                    messageSource.getMessage("mensagem.campo.obrigatorio", new Object[] {"Nome"},
                            LocaleContextHolder.getLocale()));
        }

        if (dto.getIdade() == null) {
            throw new BusinessException(
                    messageSource.getMessage("mensagem.campo.obrigatorio", new Object[] {"Idade"},
                            LocaleContextHolder.getLocale()));
        }
        // valida cadastro novo cliente, verifica se cpf já existe.
        if (dto.getId() == null) {
            if (StringUtils.isBlank(dto.getCpf())) {
                throw new BusinessException(
                        messageSource.getMessage("mensagem.campo.obrigatorio", new Object[] {"CPF"},
                                LocaleContextHolder.getLocale()));
            } else {

                Cliente cliente = repository.findByCpf(dto.getCpf());
                if (cliente != null) {
                    throw new BusinessException(
                            messageSource.getMessage("mensagem.cpf.existe", new Object[] {"Id"},
                                    LocaleContextHolder.getLocale()));
                }

            }
        }

        if (dto.getDataNascimento() == null) {
            throw new BusinessException(
                    messageSource.getMessage("mensagem.campo.obrigatorio", new Object[] {"Data Nascimento"},
                            LocaleContextHolder.getLocale()));
        }
    }

}
