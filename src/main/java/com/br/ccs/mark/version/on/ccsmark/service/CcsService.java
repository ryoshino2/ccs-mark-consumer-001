package com.br.ccs.mark.version.on.ccsmark.service;


import com.br.ccs.mark.version.on.ccsmark.model.Cliente;
import com.br.ccs.mark.version.on.ccsmark.model.ContaCliente;
import com.br.ccs.mark.version.on.ccsmark.repository.ClienteRepository;
import com.br.ccs.mark.version.on.ccsmark.repository.ContaClienteRepository;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

@Service
@EnableScheduling
public class CcsService {

    @Autowired
    private final ClienteRepository clienteRepository;

    @Autowired
    private final ContaClienteRepository contaClienteRepository;

    public CcsService(ClienteRepository convidadoRepository, ContaClienteRepository contaClienteRepository) {
        this.clienteRepository = convidadoRepository;
        this.contaClienteRepository = contaClienteRepository;
    }

    public Iterable<Cliente> obterTodosClientes() {
        return clienteRepository.findAll();
    }

    public Iterable<ContaCliente> obterTodasContas() {
        return contaClienteRepository.findAll();
    }

    public List<ContaCliente> pesquisarPorData(Date dataAtualizacao) {
//        System.out.println(testeContador(dataAtualizacao));
        return contaClienteRepository.findByDataAtualizacao(dataAtualizacao);
    }

    private long testeContador(Date dataAtualizacao) {
        return contaClienteRepository.countByDataAtualizacao(dataAtualizacao);
    }


    public void escreverArquivo(List<ContaCliente> contaClienteList, Date dataAtualizacao) throws IOException {
        FileWriter arquivo = new FileWriter("relatorioTransacao" + new Date());

        BufferedWriter bufferedWriter = new BufferedWriter(arquivo);
        bufferedWriter.write("CPF;NOME;SALDO");
        for (ContaCliente contaCliente : contaClienteList) {
            bufferedWriter.flush();
            if (verificarSaldo(contaCliente)) {
                bufferedWriter.newLine();
                bufferedWriter.write(contaCliente.getIdCliente().getCpf() + ";");
                bufferedWriter.write(contaCliente.getIdCliente().getNome() + ";");
                bufferedWriter.write(String.valueOf(contaCliente.getSaldoConta()));
            }
        }
        bufferedWriter.close();
    }


    public void escreverArquivoTodo(List<ContaCliente> contaClienteList) throws IOException {
        FileWriter arquivo = new FileWriter("relatorioTransacao");

        BufferedWriter bufferedWriter = new BufferedWriter(arquivo);
        bufferedWriter.write("CPF;NOME;SALDO");
        for (int j = 0; j < contaClienteList.size(); j++) {
            bufferedWriter.newLine();
            bufferedWriter.write(j + " - " + contaClienteList.get(j).getIdCliente().getNome() + ";");
        }

        bufferedWriter.close();
    }

    private void contarLinha() {
        try {
            String file = ("./relatorioTransacao25-7-119.txt");
            BufferedReader readFile = Files.newBufferedReader(Paths.get(file));
            System.out.println(readFile.lines().toArray().length);
            readFile.close();
        } catch (IOException e) {
            System.out.println("Erro ao abrir o arquivo: " + e.getMessage());
        }
    }

    private boolean verificarSaldo(ContaCliente contaCliente) {
        return contaCliente.getSaldoConta() > 0;
    }

    public void saveTransaction(ContaCliente contaCliente) {
        contaClienteRepository.save(contaCliente);
    }

    public void automatizarTarefa(Date dataAtualizacao) {
        Timer timer = new Timer();
        final long SEGUNDOS = (2000 * 10);

//        Calendar c = Calendar.getInstance();
//        c.setTime(dataAtualizacao);
//        c.add(Calendar.DATE, +1);
//        dataAtualizacao = c.getTime();
//        Date finalDataAtualizacao = dataAtualizacao;

        TimerTask tarefa = new TimerTask() {
            @Override
            public void run() {
                try {
                    escreverArquivo(pesquisarPorData(dataAtualizacao), dataAtualizacao);
                    System.out.println(dataAtualizacao);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.scheduleAtFixedRate(tarefa, 0, SEGUNDOS);
    }


    Timer timer = new Timer();
    final long MINUTOS = (5000 * 60);

    @Scheduled(fixedDelay = MINUTOS)
    public void enviarPeloKafka(){

        String bootstrapServers = "127.0.0.1:9092";
        // create Producer properties
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // create the producer
        KafkaProducer<Cliente, String> producer = new KafkaProducer<>(properties);

        // create a producer record
        ProducerRecord<Cliente, String> record;

        List<Cliente> contaClienteList = clienteRepository.findAll();

        for (Cliente cliente : contaClienteList) {
            record = new ProducerRecord<>("ccs_mark", cliente.toString());
            producer.send(record);
        }

        System.out.println("-------------------------------------------------------");
//        record = new ProducerRecord<>("two_topic", cliente.toString());

        // send data - asynchronous
//        producer.send(record);

        // flush data
        producer.flush();
        // flush and close producer
        producer.close();
    }
}