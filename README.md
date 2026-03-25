# Projeto QA API - Dog API (Java)

Automacao de testes de API em Java para avaliacao tecnica de QA, com foco em confiabilidade de integracao, validacao de contrato de resposta e rastreabilidade de execucao em CI.

## Objetivo

Validar o comportamento dos endpoints da Dog API:

- GET /breeds/list/all
- GET /breed/{breed}/images
- GET /breeds/image/random

Base URL:

- https://dog.ceo/api

## Stack e ferramentas

- Java 17+
- Maven Wrapper (mvnw / mvnw.cmd)
- JUnit 5
- Rest Assured
- AssertJ
- GitHub Actions

## Escopo de testes

### Cobertura funcional

1. Listagem de racas
- Status code 200
- Estrutura JSON esperada
- Campo status igual a success
- Lista de racas nao vazia
- Presenca de raca de referencia

2. Imagens por raca valida
- Status code 200
- Estrutura JSON esperada
- Lista de imagens nao vazia
- URL das imagens em formato valido
- Relacao da URL com a raca consultada

3. Imagens por raca invalida (negativo)
- Status code 404
- Estrutura JSON de erro esperada
- Campo status igual a error
- Mensagem com indicativo de not found

4. Imagem aleatoria
- Status code 200
- Estrutura JSON esperada
- URL de imagem valida

### Cobertura nao funcional basica

- Tempo de resposta menor que 2 segundos
- Content-Type contendo application/json
- Corpo de resposta nao vazio

## Criterios de qualidade (quality gates)

Cada cenario deve satisfazer obrigatoriamente:

- Status code esperado
- Tempo de resposta dentro do limite
- Header Content-Type valido
- Contrato minimo de resposta JSON

Build e pipeline falham quando qualquer criterio acima nao e atendido.

## Arquitetura da automacao

Estrutura principal:

```text
.
|-- .github/
|   `-- workflows/
|       `-- java-api-tests.yml
|-- java-dog-api-tests/
|   |-- .mvn/wrapper/maven-wrapper.properties
|   |-- mvnw
|   |-- mvnw.cmd
|   |-- pom.xml
|   `-- src/test/
|       |-- java/com/dogapi/
|       |   |-- assertions/ApiAssertions.java
|       |   |-- client/DogApiClient.java
|       |   |-- config/TestConfig.java
|       |   `-- tests/DogApiTests.java
|       `-- resources/test-data.properties
`-- README.md
```

Separacao por responsabilidade:

- client: encapsula chamadas HTTP e configuracao comum de requests
- assertions: centraliza regras de validacao e contrato
- config: parametros de execucao e massa de teste
- tests: cenarios de negocio e orquestracao

## Execucao local

Na pasta java-dog-api-tests:

Windows:

```powershell
cd java-dog-api-tests
.\mvnw.cmd clean test
```

Linux/macOS:

```bash
cd java-dog-api-tests
./mvnw clean test
```

Executar apenas a classe principal:

Windows:

```powershell
.\mvnw.cmd -Dtest=DogApiTests test
```

Linux/macOS:

```bash
./mvnw -Dtest=DogApiTests test
```

## Relatorios e evidencias

Relatorios locais do Maven Surefire:

- java-dog-api-tests/target/surefire-reports

Na pipeline:

- Upload de artefatos com os resultados por sistema operacional e versao do JDK
- Job Summary com total de testes, aprovados, falhas, erros e skipped

## CI/CD

Workflow:

- .github/workflows/java-api-tests.yml

Caracteristicas:

- Gatilhos em push, pull_request e workflow_dispatch
- Execucao em matriz Linux/Windows/macOS
- JDK 17 e 21
- Execucao via Maven Wrapper
- Publicacao de artefatos de teste
- Concurrency para evitar execucoes redundantes em paralelo

## Limitacoes conhecidas

- Ambiente publico da API pode apresentar instabilidades pontuais de rede
- Nao ha mock server neste escopo tecnico (validacao ocorre contra API real)

## Evolucoes sugeridas

- Adicionar testes de schema com JSON Schema dedicado por endpoint
- Adicionar retries controlados para falhas de rede transientes
- Adicionar geracao de relatorio consolidado (Allure) em CI
- Adicionar estrategia de dados de teste por perfil (smoke, regression, negative)
