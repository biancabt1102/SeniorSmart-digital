# SeniorSmart

*O nosso projeto é focado em pessoas a partir de 50 anos que possuem dificuldade em utilizar computadores, smartphones e outros eletroeletrônicos. Ele consiste em um aplicativo que utiliza um chatbot com inteligência artificial, capaz de responder perguntas personalizadas para cada problema ou dúvida do usuário.*

<hr>

<br>

<h2 style="font-style: italic; color: #F5FFFA" >Ideia da aplicação</h2>

<img src="image/Digital Business - SeniorSmart.png" width="auto">

<br><hr>

## Endpoints

- Usuário:
    - [Cadastrar](#cadastrar-usuário)
    - [Listar todos](#listar-usuários)
    - [Alterar](#alterar-usuário)
    - [Detalhes](#detalhes-usuário)
    - [Excluir](#excluir-usuário)

- Plano:
    - [Cadastrar](#cadastrar-plano)
    - [Listar todos](#listar-planos)
    - [Alterar](#alterar-plano)
    - [Detalhes](#detalhes-plano)
    - [Excluir](#excluir-plano)

- Pagamento:
    - [Cadastrar](#cadastrar-pagamento)
    - [Listar todos](#listar-pagamentos)
    - [Alterar](#alterar-pagamento)
    - [Detalhes](#detalhes-pagamento)
    - [Excluir](#excluir-pagamento)

- Pergunta:
    - [Cadastrar](#cadastrar-pergunta)
    - [Listar todos](#listar-perguntas)
    - [Detalhes](#detalhes-pergunta)

- Resposta:
    - [Cadastrar](#cadastrar-resposta)
    - [Listar todos](#listar-respostas)
    - [Detalhes](#detalhes-resposta)

<hr>

### Cadastrar Usuário

`POST` /seniorsmart/api/usuario

*Campos da requisição*

| Campo                | Tipo   | Obrigatório | Descrição                                                          |
|----------------------|--------|:-----------:|--------------------------------------------------------------------|
|nomeUsuario           |String  |sim          | Nome completo do usuário.                                          |
|emailUsuario          |String  |sim          | Endereço de e-mail do usuário.                                     |
|senhaUsuario          |String  |sim          | Senha do usuário.                                                  |
|confirmacaoSenha      |String  |sim          | Confirmação da senha do usuário.                                   |
|dataNascimentoUsuario |String  |sim          | Data de nascimento do usuário. (No formato "DD-MM-YYYY")           |
|telefoneUsuario       |String  |sim          | Número de celular do usuário.                                      |
|tipoPlano             |String  |sim          | O tipo de pagamento escolhido pelo usuário. (teste grátis, anual ou mensal)|

```
{
    "nomeUsuario": "Mateus Araujo",
    "emailUsuario": "matheusaraujo@hotmail.com",
    "senhaUsuario": "03121865",
    "confirmacaoSenha": "03121865",
    "dataNascimentoUsuario": "15-05-1860",
    "telefoneUsuario": "11940028922",
    "tipoPlano": "Teste Grátis"
}
```

*Corpo da resposta*

| Código | Descrição                                    |
|:------:|----------------------------------------------|
|201     | Usuário criada com sucesso.                  |
|400     | Usuário inválido.                            |
|409     | Já existe um usuário com o e-mail fornecido. |

<hr>

### Listar Usuários

`GET` /seniorsmart/api/usuarios

*Exemplo de resposta*

| Campo                | Tipo   | Descrição                                                                  |
|----------------------|--------|----------------------------------------------------------------------------|
|idUsuario             |Long    | O id de um usuário previamente criado.                                     |
|nomeUsuario           |String  | Nome completo do usuário.                                                  |
|emailUsuario          |String  | Endereço de e-mail do usuário.                                             |
|senhaUsuario          |String  | Senha do usuário.                                                          |
|dataNascimentoUsuario |Date    | Data de nascimento do usuário. (No formato "DD-MM-YYYY")                   |
|telefoneUsuario       |String  | Número de celular do usuário.                                              |
|tipoPlano             |String  | O valor do tipo de pagamento escolhido pelo usuário (teste grátis, anual ou mensal)|

```
[
    {
        "idUsuario": "1"
        "nomeUsuario": "Mateus Araujo",
        "emailUsuario": "matheusaraujo@hotmail.com",
        "senhaUsuario": "03121865",
        "dataNascimentoUsuario": "15-05-1860",
        "telefoneUsuario": "11940028922",
        "tipoPlano": "Teste Grátis"
    },

    {
        "idUsuario": "2"
        "nomeUsuario": "José Aquino",
        "emailUsuario": "seuze@bol.com",
        "senhaUsuario": "11091922",
        "dataNascimentoUsuario": "02-12-1922",
        "telefoneUsuario": "11989224002",
        "tipoPlano": "Anual"
    },

    {
        "idUsuario": "3"
        "nomeUsuario": "Pedro de Alcântara João Carlos Leopoldo Salvador Bibiano Francisco Xavier de Paula Leocádio Miguel Gabriel Rafael Gonzaga de Bragança e Bourbon",
        "emailUsuario": "dompedro2@bol.com.br",
        "senhaUsuario": "07091822",
        "dataNascimentoUsuario": "15-05-1825",
        "telefoneUsuario": "11911111111",
        "tipoPlano": "Mensal"
    }
]
```

*Corpo da resposta*

| Código | Descrição                   |
|:------:|-----------------------------|
|200     | Listagem feita com sucesso. |
|404     | Lista não encontrada.       |

<hr>

### Alterar Usuário

`PUT` /seniorsmart/api/usuario/{id}

*Campos da requisição*

| Campo                | Tipo   | Obrigatório | Descrição                                                          |
|----------------------|--------|:-----------:|--------------------------------------------------------------------|
|idUsuario             |Long    |sim          | O id de um usuário previamente criado.                             |
|nomeUsuario           |String  |não          | Nome completo do usuário.                                          |
|emailUsuario          |String  |não          | Endereço de e-mail do usuário.                                     |
|senhaUsuario          |String  |não          | Senha do usuário.                                                  |
|dataNascimentoUsuario |String  |não          | Data de nascimento do usuário. (No formato "DD-MM-YYYY")           |
|telefoneUsuario       |String  |não          | Número de celular do usuário.                                      |
|tipoPlano             |String  |não          | O tipo de pagamento escolhido pelo usuário. (teste grátis, anual ou mensal)|

```
{
    "nomeUsuario": "Matheus Araujo",
    "emailUsuario": "matheusaraujo@hotmail.com",
    "senhaUsuario": "03121865",
    "confirmacaoSenha": "03121865",
    "dataNascimentoUsuario": "15-05-1860",
    "telefoneUsuario": "11940028922",
    "tipoPlano": "Anual"
}
```

*Corpo de resposta*

| Código | Descrição                        |
|:------:|----------------------------------|
|200     | Alteração realizada com sucesso. |
|400     | Alteração inválida.              |
|404     | Avaliação não encontrada.        |

<hr>

### Detalhes Usuário

`GET` /seniorsmart/api/usuario/{id}

*Exemplo de resposta*

| Campo                | Tipo | Descrição                                                |
|----------------------|------|----------------------------------------------------------|
|idUsuario             |Long   | O id de um usuário previamente criado.                   |
|nomeUsuario           |String | Nome completo do usuário.                                |
|emailUsuario          |String | Endereço de e-mail do usuário.                           |
|senhaUsuario          |String | Senha do usuário.                                        |
|dataNascimentoUsuario |Date   | Data de nascimento do usuário. (No formato "DD-MM-YYYY") |
|telefoneUsuario       |String | Número de celular do usuário.                            |
|tipoPlano             |String | O valor do tipo de pagamento escolhido pelo usuário (teste grátis, anual ou mensal)|

```
{
    "idUsuario": "1",
    "nomeUsuario": "Matheus Araujo",
    "emailUsuario": "matheusaraujo@hotmail.com",
    "senhaUsuario": "03121865",
    "confirmacaoSenha": "03121865",
    "dataNascimentoUsuario": "15-05-1860",
    "telefoneUsuario": "11940028922",
    "tipoPlano": "Anual"
}
```

*Corpo da resposta*

| Código | Descrição                                  |
|:------:|--------------------------------------------|
|200     | Os dados foram retornados com sucesso.     |
|404     | Não foi encontrado um usuário com esse ID. |

<hr>

### Excluir Usuário

`DELETE` /seniorsmart/api/usuario/{id}

*Corpo da resposta*

| Código | Descrição                |
|:------:|--------------------------|
|204     | Requisição bem-sucedida. |
|404     | Conteúdo não encontrado. |

<hr>

### Cadastrar Plano

`POST` /seniorsmart/api/plano

*Campos da requisição*

| Campo             | Tipo      | Obrigatório    | Descrição                                                   |
|-------------------|-----------|:--------------:|-------------------------------------------------------------|
|tipoPlano          |String     |sim             |O tipo de pagamento escolhido pelo usuário. (teste grátis, anual ou mensal)|
|planoMensal        |BigDecimal |não             |Valor do plano mensal.                                   |
|planoAnual         |BigDecimal |não             |Valor do plano anual.                                 |

```
{
    "tipoPlano": "Teste Gratis"
}
```

*Corpo da resposta*

| Código | Descrição                                                      |
|:------:|----------------------------------------------------------------|
|201     | Informação de plano criada com sucesso.                        |
|400     | Informação de plano inválida.                                  |

<hr>

### Listar Planos

`GET` /seniorsmart/api/planos

*Campos da requisição*

| Campo             | Tipo      | Descrição                                                   |
|-------------------|-----------|-------------------------------------------------------------|
|tipoPlano          |String     |O tipo de pagamento escolhido pelo usuário. (teste grátis, anual ou mensal)|
|planoMensal        |BigDecimal |Valor do plano mensal.                                   |
|planoAnual         |BigDecimal |Valor do plano anual.                                 |

```
[
    {
        "idPlano": 1,
        "tipoPlano": "Teste Gratis",
        "planoMensal": null,
        "planoAnual": null
    },
    {
        "idPlano": 2,
        "tipoPlano": "Mensal",
        "planoMensal": 35,
        "planoAnual": null
    },
    {
        "idPlano": 3,
        "tipoPlano": "Anual",
        "planoMensal": null,
        "planoAnual": 378
    }
]
```

*Corpo da resposta*

| Código | Descrição                   |
|:------:|-----------------------------|
|200     | Listagem feita com sucesso. |
|404     | Lista não encontrada.       |

<hr>

### Alterar Plano

`PUT` /seniorsmart/api/plano/{id}

*Campos da requisição*

| Campo             | Tipo      | Obrigatório    | Descrição                                                   |
|-------------------|-----------|:--------------:|-------------------------------------------------------------|
|tipoPlano          |String     |não             |O tipo de pagamento escolhido pelo usuário. (teste grátis, anual ou mensal)|
|planoMensal        |BigDecimal |não             |Valor do plano mensal.                                   |
|planoAnual         |BigDecimal |não             |Valor do plano anual.                                 |

```
{
    "idPlano": 1,
    "tipoPlano": "Mensal",
    "planoMensal": 35
}
```

*Corpo de resposta*

| Código | Descrição                        |
|:------:|----------------------------------|
|200     | Alteração realizada com sucesso. |
|400     | Alteração inválida.              |
|404     | Plano não encontrado.            |

<hr>

### Detalhes Plano

`GET` /seniorsmart/api/plano/{id}

*Campos da requisição*

| Campo             | Tipo      | Descrição                                                   |
|-------------------|-----------|-------------------------------------------------------------|
|tipoPlano          |String     |O tipo de pagamento escolhido pelo usuário. (teste grátis, anual ou mensal)|
|planoMensal        |BigDecimal |Valor do plano mensal.                                   |
|planoAnual         |BigDecimal |Valor do plano anual.                                 |

```
{
    "idPlano": 1,
    "tipoPlano": "Mensal",
    "planoMensal": 35,
    "planoAnual": null
}
```

*Corpo da resposta*

| Código | Descrição                                    |
|:------:|----------------------------------------------|
|200     | Os dados foram retornados com sucesso.       |
|404     | Não foi encontrado um plano com esse ID.     |

<hr>

### Excluir Plano

`DELETE` /seniorsmart/api/plano/{id}

*Corpo da resposta*

| Código | Descrição                |
|:------:|--------------------------|
|204     | Requisição bem-sucedida. |
|404     | Conteúdo não encontrado. |

<hr>

### Cadastrar Pagamento

`POST` /seniorsmart/api/pagamento

*Campos da requisição*

| Campo             | Tipo   | Obrigatório | Descrição                                                   |
|-------------------|--------|:-----------:|-------------------------------------------------------------|
|idUsuario          |Long    |sim          |ID do usuário associado à informação de pagamento.           |
|nomeTitular        |String  |sim          |Nome do titular do cartão.                                   |
|numeroCartao       |String  |sim          |Número do cartão de crédito.                                 |
|dataValidadeCartao |String  |sim          |Data de validade do cartão de crédito. (No formato "MM/YYYY")|
|codigoSeguranca    |String  |sim          |Código de segurança do cartão de crédito.                    |
|tipoPlano          |String  |sim          | O tipo de pagamento escolhido pelo usuário. (teste grátis, anual ou mensal)|

```
{
    "idUsuario": 1,
    "nomeTitular": "MATHEUS ARAUJO",
    "numeroCartao": "1234 5678 9012 3456",
    "dataValidadeCartao": "10/2024",
    "codigoSeguranca": "123",
    "tipoPlano": "Anual"
}
```

*Corpo da resposta*

| Código | Descrição                                                      |
|:------:|----------------------------------------------------------------|
|201     | Informação de pagamento criada com sucesso.                    |
|400     | Informação de pagamento inválida.                              |
|409     | Já existe uma informação de pagamento para o usuário informado.|

<hr>

### Listar Pagamentos

`GET` /seniorsmart/api/pagamentos

*Campos da resposta*

| Campo             | Tipo   | Descrição                                                   |
|-------------------|--------|-------------------------------------------------------------|
|idPagamento        |Long    |O id de um pagamento previamente criado.                     |
|idUsuario          |Long    |ID do usuário associado à informação de pagamento.           |
|nomeTitular        |String  |Nome do titular do cartão.                                   |
|numeroCartao       |String  |Número do cartão de crédito.                                 |
|dataValidadeCartao |String  |Data de validade do cartão de crédito. (No formato "MM/YYYY")|
|codigoSeguranca    |String  |Código de segurança do cartão de crédito.                    |
|tipoPlano          |String  | O valor do tipo de pagamento escolhido pelo usuário (teste grátis, anual ou mensal)|

```
[
    {
        "idPagamento": 1,
        "idUsuario": 1,
        "nomeTitular": "MATHEUS ARAUJO",
        "numeroCartao": "**** **** **** 3456",
        "dataValidadeCartao": "10/2024",
        "codigoSeguranca": "***",
        "tipoPlano": "Anual"
    },

    {
        "idPagamento": 2,
        "idUsuario": 2,
        "nomeTitular": "JOSÉ A.",
        "numeroCartao": "**** **** **** 1234",
        "dataValidadeCartao": "03/2025",
        "codigoSeguranca": "***",
        "tipoPlano": "Mensal"
    },

    {
        "idPagamento": 3,
        "idUsuario": 3,
        "nomeTitular": "PEDRO ALVARES C.",
        "numeroCartao": "**** **** **** 1111",
        "dataValidadeCartao": "01/2024",
        "codigoSeguranca": "***",
        "tipoPlano": "Teste Grátis"
    }
]
```

*Corpo da resposta*

| Código | Descrição                   |
|:------:|-----------------------------|
|200     | Listagem feita com sucesso. |
|404     | Lista não encontrada.       |

<hr>

### Alterar Pagamento

`PUT` /seniorsmart/api/pagamento/{id}

*Campos da requisição*

| Campo             | Tipo   | Obrigatório | Descrição                                                   |
|-------------------|--------|:-----------:|-------------------------------------------------------------|
|idUsuario          |Long    |sim          |ID do usuário associado à informação de pagamento.           |
|idPagamento        |Long    |sim          |O id de um pagamento previamente criado.                     |
|nomeTitular        |String  |não          |Nome do titular do cartão.                                   |
|numeroCartao       |String  |não          |Número do cartão de crédito.                                 |
|dataValidadeCartao |String  |não          |Data de validade do cartão de crédito. (No formato "MM/YYYY")|
|codigoSeguranca    |String  |não          |Código de segurança do cartão de crédito.                    |
|tipoPlano          |String  |sim          | O tipo de pagamento escolhido pelo usuário. (teste grátis, anual ou mensal)|
```
{
    "idUsuario": 1,
    "idPagamento": 1,
    "nomeTitular": "MATHEUS ARAUJO",
    "numeroCartao": "5678 9012 3456 1234",
    "dataValidadeCartao": "07/2025",
    "codigoSeguranca": "321",
    "tipoPlano": "Mensal"
}
```

*Corpo de resposta*

| Código | Descrição                        |
|:------:|----------------------------------|
|200     | Alteração realizada com sucesso. |
|400     | Alteração inválida.              |
|404     | Pagamento não encontrado.        |

<hr>

### Detalhes Pagamento

`GET` /seniorsmart/api/pagamento/{id}

*Campos da resposta*

| Campo             | Tipo   | Descrição                                                   |
|-------------------|--------|-------------------------------------------------------------|
|idPagamento        |Long    |O id de um pagamento previamente criado.                     |
|idUsuario          |Long    |ID do usuário associado à informação de pagamento.           |
|nomeTitular        |String  |Nome do titular do cartão.                                   |
|numeroCartao       |String  |Número do cartão de crédito.                                 |
|dataValidadeCartao |String  |Data de validade do cartão de crédito. (No formato "MM/YYYY")|
|codigoSeguranca    |String  |Código de segurança do cartão de crédito.                    |
|tipoPlano          |String  | O valor do tipo de pagamento escolhido pelo usuário (teste grátis, anual ou mensal)|

```
{
    "idPagamento": 1,
    "idUsuario": 1,
    "nomeTitular": "MATHEUS ARAUJO",
    "numeroCartao": "5678 9012 3456 1234",
    "dataValidadeCartao": "07/2025",
    "codigoSeguranca": "321",
    "tipoPlano": "Mensal"
}
```

*Corpo da resposta*

| Código | Descrição                                    |
|:------:|----------------------------------------------|
|200     | Os dados foram retornados com sucesso.       |
|404     | Não foi encontrado um pagamento com esse ID. |

<hr>

### Excluir Pagamento

`DELETE` /seniorsmart/api/pagamento/{id}

*Corpo da resposta*

| Código | Descrição                |
|:------:|--------------------------|
|204     | Requisição bem-sucedida. |
|404     | Conteúdo não encontrado. |

<hr>

### Cadastrar Pergunta

`POST` /seniorsmart/api/pergunta

*Campos da requisição*

| Campo    | Tipo  | Obrigatório | Descrição                          |
|----------|-------|:-----------:|------------------------------------|
|idUsuario |Long   |sim          |ID do usuário associado à pergunta. |           
|pergunta  |String |sim          |A pergunta feita pelo idoso.        |


```
{
    "idUsuario": 1,
    "pergunta": "Como faço para tirar uma foto com o meu celular?"
}
```

*Corpo da resposta*

| Código | Descrição                |
|:------:|--------------------------|
|201     | Requisição bem-sucedida. |
|400     | Requisição inválida.     |

<hr>

### Listar Perguntas

`GET` /seniorsmart/api/perguntas

*Campos da resposta*

| Campo     | Tipo  | Descrição                                    |
|-----------|-------|----------------------------------------------|
|idPergunta |Long   |O id da pergunta à qual a resposta se refere. |
|idUsuario  |Long   |Id do usuário associado à pergunta.           |
|pergunta   |String |A pergunta feita pelo idoso.                  |

```
[   
    {        
        "idPergunta": 1,
        "idUsuario": 1,        
        "pergunta": "Como faço para tirar uma foto com o meu celular?"
    },    
    {        
        "idPergunta": 2,
        "idUsuario": 1,
        "pergunta": "Como faço para enviar uma mensagem no WhatsApp?"
    },
    {
        "idPergunta": 3,
        "idUsuario": 2,
        "pergunta": "Como faço para adicionar um contato na minha lista de contatos do celular?"
    }
]
```

*Corpo da resposta*

| Código | Descrição                   |
|:------:|-----------------------------|
|200     | Listagem feita com sucesso. |
|404     | Lista não encontrada.       |

<hr>

### Detalhes Pergunta

`GET` /seniorsmart/api/pergunta/{id}

*Campos da resposta*

| Campo     | Tipo  | Descrição                                    |
|-----------|-------|----------------------------------------------|
|idPergunta |Long   |O id da pergunta à qual a resposta se refere. |
|idUsuario  |Long   |Id do usuário associado à pergunta.           |
|pergunta   |String |A pergunta feita pelo idoso.                  |


```
{        
    "idPergunta": 1,
    "idUsuario": 1,        
    "pergunta": "Como faço para tirar uma foto com o meu celular?"
}
```

*Corpo da resposta*

| Código | Descrição                                    |
|:------:|----------------------------------------------|
|200     | Os dados foram retornados com sucesso.       |
|404     | Não foi encontrado uma pergunta com esse ID. |

<hr>

### Cadastrar Resposta

`POST` /seniorsmart/api/resposta

*Campos da requisição*

| Campo     | Tipo  | Obrigatório | Descrição                                    |
|-----------|-------|:-----------:|----------------------------------------------|
|idPergunta |Long   |sim          |O id da pergunta à qual a resposta se refere. |
|resposta   |String |sim          |A resposta do chatbot.                        |

```
{
    "idPergunta": 1,
    "resposta": "Para tirar uma foto, abra o aplicativo da câmera e toque no ícone da câmera. Em seguida, aponte a câmera para o que deseja fotografar e toque no botão de tirar foto."
}
```

| Código | Descrição                |
|:------:|--------------------------|
|201     | Requisição bem-sucedida. |
|400     | Requisição inválida.     |

<hr>

### Listar Respostas

`GET` /seniorsmart/api/respostas

*Campos da resposta*

| Campo     | Tipo  | Descrição                                    |
|-----------|-------|----------------------------------------------|
|idResposta |Long   |O id da resposta.                             |
|idPergunta |Long   |O id da pergunta à qual a resposta se refere. |
|resposta   |String |A resposta do chatbot.                        |

```
[
    {
        "idResposta": 1,
        "idPergunta": 1,
        "resposta": "Para tirar uma foto com o seu celular, siga os passos abaixo:
        1. Abra o aplicativo de câmera do seu celular.
        2. Enquadre o objeto ou a cena que deseja fotografar.
        3. Toque no botão de disparo da câmera para capturar a foto.
        4. Você pode acessar a foto tirada na galeria de imagens do seu celular.
        Espero que estas instruções sejam úteis!"
        
    },
    {
        "idResposta": 2,
        "idPergunta": 2,
        "resposta": "Para enviar uma mensagem no WhatsApp, siga os passos abaixo:
        1. Abra o aplicativo do WhatsApp no seu celular.
        2. Toque na opção de bate-papo ou no nome do contato para quem deseja enviar a mensagem.
        3. Digite a mensagem no campo de texto.
        4. Toque no botão de envio (geralmente representado por um ícone de avião de papel).
        5. A mensagem será enviada ao contato selecionado.
        Espero que estas instruções sejam úteis!"
    },
    {
        "idResposta": 3,
        "idPergunta": 3,
        "resposta": "Para adicionar um contato na lista de contatos do seu celular, siga os passos abaixo:
        1. Abra o aplicativo de contatos no seu celular.
        2. Selecione a opção de adicionar um novo contato.
        3. Preencha os campos de nome, número de telefone e outras informações relevantes do contato.
        4. Salve o contato.
        O novo contato será adicionado à sua lista de contatos do celular e estará disponível para acesso e uso."
    }
]
```

*Corpo da resposta*

| Código | Descrição                   |
|:------:|-----------------------------|
|200     | Listagem feita com sucesso. |
|404     | Lista não encontrada.       |

<hr>

### Detalhes Resposta

`GET` /seniorsmart/api/resposta/{id}

*Campos da resposta*

| Campo     | Tipo  | Descrição                                    |
|-----------|-------|----------------------------------------------|
|idResposta |Long   |O id da resposta.                             |
|idPergunta |Long   |O id da pergunta à qual a resposta se refere. |
|resposta   |String |A resposta do chatbot.                        |

```
{
    "idResposta": 1,
    "idPergunta": 1,
    "resposta": "Para tirar uma foto com o seu celular, siga os passos abaixo:
    1. Abra o aplicativo de câmera do seu celular.
    2. Enquadre o objeto ou a cena que deseja fotografar.
    3. Toque no botão de disparo da câmera para capturar a foto.
    4. Você pode acessar a foto tirada na galeria de imagens do seu celular.
    Espero que estas instruções sejam úteis!" 
}
```

*Corpo da resposta*

| Código | Descrição                                    |
|:------:|----------------------------------------------|
|200     | Os dados foram retornados com sucesso.       |
|404     | Não foi encontrado uma resposta com esse ID. |