# language: pt
@ContaTeste
Funcionalidade: Testar a persistencia no banco
O sistema deve persistir o cliente recebido do banco

Esquema do Cenario: Persistir o cliente recebido pelo servico de busca no banco de dados
Dado que recebemos um cliente de id <idCliente> nome "<nome>" endereco "<endereco>" telefone <telefone> email "<email>" cpf <cpf> dataAtualizacao <dataAtualizacao>
E que inseriu o cliente no banco de dados
Entao o registro deve ser inserido no banco de dados.

Exemplos:
      | idCliente   | nome	    | endereco		    | telefone	| email			    | cpf		| dataAtualizacao	|
      | 1           | rafael	| rua dos cravos	| 123		| rafael@rafael.com	| 77777		| 2019-08-01		|

Esquema do Cenario: Buscar o cliente cadastrado no banco de dados
Dado que temos o cliente de id <idCliente> nome "<nome>" endereco "<endereco>" telefone <telefone> email "<email>" cpf <cpf> dataAtualizacao <dataAtualizacao>
Quando o servico buscar o cliente pelo id
Entao o cliente com id 1 deve ser retornado

Exemplos:
      | idCliente   | nome	    | endereco		    | telefone	| email			    | cpf		| dataAtualizacao	|
      | 1           | rafael	| rua dos cravos	| 123		| rafael@rafael.com	| 77777		| 2019-08-01		|

Esquema do Cenario: Buscar o cliente nao cadastrado no banco de dados
Dado que temos o cliente de id <idCliente> nome "<nome>" endereco "<endereco>" telefone <telefone> email "<email>" cpf <cpf> dataAtualizacao <dataAtualizacao>
Quando o servico buscar o cliente pelo id
Entao o cliente com id 2 deve ser retornado null

Exemplos:
      | idCliente   | nome	    | endereco		    | telefone	| email			    | cpf		| dataAtualizacao	|
      | 1           | rafael	| rua dos cravos	| 123		| rafael@rafael.com	| 77777		| 2019-08-01		|


