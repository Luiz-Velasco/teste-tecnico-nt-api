# Projeto QA API - Dog API (Java)

Projeto de automacao de testes de API em Java para avaliacao tecnica de QA, cobrindo validacao funcional, contrato de resposta e requisitos nao funcionais basicos.

## Stack

- Java 17+
- Maven Wrapper (mvnw / mvnw.cmd)
- JUnit 5
- Rest Assured
- AssertJ

## Endpoints testados

- GET /breeds/list/all
- GET /breed/{breed}/images
- GET /breeds/image/random

## Estrutura do projeto

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

## Como executar

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

## CI/CD

Pipeline em .github/workflows/java-api-tests.yml com:

- execucao em push e pull request
- matriz Linux/Windows/macOS
- JDK 17 e 21
- execucao via Maven Wrapper
- upload de relatorios Surefire
- resumo de resultados no Job Summary
