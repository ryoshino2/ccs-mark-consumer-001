package com.br.ccs.mark.version.on.ccsmark.controller;

import com.br.ccs.mark.version.on.ccsmark.model.ClienteKafka;
import com.br.ccs.mark.version.on.ccsmark.repository.ClienteKafkaRepository;
import com.br.ccs.mark.version.on.ccsmark.service.CcsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(CcsController.class)
public class CcsControllerTest {
    private ClienteKafka clienteKafka;

    @Autowired
    private CcsController ccsController;

    @MockBean
    private CcsService ccsService;

    @MockBean
    private ClienteKafkaRepository clienteKafkaRepository;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setup(){
        clienteKafka = new ClienteKafka(1,"Rafael","endereco",123,"rafael@email.com",789, new Date());
        ccsController = new CcsController(ccsService);
    }

    @Test
    public void listarClienteTest() throws Exception {

        //criando objeto esperado de resposta
        List<ClienteKafka> clienteKafkaList = new ArrayList<>();
        clienteKafkaList.add(clienteKafka);

        //determinando o retorno do serviço
        when(ccsController.listarClientes()).thenReturn(clienteKafkaList);

        //testando
        mockMvc.perform(get("/clienteKafka")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(1)))
                .andExpect(jsonPath("$[0]['idCliente']").value(clienteKafka.getIdCliente()))
                .andExpect(jsonPath("$[0]['nome']").value(clienteKafka.getNome()))
                .andExpect(jsonPath("$[0]['email']").value(clienteKafka.getEmail()));
    }

    @Test
    public void deveRetornarListaDeClientesVazia() throws Exception {

        //criando objeto esperado de resposta
        List<ClienteKafka> clienteKafkaList = new ArrayList<>();

        //determinando o retorno do serviço
        when(ccsController.listarClientes()).thenReturn(clienteKafkaList);

        //testando
        mockMvc.perform(get("/clienteKafka")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(hasSize(1)));
    }

}
