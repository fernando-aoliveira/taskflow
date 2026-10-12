# TaskFlow

> API REST para gerenciamento de tarefas, desenvolvida em Java com
> Spring Boot como projeto de estudo do módulo **Design Patterns com
> Java: Dos Clássicos (GoF) ao Spring Framework**.

![Java](https://img.shields.io/badge/Java-25-orange) ![Spring
Boot](https://img.shields.io/badge/Spring%20Boot-4.1.1-6DB33F)
![Maven](https://img.shields.io/badge/Maven-build-C71A36)
![Tests](https://img.shields.io/badge/Testes-9%20aprovados-brightgreen)

## Sobre o projeto

O **TaskFlow** é um projeto educacional criado durante o curso **Itaú -
Java com Inteligência Artificial**, realizado pela plataforma
[DIO](https://www.dio.me/).

O objetivo é praticar a construção de uma API REST com Java e Spring
Boot e aplicar conceitos de organização de código e padrões de projeto.
O foco deste projeto é o padrão comportamental **Strategy**, utilizado
para permitir diferentes formas de ordenação das tarefas sem concentrar
todas as regras em um único bloco de código.

O TaskFlow tem um escopo intencionalmente enxuto: é um projeto de
aprendizagem e preparação para desafios maiores ao longo do curso.

## Funcionalidades

-   Cadastrar tarefas.
-   Listar todas as tarefas.
-   Consultar uma tarefa pelo identificador.
-   Ordenar tarefas por prioridade.
-   Ordenar tarefas alfabeticamente pelo título, ignorando diferenças
    entre maiúsculas e minúsculas.
-   Validar dados de entrada.
-   Documentar e explorar a API pelo Swagger UI.
-   Executar testes automatizados com JUnit e Mockito.

## Tecnologias utilizadas

-   **Java 25**
-   **Spring Boot 4.1.1**
-   **Spring Web MVC** --- criação dos endpoints REST.
-   **Spring Data JPA** --- acesso e persistência de dados.
-   **Bean Validation** --- validação dos campos.
-   **H2 Database** --- banco de dados em memória para desenvolvimento.
-   **Springdoc OpenAPI / Swagger UI** --- documentação interativa da
    API.
-   **Maven** --- gerenciamento de dependências e execução do projeto.
-   **JUnit 5** --- testes automatizados.
-   **Mockito** --- simulação de dependências nos testes.
-   **IntelliJ IDEA** --- ambiente de desenvolvimento utilizado.

## Design Pattern aplicado: Strategy

O padrão **Strategy** permite definir diferentes algoritmos,
encapsulá-los em classes separadas e selecionar qual utilizar conforme a
necessidade.

No TaskFlow, as estratégias de ordenação implementam a interface
`OrdenacaoTarefaStrategy`:

-   `OrdenarPorPrioridade` --- organiza as tarefas da prioridade mais
    alta para a mais baixa.
-   `OrdenarPorTitulo` --- organiza os títulos em ordem alfabética, sem
    diferenciar maiúsculas de minúsculas.

O `TarefaService` seleciona a estratégia de acordo com o parâmetro
recebido. Dessa forma, cada algoritmo permanece separado, facilitando a
manutenção e a inclusão de novas estratégias no futuro.

## Modelo de tarefa

Uma tarefa contém os seguintes campos principais:

  -----------------------------------------------------------------------
  Campo                               Descrição
  ----------------------------------- -----------------------------------
  `id`                                Identificador gerado pela
                                      persistência.

  `titulo`                            Título da tarefa; obrigatório e
                                      limitado a 100 caracteres.

  `descricao`                         Descrição opcional, limitada a 500
                                      caracteres.

  `prioridade`                        `BAIXA`, `MEDIA` ou `ALTA`.

  `status`                            `PENDENTE`, `EM_ANDAMENTO` ou
                                      `CONCLUIDA`.
  -----------------------------------------------------------------------

## Endpoints disponíveis

A API utiliza o prefixo `/api/tarefas`.

  -------------------------------------------------------------------------------------
  Método                  Endpoint                              Descrição
  ----------------------- ------------------------------------- -----------------------
  `POST`                  `/api/tarefas`                        Cadastra uma tarefa.

  `GET`                   `/api/tarefas`                        Lista todas as tarefas.

  `GET`                   `/api/tarefas?ordenacao=prioridade`   Lista tarefas ordenadas
                                                                por prioridade.

  `GET`                   `/api/tarefas?ordenacao=titulo`       Lista tarefas ordenadas
                                                                pelo título.

  `GET`                   `/api/tarefas/{id}`                   Busca uma tarefa pelo
                                                                ID.
  -------------------------------------------------------------------------------------

Critérios de ordenação aceitos: `prioridade` e `titulo`. Um critério
inválido gera uma resposta de erro `400 Bad Request`. Uma busca por ID
inexistente gera `404 Not Found`.

### Exemplo de cadastro

``` json
{
  "titulo": "Estudar Design Patterns",
  "descricao": "Praticar o padrão Strategy com Java e Spring Boot",
  "prioridade": "ALTA",
  "status": "PENDENTE"
}
```

Envie esse objeto no corpo de uma requisição `POST` para `/api/tarefas`,
com o cabeçalho `Content-Type: application/json`.

## Como executar o projeto

### Pré-requisitos

-   JDK 25 instalado.
-   Maven instalado ou utilização do Maven Wrapper, caso esteja
    disponível no repositório.
-   Git, para clonar o projeto.

### 1. Clone o repositório

``` bash
git clone https://github.com/SEU-USUARIO/taskflow.git
cd taskflow
```

> Substitua `SEU-USUARIO` pelo seu usuário do GitHub e ajuste o endereço
> caso o nome do repositório seja diferente.

### 2. Execute a aplicação

Se o Maven estiver instalado:

``` bash
mvn spring-boot:run
```

Ou, se o projeto tiver Maven Wrapper:

**Windows**

``` powershell
.\mvnw.cmd spring-boot:run
```

**Linux/macOS**

``` bash
./mvnw spring-boot:run
```

### 3. Acesse a documentação interativa

Com a aplicação em execução, acesse:

-   Swagger UI: `http://localhost:8080/swagger-ui/index.html`
-   H2 Console: `http://localhost:8080/h2-console`

A configuração atual do H2 utiliza um banco em memória. Por isso, os
dados são reiniciados quando a aplicação é encerrada.

## Testes automatizados

Os testes foram desenvolvidos com **JUnit 5** e **Mockito**. Eles
verificam as estratégias de ordenação e os principais comportamentos do
serviço, sem precisar acessar o banco de dados nos testes unitários do
serviço.

Cenários cobertos:

-   Ordenação por prioridade.
-   Ordenação alfabética por título.
-   Criação de tarefa por meio do serviço.
-   Listagem de tarefas.
-   Busca de tarefa inexistente e exceção `404`.
-   Seleção da estratégia de ordenação por título.
-   Seleção da estratégia de ordenação por prioridade.
-   Critério de ordenação inválido e exceção `400`.
-   Inicialização do contexto da aplicação Spring.

**Última execução conjunta registrada durante o desenvolvimento: 9
testes aprovados.**

Para executar os testes com Maven:

``` bash
mvn test
```

Ou pelo IntelliJ IDEA, executando os testes na pasta `src/test/java`.

## Estrutura do código

A organização principal do projeto segue a separação por
responsabilidades:

``` text
src/
├── main/
│   ├── java/br/com/taskflow/taskflow/
│   │   ├── controller/   # Endpoints REST
│   │   ├── model/        # Entidade e enums
│   │   ├── repository/   # Persistência com Spring Data JPA
│   │   ├── service/      # Regras e coordenação das operações
│   │   └── strategy/     # Interface e estratégias de ordenação
│   └── resources/
│       └── application.properties
└── test/
    └── java/br/com/taskflow/taskflow/
        ├── OrdenarPorPrioridadeTest.java
        ├── OrdenarPorTituloTest.java
        ├── TarefaServiceTest.java
        └── TaskflowApplicationTests.java
```

## Escopo e próximos aprendizados

Este projeto prioriza os fundamentos de Java, Spring Boot, APIs REST, o
padrão Strategy e testes automatizados. A proposta não é construir uma
plataforma completa de gestão de tarefas, mas manter uma base simples e
compreensível para consolidar os conceitos estudados.

## Contexto educacional

-   **Curso:** Itaú - Java com Inteligência Artificial
-   **Plataforma:** DIO
-   **Módulo:** Design Patterns com Java: Dos Clássicos (GoF) ao Spring
    Framework
-   **Projeto:** TaskFlow

------------------------------------------------------------------------

Projeto desenvolvido para fins educacionais e de aprendizado contínuo.
