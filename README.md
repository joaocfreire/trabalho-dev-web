# Trabalho de Desenvolvimento Web - Sistema de Gestão Acadêmica

Este projeto consiste em uma API RESTful desenvolvida em Spring Boot integrada a uma interface SPA em React (Vite) para a gestão de alunos, professores, disciplinas, turmas e inscrições. 

---

## Foco do Projeto: Qualidade e Cobertura de Testes

O diferencial técnico deste projeto é a sua **robusta pirâmide de testes automatizados**, garantindo a integridade do ecossistema de ponta a ponta. O desenvolvimento seguiu as melhores práticas de isolamento e validação de camadas:

1. **Testes Unitários de Serviço:** Validação isolada de todas as regras de negócio usando **JUnit 5** e **Mockito** puros, eliminando efeitos colaterais de infraestrutura.
2. **Testes de Roteamento e API:** Validação de endpoints, verbos HTTP, mapeamento JSON e payloads de validação (`@Valid` / `422 Unprocessable Entity`) usando **@WebMvcTest** e **MockMvc**.
3. **Testes de Integração de Banco de Dados:** Validação real de persistência e integridade referencial utilizando o banco de dados em memória **H2** (com suporte configurado para transições para ambientes produtivos/efêmeros).
4. **Testes do Cliente e End-to-End:** Testes de interface automatizados utilizando o framework **Playwright (TypeScript)** para simular o comportamento de usuários reais em fluxos críticos (Autenticação, Cadastro de Alunos e Matrículas).

---

## Pré-requisitos para Execução

Antes de começar, certifique-se de ter instalado em sua máquina:
- [Docker](https://docs.docker.com/get-docker/) e [Docker Compose](https://docs.docker.com/compose/install/)
- [Node.js](https://nodejs.org/) (versão 18 ou superior)

---

## Como Executar o Projeto

A infraestrutura foi desenhada para subir o ecossistema completo (Banco de Dados MySQL + Backend Java) usando Docker de forma unificada.

### 1. Clonar o repositório e acessar a pasta raiz:
```
git clone <url-do-repositorio>
cd trabalho-dev-web
```

### 2. Subir o ambiente completo (Banco e Backend):

Na raiz do projeto (onde está o arquivo docker-compose.yaml), execute:


```
docker compose up --build
```

O backend subirá na porta 8080 e o MySQL na porta 3307 interna. O SpringBoot possui um DataSeeder ativo para povoar o banco automaticamente com dados iniciais de teste.


### 3. Subir a Interface do Frontend:

Abra um novo terminal, acesse a pasta frontend/ e inicie o servidor de desenvolvimento:


```
cd frontend
npm install
npm run dev
```

O frontend estará disponível em http://localhost:5173.

---

##  Como Executar a Base de Testes

Dado o foco em testes do projeto, a execução é separada por camadas:

### 1. Rodando Testes do Backend (Unitários + Integração)

Acesse a pasta backend/ e utilize o wrapper do Maven.

```
cd backend
./mvnw test
```

### 2.1. Rodando Testes de Interface (Playwright)

Os testes de interface rodam a partir do ambiente Node na raiz do projeto.

Garanta que o backend está ativo e rodando (localhost:8080).

Abra um terminal na raiz do projeto e execute:

```
npm install
npx playwright install
npx playwright test
```

### 2.2. Modo Visual

Para assistir ao Playwright abrindo o navegador sozinho, preenchendo os formulários e navegando em tempo real, rode:

```
npx playwright test --ui
```
