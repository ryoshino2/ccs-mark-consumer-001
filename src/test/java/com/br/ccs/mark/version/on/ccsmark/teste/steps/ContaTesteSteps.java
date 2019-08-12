package com.br.ccs.mark.version.on.ccsmark.teste.steps;

import com.br.ccs.mark.version.on.ccsmark.CcsmarkApplication;
import com.br.ccs.mark.version.on.ccsmark.model.ClienteKafka;
import com.br.ccs.mark.version.on.ccsmark.repository.ClienteKafkaRepository;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.E;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@ContextConfiguration(classes = CcsmarkApplication.class)
public class ContaTesteSteps {

    private ClienteKafka clienteKafka;

    @Autowired
    private ClienteKafkaRepository clienteKafkaRepository;


    @Dado("^que recebemos um cliente de id (\\d+) nome \"([^\"]*)\" endereco \"([^\"]*)\" telefone (\\d+) email \"([^\"]*)\" cpf (\\d+) dataAtualizacao (\\d+)-(\\d+)-(\\d+)$")
    public void que_recebemos_um_cliente_de_id_nome_endereco_telefone_email_cpf_dataAtualizacao(Integer idCliente, String nome, String endereco, Integer telefone, String email, Integer cpf, Integer arg6, Integer arg7, Integer arg8) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        Date data = new Date();
        clienteKafka = new ClienteKafka(idCliente, nome, endereco, telefone, email, cpf, data);
    }

    @E("^que inseriu o cliente no banco de dados$")
    public void que_inseriu_o_cliente_no_banco_de_dados() throws Throwable {
        clienteKafkaRepository.save(clienteKafka);
    }

    @Entao("^o registro deve ser inserido no banco de dados\\.$")
    public void o_registro_deve_ser_inserido_no_banco_de_dados() throws Throwable {
        assertEquals(1, clienteKafkaRepository.findAll().size());
    }

    @Dado("^que temos o cliente de id (\\d+) nome \"([^\"]*)\" endereco \"([^\"]*)\" telefone (\\d+) email \"([^\"]*)\" cpf (\\d+) dataAtualizacao (\\d+)-(\\d+)-(\\d+)$")
    public void que_temos_o_cliente_de_id_nome_endereco_telefone_email_cpf_dataAtualizacao(int idCliente, String nome, String endereco, int telefone, String email, int cpf, int arg7, int arg8, int arg9) throws Throwable {
        Date data = new Date();
        clienteKafka = new ClienteKafka(idCliente, nome, endereco, telefone, email, cpf, data);
        clienteKafkaRepository.save(clienteKafka);

    }

    @Quando("^o servico buscar o cliente pelo id$")
    public void o_servico_buscar_o_cliente_pelo_id() throws Throwable {
        // Write code here that turns the phrase above into concrete actions

    }

    @Entao("^o cliente com id (\\d+) deve ser retornado$")
    public void o_cliente_com_id_deve_ser_retornado(int arg1) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        assertEquals(clienteKafka.getNome(), clienteKafkaRepository.findByIdCliente(arg1).getNome());
    }

    @Entao("^o cliente com id (\\d+) deve ser retornado null$")
    public void o_cliente_com_id_deve_ser_retornado_null(int arg1) throws Throwable {
        assertNull(clienteKafkaRepository.findByIdCliente(arg1));
    }
}
