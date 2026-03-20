# 🐳 Como usar Docker Compose

## 📋 Pré-requisitos
- Docker instalado
- Docker Compose instalado

## 🔧 Configuração de ambiente

1. Crie um arquivo `.env` com base no `.env.example`:
```bash
cp .env.example .env
```

2. Ajuste ao menos as variáveis abaixo no `.env`:
- `DB_PASSWORD`
- `JWT_ISSUER`
- `JWT_ACCESS_TOKEN_TTL_HOURS`
- `JWT_PUBLIC_KEY_LOCATION`
- `JWT_PRIVATE_KEY_LOCATION`

3. Sobre as chaves JWT:
- Se `public.pem` e `private.pem` já estiverem em `src/main/resources`, use `classpath:public.pem` e `classpath:private.pem`.
- Se quiser chaves fora da imagem, monte os arquivos no container e use `file:/...`.

## 🚀 Comandos principais

### Subir os containers (aplicação + banco)
```bash
docker-compose up -d
```

### Ver os logs
```bash
# Todos os containers
docker-compose logs -f

# Apenas a aplicação
docker-compose logs -f app

# Apenas o banco
docker-compose logs -f postgres
```

### Parar os containers
```bash
docker-compose stop
```

### Parar e remover containers
```bash
docker-compose down
```

### Parar e remover containers + volumes (APAGA OS DADOS DO BANCO!)
```bash
docker-compose down -v
```

### Recompilar e subir
```bash
docker-compose up --build -d
```

## 🔍 Verificar se está funcionando

### Verificar status dos containers
```bash
docker-compose ps
```

### Acessar a aplicação
- Aplicação: http://localhost:8080
- Banco PostgreSQL: localhost:5432

### Conectar no banco via terminal
```bash
docker exec -it habibs-postgres psql -U postgres -d habibs
```

## ⚙️ Estrutura

### Dockerfile (Multi-stage build)
- **Etapa 1 (build)**: Compila a aplicação com Maven
- **Etapa 2 (runtime)**: Cria imagem leve apenas com JRE e o JAR

### docker-compose.yml
- **postgres**: Container do PostgreSQL 16
- **app**: Container da aplicação Spring Boot
- **Healthcheck**: Garante que o banco esteja pronto antes da app iniciar
- **Volumes**: Persiste os dados do banco
- **Network**: Permite comunicação entre containers

## 🔒 Segurança

As senhas são lidas do arquivo `.env`, que **NÃO está no Git**.

Para produção:
- Não use `changeme`/senhas padrão.
- Prefira chaves JWT externas (volume/secret) em vez de embutir na imagem.

## 🐛 Problemas comuns

**Porta já em uso**
- Mude a porta no `.env`: `SERVER_PORT=8081`

**Banco não conecta**
- Verifique os logs: `docker-compose logs postgres`
- Aguarde o healthcheck: `docker-compose ps`

**Mudei o código mas não atualiza**
- Recompile: `docker-compose up --build -d`

**Limpar tudo e começar do zero**
```bash
docker-compose down -v
docker-compose up --build -d
```
