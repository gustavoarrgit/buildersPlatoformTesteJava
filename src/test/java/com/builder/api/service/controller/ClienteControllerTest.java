package com.builder.api.service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.builder.api.controller.ClienteController;
import com.builder.api.dto.ClienteDTO;
import com.builder.api.service.ClienteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = ClienteController.class)
public class ClienteControllerTest {

    @MockBean
    private ClienteService service;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @Test
    public void teste_criar_cliente_controller() throws JsonProcessingException, Exception {
        ClienteDTO post = montaDTO();

        mockMvc.perform(post("/cliente")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(post)))
                .andExpect(status().isCreated());
    }
    
  
    private ClienteDTO montaDTO() {
        return ClienteDTO.builder().cpf("99533260106").dataNascimento(LocalDate.of(1984, 4, 16)).idade(37)
                .nome("Gustavo Augusto Ribeiro").build();
    }

}
