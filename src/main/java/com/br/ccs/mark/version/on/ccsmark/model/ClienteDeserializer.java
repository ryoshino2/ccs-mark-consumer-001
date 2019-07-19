package com.br.ccs.mark.version.on.ccsmark.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class ClienteDeserializer implements Deserializer<Cliente> {


    @Override
    public void configure(Map map, boolean b) {

    }

    @Override
    public Cliente deserialize(String arg0, byte[] arg1) {
        ObjectMapper mapper = new ObjectMapper();
        Cliente cliente = null;
        try {
            cliente = mapper.readValue(arg1, Cliente.class);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return cliente;
    }

    @Override
    public void close() {

    }
}
