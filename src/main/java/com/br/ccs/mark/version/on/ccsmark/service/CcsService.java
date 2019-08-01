package com.br.ccs.mark.version.on.ccsmark.service;


import com.br.ccs.mark.version.on.ccsmark.controller.CcsController;
import com.br.ccs.mark.version.on.ccsmark.model.ClienteKafka;
import com.br.ccs.mark.version.on.ccsmark.repository.ClienteKafkaRepository;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

@Service
@EnableScheduling
public class CcsService {

    private final long MINUTOS = (1000 * 60);

    @Autowired
    private ClienteKafkaRepository clienteKafkaRepository;

    private final CcsKafka kafkaProperties = new CcsKafka();

    @Scheduled(fixedDelay = MINUTOS)
    public void consumirPeloKafkaAssincrono() throws IOException {
        Logger logger = LoggerFactory.getLogger(CcsController.class.getName());

        String bootstrapServers = "127.0.0.1:9092";
        String groupId = "my-fourth-application";
        String topic = "ccs_mark";

        // create consumer configs
        kafkaProperties.configurationKafka().setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        kafkaProperties.configurationKafka().setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "10");
        // create consumer
        KafkaConsumer<String, ClienteKafka> consumer = new KafkaConsumer<>(kafkaProperties.configurationKafka());

        // subscribe consumer to our topic(s)
        consumer.subscribe(Arrays.asList(topic));

        // poll for new data
        FileWriter arquivo = new FileWriter("relatorioTransacao_Kafka");
        BufferedWriter bufferedWriter = new BufferedWriter(arquivo);
        bufferedWriter.write("CPF;NOME");
        ConsumerRecords<String, ClienteKafka> records;

        records = consumer.poll(Duration.ofMinutes(1)); // new in Kafka 2.0.0
        logger.info("Received: " + records.count());

            for (ConsumerRecord<String, ClienteKafka> record : records) {
                bufferedWriter.flush();
                logger.info("Value: " + record.value());
                logger.info("Valor:" + record.toString().toUpperCase());
                bufferedWriter.newLine();
                bufferedWriter.write(record.value().getIdCliente() + ", " + record.value().getNome() + ";");
                clienteKafkaRepository.save(record.value());
            }
            consumer.commitSync();

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            bufferedWriter.close();
            consumer.close();
    }

    public void consumirSincrono() throws IOException {
        Logger logger = LoggerFactory.getLogger(CcsController.class.getName());

        String topic = "ccs_mark";

        // create consumer
        KafkaConsumer<String, ClienteKafka> consumer = new KafkaConsumer<>(kafkaProperties.configurationKafka());

        // subscribe consumer to our topic(s)
        consumer.subscribe(Arrays.asList(topic));

        // poll for new data
        FileWriter arquivo = new FileWriter("relatorioTransacao_Kafka");
        BufferedWriter bufferedWriter = new BufferedWriter(arquivo);
        bufferedWriter.write("CPF;NOME");
        ConsumerRecords<String, ClienteKafka> records;

        records = consumer.poll(Duration.ofMinutes(1)); // new in Kafka 2.0.0
        for (ConsumerRecord<String, ClienteKafka> record : records) {
            bufferedWriter.flush();
            logger.info("Value: " + record.value());
            bufferedWriter.newLine();
            bufferedWriter.write(record.value().getIdCliente() + ", " + record.value().getNome() + ";");
            clienteKafkaRepository.save(record.value());

        }
        bufferedWriter.close();
        consumer.close();
    }
}
