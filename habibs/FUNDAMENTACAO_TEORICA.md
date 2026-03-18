# Fundamentação Teórica

Este documento apresenta conceitos fundamentais para o desenvolvimento de software que servirão como base teórica para o desenvolvimento deste projeto — um clone do sistema interno do Habibs. Este projeto é um trabalho auxiliar para a disciplina de **Qualidade de Software**, e os tópicos aqui abordados contextualizam as decisões técnicas e arquiteturais adotadas ao longo do desenvolvimento.

---

## CRUD

CRUD é um acrônimo que representa as quatro operações básicas utilizadas em sistemas que manipulam dados persistentes:

| Operação   | Descrição                                      | Verbo HTTP correspondente |
|------------|-------------------------------------------------|---------------------------|
| **C**reate | Criação de um novo registro                     | POST                      |
| **R**ead   | Leitura/consulta de registros existentes        | GET                       |
| **U**pdate | Atualização de um registro existente            | PUT / PATCH               |
| **D**elete | Remoção de um registro                          | DELETE                    |

Praticamente todo sistema que interage com um banco de dados implementa, em algum nível, essas quatro operações. Um sistema de gestão de pedidos, por exemplo, precisa **criar** novos pedidos, **consultar** pedidos existentes, **atualizar** o status de um pedido e **remover** pedidos cancelados. O CRUD é, portanto, a espinha dorsal de qualquer aplicação que persiste dados.

---

## API

Uma **API** (Application Programming Interface) é uma interface que permite a comunicação entre sistemas de forma que **não importa, para nenhum dos dois lados, como essa comunicação é feita internamente**. Ela define um contrato: quem chama sabe o que pedir e o que esperar de volta, sem precisar conhecer os detalhes de implementação do outro lado.

### Antes das APIs

Antes da popularização das APIs, a comunicação entre sistemas era feita de formas bem mais primitivas:

- **Troca de arquivos**: o sistema A escrevia um arquivo, jogava em um servidor FTP, e o sistema B importava esse arquivo. Esse modelo foi (e ainda é) muito utilizado em bancos e instituições financeiras.
- **Acesso direto**: o sistema A acessava o sistema B diretamente — por exemplo, o front-end acessando o banco de dados sem nenhuma camada intermediária.
- **RPC (Remote Procedure Call)**: o sistema A invocava métodos que estavam rodando no sistema B, como se fossem chamadas locais.

Em graus diferentes, todos esses métodos traziam **forte acoplamento** entre os sistemas e dificuldades de **padronização**, **tratamento de erros** e **versionamento**. Alguns, como a troca de arquivos via FTP, também introduziam lentidão pelo caráter batch do processamento. Outros, como o acesso direto ao banco, traziam riscos sérios de **segurança** — expondo a estrutura interna dos dados a qualquer camada que precisasse consultá-los.

### SOAP: padronização da comunicação via web

Ainda antes das APIs web, houve tentativas de padronizar a comunicação entre sistemas distribuídos, como **CORBA** (1991) e **DCOM** (1993). No contexto web, o **XML-RPC** (1998) foi um dos primeiros protocolos a usar XML sobre HTTP para chamadas remotas de forma padronizada.

O **SOAP** (Simple Object Access Protocol), criado em 1999 e fortemente adotado no início dos anos 2000, evoluiu sobre essas ideias. Ele é baseado em arquivos **XML** e define contratos rígidos (WSDL) para a troca de mensagens. Apesar de ser extremamente **complexo, pesado e difícil de ler**, o SOAP se mostra bastante **seguro** justamente por ter regras rígidas e bem definidas — o que explica seu uso até hoje em ambientes que exigem alto nível de confiabilidade.

---

## API REST

**REST** é a sigla para **Representational State Transfer**. Uma API REST é um tipo de API que permite a comunicação entre sistemas utilizando o **protocolo HTTP**.

### HTTP

**HTTP** (HyperText Transfer Protocol) é o protocolo que dita a comunicação entre **cliente e servidor** na web.

#### Estrutura de uma requisição HTTP

| Componente         | Descrição                                                        |
|--------------------|------------------------------------------------------------------|
| **Verbo (método)** | Define o escopo da requisição: `GET`, `POST`, `PUT`, `DELETE`    |
| **Header**         | Metadados da requisição e informações de autenticação            |
| **URL / Endpoint** | Endereço do recurso que está sendo acessado                      |
| **Body** (opcional)| Dados enviados na requisição (comum em `POST` e `PUT`)          |

#### Estrutura de uma resposta HTTP

| Componente         | Descrição                                                                 |
|--------------------|---------------------------------------------------------------------------|
| **Status Code**    | Informa o resultado da requisição: `2XX` (sucesso), `4XX` (erro do cliente), `5XX` (erro do servidor) |
| **Body** (opcional)| Dados retornados na resposta                                              |

### JSON

**JSON** (JavaScript Object Notation) é o formato mais utilizado nas requisições e respostas HTTP. É **muito mais leve que o XML**, fácil de ler por humanos e simples de serializar/deserializar por máquinas. Exemplo:

```json
{
  "id": 1,
  "nome": "Bib'sfiha de Carne",
  "preco": 3.50
}
```

### Stateless vs Stateful

A API REST é **stateless**, ou seja, **não guarda estados de sessão** entre requisições. Cada requisição carrega consigo todas as informações necessárias para ser processada.

Em contrapartida, um sistema **stateful** — como o modelo baseado em **sessões do servidor** — mantém estado entre requisições. No modelo tradicional de sessão, o servidor cria um registro em memória a cada login e armazena o ID dessa sessão em um cookie no cliente. Isso é perfeitamente válido e amplamente utilizado em aplicações web convencionais.

O ponto de atenção surge na **escalabilidade horizontal**: ao distribuir a aplicação em múltiplas instâncias, as sessões precisam ser compartilhadas entre elas — caso contrário, um usuário autenticado em uma instância seria tratado como não autenticado em outra. Isso é resolvível com técnicas como *sticky sessions* ou um armazenamento de sessão centralizado (ex: Redis), mas adiciona complexidade à infraestrutura. APIs REST, por serem stateless, evitam essa preocupação por design.

### Comparação: REST vs SOAP vs GraphQL

| Característica       | REST                            | SOAP                                  | GraphQL                                  |
|----------------------|---------------------------------|---------------------------------------|------------------------------------------|
| **Formato**          | JSON (geralmente)               | XML (obrigatório)                     | JSON                                     |
| **Protocolo**        | HTTP                            | HTTP, SMTP, TCP, etc.                 | HTTP                                     |
| **Contrato**         | Flexível (OpenAPI opcional)     | Rígido (WSDL obrigatório)            | Schema fortemente tipado                 |
| **Flexibilidade**    | Endpoints fixos por recurso     | Operações definidas no WSDL           | Cliente define exatamente o que quer     |
| **Complexidade**     | Baixa                           | Alta                                  | Média                                    |
| **Uso principal**    | Padrão de mercado geral         | Governo, bancos, sistemas legados     | Sistemas com muitos relacionamentos      |

**REST** é hoje o **padrão de mercado** para a maioria das aplicações. É simples, leve e amplamente suportado.

**SOAP** ainda é utilizado em sistemas que precisam de um **contrato formal de transferência** para garantir segurança e conformidade — como sistemas de **governo, bancos e instituições financeiras**.

**GraphQL** é utilizado em sistemas com **muitos relacionamentos, telas com muitos dados diferentes e necessidade de otimização de tráfego**. O GraphQL foi criado pelo **Facebook/Meta** justamente para resolver esse problema: uma página do feed precisava carregar publicações, comentários, reações, dados do autor, sugestões de amigos e muito mais em uma única tela. Com o GraphQL, o cliente define exatamente os dados que precisa em uma única requisição, evitando **over-fetching** (receber dados desnecessários) e **under-fetching** (precisar de múltiplas requisições para montar uma tela).

---

## ORM

Existem algumas formas de o backend se comunicar com um banco de dados via código. Uma delas é o uso de **cursores**, que permitem inserir **SQL cru** (raw SQL) diretamente no código para obter respostas do banco. O problema dessa abordagem é que:

- Requer **alta troca de contexto** entre a linguagem de programação e SQL;
- Pode gerar **erros de sintaxe** ao trocar de banco de dados (ex: de MySQL para PostgreSQL);
- Traz **vulnerabilidade a SQL Injection**, uma das falhas de segurança mais comuns em aplicações web.

Para resolver esses problemas, foram criadas as **ORMs** (Object-Relational Mapping). Uma ORM é uma **camada de abstração** que mapeia objetos da aplicação para tabelas do banco de dados, delegando ao sistema a responsabilidade de gerar e gerenciar as queries SQL. Com isso, o desenvolvedor trabalha com objetos e métodos da própria linguagem, sem precisar escrever SQL manualmente.

Exemplos de ORMs populares:

| Linguagem  | ORM                                                                 |
|------------|---------------------------------------------------------------------|
| Java       | Hibernate (implementação) / JPA (especificação que o Hibernate segue) |
| Python     | SQLAlchemy / Django ORM                                             |
| JavaScript | Sequelize / Prisma                                                  |
| C#         | Entity Framework                                                    |

> **Nota:** Em Java, **JPA** (Jakarta Persistence API) é uma *especificação* — um conjunto de interfaces e regras que define como uma ORM deve se comportar. **Hibernate** é a *implementação* mais popular dessa especificação. Na prática, você escreve código contra a interface JPA e o Hibernate faz o trabalho por baixo.

---

## Spring

**Spring** é um framework para desenvolvimento de aplicações Java que, como qualquer outro framework, gerencia dependências de objetos, configura a aplicação e integra componentes, facilitando o desenvolvimento.

### O problema que o Spring resolve

O problema central que o Spring resolve é o **acoplamento excessivo** e a **complexidade no gerenciamento de objetos**.

Quando você cria e instancia uma classe dentro de outra, você atribui a ela a responsabilidade de existência dessa classe, **acoplando-a** à outra. Dessa forma, fica difícil **escalar**, **testar** a aplicação e **trocar implementações**.

```java
// Exemplo de acoplamento forte
public class PedidoService {
    // PedidoService é RESPONSÁVEL por criar PedidoRepository
    private PedidoRepository repository = new PedidoRepository();
}
```

### Spring Container, IoC e DI

O que o Spring faz é **gerenciar e controlar as instâncias de objetos** da sua aplicação por meio de uma estrutura chamada **Spring Container**. Ele define o quanto cada objeto vai viver, o escopo de cada um, e onde serão utilizados.

Os dois conceitos fundamentais que o container aplica na aplicação são:

- **Inversion of Control (IoC)**: consiste em **delegar ao sistema** (e não ao desenvolvedor) a responsabilidade de gerenciamento do ciclo de vida dos objetos.
- **Dependency Injection (DI)**: controla **onde esses objetos serão inseridos** no código. Em vez da classe A instanciar um objeto da classe B, a classe A vai **depender de uma abstração**, e alguém externo vai **injetar** o B nela. Em vez de você criar suas dependências, elas serão **fornecidas para você**.

```java
// Exemplo com Dependency Injection
public class PedidoService {
    private final PedidoRepository repository;

    // O Spring injeta a dependência automaticamente
    public PedidoService(PedidoRepository repository) {
        this.repository = repository;
    }
}
```

### Spring Boot

O **Spring Framework** é a ferramenta que fornece todas essas facilitações, mas o que utilizaremos neste projeto é o **Spring Boot**.

O Spring normalmente requer **configurações excessivas** para funcionar (arquivos XML, anotações manuais, configuração de servidor, etc.). O Spring Boot existe para **automatizar essas configurações** com **convenções pré-estabelecidas**. Ele traz também:

- **Servidor embutido** (Tomcat, Jetty);
- **Dependências starter** que agrupam tudo o que a aplicação precisa;
- **Auto-configuração** inteligente baseada nas dependências presentes no projeto.

O Spring Boot é, essencialmente, uma **camada por cima do Spring** que elimina a complexidade de configuração e permite que o desenvolvedor foque no que realmente importa: a lógica da aplicação.

---

## Arquitetura em Camadas

> **Observação:** este **não** é o modelo que iremos utilizar no projeto, mas é necessário compreendê-lo para entender a Clean Architecture.

A **Arquitetura em Camadas** é um modelo de organização de sistema onde dividimos a aplicação em **níveis**, cada um com sua própria responsabilidade. As **dependências fluem sempre para baixo**: a camada superior conhece e chama a camada inferior, mas o contrário nunca ocorre. O que retorna para cima são os resultados das operações, não chamadas diretas.

### As três camadas principais

```
┌─────────────────────────────┐
│         Controller          │  ← Recebe requisições, valida entrada e retorna resposta
├─────────────────────────────┤
│          Service            │  ← Processa lógica e aplica regras de negócio
├─────────────────────────────┤
│         Repository          │  ← Camada de persistência, acesso ao banco de dados
└─────────────────────────────┘
```

- **Controller**: responsável por receber as requisições externas, validar a entrada de dados e retornar a resposta ao cliente.
- **Service**: camada onde toda a lógica é processada e as regras de negócio são aplicadas.
- **Repository**: camada de persistência, responsável pelo acesso ao banco de dados.

### Vantagens

Esse modelo **separa responsabilidades** de forma clara entre as camadas, o que **facilita testes e manutenção** — é possível, por exemplo, trocar o banco de dados sem tocar no Service, ou reescrever a lógica de negócio sem mexer no Controller. É uma boa escolha para sistemas com regras de negócio **não tão complexas**.

### Limitações

O problema da Arquitetura em Camadas é que, querendo ou não, **a essência da aplicação ainda depende da infraestrutura**. A regra de negócio acaba dependendo do framework ou do banco utilizado — e ela **não deveria**.

O **domínio do negócio deve existir de forma independente da sua implementação**. Na arquitetura em camadas, essa separação não é garantida, o que pode tornar o sistema:

- **Desprotegido contra dependências de infraestrutura** — uma mudança de framework ou banco pode impactar diretamente a lógica de negócio;
- **Suscetível a crescimento desordenado** — especialmente quando o domínio é muito complexo, a falta de fronteiras claras entre "o que é regra de negócio" e "o que é infraestrutura" pode levar a um código difícil de manter.

É justamente para resolver essas limitações que surgem arquiteturas como a **Clean Architecture**, que será o modelo adotado neste projeto.
