# Trabalho - Desenvolvimento Web

Este repositório contém um projeto de sistema acadêmico composto por um **Backend (Spring Boot)** e um **Frontend (React + Vite + TypeScript)**. Abaixo estão as instruções para configurar e executar ambas as partes do projeto em sua máquina local.

---

## Pré-requisitos

Antes de começar, certifique-se de ter instalado em sua máquina:
- [Docker](https://docs.docker.com/get-docker/) e [Docker Compose](https://docs.docker.com/compose/install/)
- [Node.js](https://nodejs.org/) (versão 18 ou superior)

---

## Como Executar o Backend (API & Banco de Dados)

1. **Configurar as Variáveis de Ambiente:**
   Abra a pasta `backend` e certifique-se de criar ou configurar o arquivo `.env` contendo a chave secreta usada para a geração dos tokens JWT:

```
   CHAVE_SECRETA=sua_chave_secreta_aqui
```

2. **Iniciar os Serviços:**
    No terminal, navegue até a raiz do projeto e execute o comando:

```
docker compose up --build
```

3. **Verificar o funcionamento:**
O Docker vai compilar o código, baixar a imagem do MySQL e iniciar a aplicação. O backend estará pronto e escutando na porta 8080.

    API disponível em: http://localhost:8080

---

## Como Executar o Frontend (React)

1. **Navegar até a pasta do Frontend:**
Abra um novo terminal e entre no diretório do frontend:
```
cd frontend
```

2. **Instalar as Dependências:**
Instale todos os pacotes necessários listados no package.json:

```
npm install
```

3. **Iniciar o Servidor de Desenvolvimento:**
Execute o script do Vite para subir o servidor local:

```
npm run dev
```

4. **Acessar a Aplicação:**
Após a inicialização, o terminal exibirá a URL local (geralmente http://localhost:5173). Abra o seu navegador e acesse esse endereço para utilizar o sistema.

---
