# Desafio notícias rápidas

## Tecnologias utilizadas

1. -Java 17

2. -Spring Boot (Security, Validation, WebFlux, Mail, JPA Hibernate, Web)

3. -MySql

4. -IntelliJ

5. -Postman

6. -Lombok

## Instruções de uso

1. -Importar o arquivo "Desafio API GFT.postman_collection" no postman

2. -Após inicializar o projeto, comentar as linhas localizadas no application.properties:<br/>
    spring.jpa.defer-datasource-initialization=true<br/>
    spring.sql.init.mode=always<br/>
    Pois essa configuração carrega o arquivo data.sql, se inicializar novamente sem comentar vai gerar erro, pois vai ler novamente o arquivo data.sql.

3. -Logins cadastrados para teste:<br/>
    ADMIN - Login: admin@gmal.com<br/>
            Senha: 123456<br/>
    USER - Login: user@mail.com<br/>
           Senha: 123456<br/>

4. -Copiar token gerado ao realizar login no postman para verificar os endpoints cadastrados.

## Permissões de perfis (endpoints)

    ADMIN - CRUD User, Historic e Top Trending
    USER - Insert / New Tag, Find News e Historic
    ADICIONAL ADMIN - CRUD Tag para manutenção (se for preciso)
