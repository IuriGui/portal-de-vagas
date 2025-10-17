# Exemplos de Uso da API - Portal de Vagas

## Endpoint: POST /oportunidades/user/createUser

Este endpoint permite criar usuários tanto como candidatos quanto como instituições, dependendo do tipo de conta especificado.

### Exemplo 1: Criar um Candidato

```json
POST http://localhost:8080/oportunidades/user/createUser
Content-Type: application/json

{
    "email": "candidato@email.com",
    "senha": "senha123",
    "tipoConta": "CANDIDATO",
    "nome": "João Silva",
    "telefone": "11999999999",
    "dataNascimento": "1990-05-15T00:00:00.000Z",
    "curriculoUrl": "https://exemplo.com/curriculo.pdf"
}
```

### Exemplo 2: Criar uma Instituição

```json
POST http://localhost:8080/oportunidades/user/createUser
Content-Type: application/json

{
    "email": "empresa@email.com",
    "senha": "senha123",
    "tipoConta": "EMPRESA",
    "nomeFantasia": "Tech Solutions LTDA",
    "descricao": "Empresa de tecnologia especializada em desenvolvimento de software",
    "telefone": "1133334444"
}
```

### Campos Obrigatórios

**Para todos os usuários:**
- `email`: String - Email do usuário
- `senha`: String - Senha do usuário
- `tipoConta`: Enum - "CANDIDATO" ou "EMPRESA"

**Para Candidatos (quando tipoConta = "CANDIDATO"):**
- `nome`: String - Nome completo do candidato
- `telefone`: String - Telefone de contato
- `dataNascimento`: Date - Data de nascimento (formato ISO 8601)
- `curriculoUrl`: String (opcional) - URL do currículo

**Para Instituições (quando tipoConta = "EMPRESA"):**
- `nomeFantasia`: String - Nome fantasia da empresa
- `descricao`: String - Descrição da empresa
- `telefone`: String - Telefone de contato

### Resposta de Sucesso

```json
HTTP/1.1 201 Created
Location: /user/{uuid}
Content-Type: application/json

{
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "email": "candidato@email.com",
    "senha": "senha123",
    "tipoConta": "CANDIDATO"
}
```

### Resposta de Erro

```json
HTTP/1.1 400 Bad Request
Content-Type: application/json

{
    "error": "Tipo de conta inválido"
}
```

