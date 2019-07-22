package com.br.ccs.mark.version.on.ccsmark.controller;

import com.br.ccs.mark.version.on.ccsmark.model.ClienteDeserializer;
import com.br.ccs.mark.version.on.ccsmark.model.ClienteKafka;
import com.br.ccs.mark.version.on.ccsmark.repository.ClienteRepository;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

@RestController
public class CcsController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    @GetMapping("/consumirPeloKafka")
    public void ConsumirPeloKafka() throws IOException {

        String bootstrapServers = "127.0.0.1:9092";
        String groupId = "my-fourth-application";
        String topic = "ccs_mark";

        // create consumer configs
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, ClienteDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // create consumer
        KafkaConsumer<String, ClienteKafka> consumer = new KafkaConsumer<>(properties);

        // subscribe consumer to our topic(s)
        consumer.subscribe(Arrays.asList(topic));

        // poll for new data
        FileWriter arquivo = new FileWriter("relatorioTransacao_Kafka");
        BufferedWriter bufferedWriter = new BufferedWriter(arquivo);
        bufferedWriter.write("CPF;NOME");
        ConsumerRecords<String, ClienteKafka> records;

        records = consumer.poll(Duration.ofMinutes(5)); // new in Kafka 2.0.0
        for (ConsumerRecord<String, ClienteKafka> record : records) {
            bufferedWriter.flush();
            System.out.println("Value: " + record.value());
            bufferedWriter.newLine();
            bufferedWriter.write(record.value().getIdCliente() + ", " + record.value().getNome() + ";");
            clienteRepository.save(record.value());
        }
        bufferedWriter.close();
    }

}