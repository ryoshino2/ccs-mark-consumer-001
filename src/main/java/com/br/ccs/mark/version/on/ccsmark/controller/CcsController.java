package com.br.ccs.mark.version.on.ccsmark.controller;

import com.br.ccs.mark.version.on.ccsmark.model.ClienteKafka;
import com.br.ccs.mark.version.on.ccsmark.repository.ClienteKafkaRepository;
import com.br.ccs.mark.version.on.ccsmark.service.CcsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@RestController
public class CcsController {

    @Autowired
    private ClienteKafkaRepository clienteKafkaRepository;

    @Autowired
    private CcsService ccsService;

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/consumirPeloKafka")
    public void consumirPeloKafka() throws IOException {
        ccsService.consumirSincrono();
    }

    @GetMapping("/consumirPeloKafkaAssincrono")
    public void consumirPeloKafkaAssincrono() throws IOException {
        ccsService.consumirPeloKafkaAssincrono();
    }

    @GetMapping("/clienteKafka")
    public ResponseEntity<List<ClienteKafka>> listarClientes() {
        List<ClienteKafka> contaClienteList = clienteKafkaRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(contaClienteList);
    }
}