package com.br.ccs.mark.version.on.ccsmark.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    public ClienteKafka(Integer idCliente, String nome, String endereco, Integer telefone, String email, Integer cpf) {
        this.idCliente = idCliente;
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
        this.email = email;
        this.cpf = cpf;
    }

    public ClienteKafka(){

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
