package com.br.ccs.mark.version.on.ccsmark.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ClienteKafka {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idCliente;

    private String nome;

    private String endereco;

    private Integer telefone;

    private String email;

    private Integer cpf;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date dataAtualizacao;


    public ClienteKafka(Integer idCliente, String nome, String endereco, Integer telefone, String email, Integer cpf, Date dataAtualizacao) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
        this.dataAtualizacao = dataAtualizacao;
    }

    public ClienteKafka() {

    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public Integer getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    public Integer getCpf() {
        return cpf;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    @Override
    public String toString() {
        return "ClienteKafka{" +
                "idCliente=" + idCliente +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", telefone=" + telefone +
                ", email='" + email + '\'' +
                ", cpf=" + cpf +
                '}';
    }
}
