# Análise de Ativos

Este projeto é um sistema de monitoramento de ativos financeiros que verifica periodicamente as cotações de ações e envia alertas por e-mail quando os preços atingem os valores configurados para compra ou venda.


## Pré-requisitos

Antes de rodar o projeto, certifique-se de ter os seguintes itens instalados:

- **Java 11** ou superior
- **Maven** para gerenciar dependências
- Conexão com a internet para acessar a API de cotações e enviar e-mails

## Configuração

1. **Configurar o arquivo `config.properties`**  
   No diretório `src/main/resources`, edite o arquivo `config.properties` com as seguintes informações:

    ```properties
    email.to=seu_email@exemplo.com
    smtp.host=smtp.exemplo.com
    smtp.port=587
    smtp.username=seu_usuario
    smtp.password=sua_senha
    api.key=sua_chave_api(https://brapi.dev)
    ```

 ## Adicionar variáveis de ambiente (opcional)

Caso prefira, você pode usar variáveis de ambiente no lugar de valores fixos no arquivo config.properties. Por exemplo:

`smtp.password=${SMTP_PASSWORD}`

---

## Como Rodar

1. **Compilar o projeto**  
   No diretório raiz do projeto, execute o comando:

   ```sh
   mvn clean package
   ```
2. **Executar o projeto**  
   Para iniciar o monitoramento, execute:

   ```sh
   java -jar target/analise-ativo.jar <ATIVO> <VALOR_VENDA> <VALOR_COMPRA>
    ```
   Caso o sistema seja iniciado sem parâmetros, ele requisita antes de rodar:

    ```sh
   Qual a ação desejada? 
   Qual o valor de venda desejado?
   Qual o valor de compra desejado?
    ```
---

## Funcionalidades

- **Monitoramento de ativos**: Verifica periodicamente as cotações de ações usando a API [brapi.dev](https://brapi.dev).
- **Envio de alertas por e-mail**: Notifica o usuário quando o preço de compra ou venda é atingido.
- **Registro de cotações**: Salva as cotações em um arquivo CSV (`src/main/resources/cotacoes.csv`).
- **Histórico visualizado**: Gera um gráfico D3 (`src/main/resources/index.html`) para visualização gráfica do histórico de valores da cotação do ativo.
## Estrutura do Código

- **`ConfigLoader`**: Carrega as configurações do arquivo `config.properties` e resolve variáveis de ambiente.
- **`EmailService`**: Gerencia o envio de e-mails com suporte a autenticação SMTP.
- **`MonitoraAcao`**: Classe principal que realiza o monitoramento das cotações e aciona os alertas.

## Personalização

- **Intervalo de monitoramento**: O intervalo padrão é de 20 segundos. Para alterar, modifique o valor no método `start()` da classe `MonitoraAcao`:

   ```java
   timer.scheduleAtFixedRate(new TimerTask() { ... }, 0, 20000); // 20000 ms = 20 segundos
   ```

- **Formato do CSV**: o arquivo `cotacoes.csv` registra as cotações no formato `yyyy-MM-dd HH:mm:ss,ATIVO,COTACAO`

## Problemas conhecidos

- **Erro ao carregar** `config.properties`: Certifique-se de que o arquivo está no diretório `src/main/resources`

- **Erro de autenticação SMTP**: Verifique as credenciais e as configurações do servidor SMTP.

- **Erro ao acessar a API**: Certifique-se de que a chave da API está correta e que há conexão com a internet.