## language: pt
#@ContaClienteTeste
##Funcionalidade: Verificar se o cliente é passivel de reporte ao CCS (cliente ativo).
##
##  Contexto: Cria todas as contas e associa ao banco
##    Dado que as contas sao do "Banco do Brasil"
##      | idConta | idCliente | saldoConta | dataAtualizacao |
##      | 1       | 10        | 10.5       | 2019-06-05      |
##
##  Cenario: Verifica o total de contas com saldo
##    Dado o calculo do total de contas criadas
##    Entao o total de contas e 1
##
##  Cenario: Verifica o total de dinheiro no banco
##    Dado o calculo do total de dinheiro
##    Entao o total de dinheiro no banco e 10.5
#Funcionalidade: a testar a busca de clientes
#   Exemplos:
#      | idConta | idCliente | saldoConta | dataAtualizacao |
#      | 1       | 10        | 10.5       | 2019-06-05      |
#  Cenario:
#    Dado que tenha 1 conta cadastrada
#    Quando o cliente solicitar total de contas com saldo
#    Entao o total de contas e 1