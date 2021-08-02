package com.builder.api.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import com.builder.api.core.BusinessException;
import com.builder.api.dto.ClienteDTO;
import com.builder.api.entidade.Cliente;
import com.builder.api.mapper.ClienteMapper;
import com.builder.api.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)

public class ClienteServiceTest {
    @InjectMocks
    private ClienteService servico;
    @Mock
    private ClienteRepository repository;
    @Mock
    private ClienteMapper mapper;
    @Mock
    private MessageSource messageSource;

    @Test
    public void cadastrar_usuario_com_sucesso() {
        when(repository.findByCpf(Mockito.anyString())).thenReturn(null);
        when(repository.save(Mockito.any(Cliente.class))).thenReturn(Cliente.builder().id(1L).build());
        when(mapper.toEntity(Mockito.any(ClienteDTO.class))).thenReturn(montaEntity());
        when(mapper.toDto(Mockito.any(Cliente.class))).thenReturn(montaDTO());
        ClienteDTO cl = servico.salvar(montaDTO());
        Mockito.verify(repository, Mockito.atLeastOnce()).save(montaEntity());
        assertNotNull(cl);
    }

    @Test
    public void usuario_cpf_ja_cadastrado() {
        Assertions.assertThrows(BusinessException.class, () -> {
            when(repository.findByCpf(Mockito.anyString())).thenReturn(Cliente.builder().id(1L).build());
            servico.salvar(montaDTO());
            Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyLong());
          });
    }
    @Test
    public void usuario_cpf_branco() {
        Assertions.assertThrows(BusinessException.class, () -> {
            servico.salvar(montaDTO("",LocalDate.now(),"gustavo",37));
            Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyLong());
          });
    }
    @Test
    public void usuario_nome_branco() {
        Assertions.assertThrows(BusinessException.class, () -> {
            servico.salvar(montaDTO("",LocalDate.now(),"",37));
            Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyLong());
        });
    }
    @Test
    public void usuario_idade_nulo() {
        Assertions.assertThrows(BusinessException.class, () -> {
            servico.salvar(montaDTO("",LocalDate.now(),"",null));
            Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyLong());
        });
    }
    @Test
    public void usuario_dtNasc_nulo() {
        Assertions.assertThrows(BusinessException.class, () -> {
            servico.salvar(montaDTO("",null,"",37));
            Mockito.verify(repository, Mockito.atLeastOnce()).findById(Mockito.anyLong());
        });
    }

    private ClienteDTO montaDTO() {
        return ClienteDTO.builder().cpf("99533260106").dataNascimento(LocalDate.of(1984, 4, 16)).idade(37)
                .nome("Gustavo Augusto Ribeiro").build();
    }
    private ClienteDTO montaDTO(String cpf,LocalDate dtNasc,String nome,Integer idade) {
        return ClienteDTO.builder().cpf(cpf).dataNascimento(dtNasc).idade(idade)
                .nome(nome).build();
    }

    private Cliente montaEntity() {
        return Cliente.builder().cpf("99533260106").dataNascimento(LocalDate.of(1984, 4, 16)).idade(37)
                .nome("Gustavo Augusto Ribeiro").build();
    }

}
