# Report Generator 
![excel][excel-image]

> Aplicação sugerida em teste pela empresa Nexfar.
> > ## Considerações
> Infelizmente não consegui desenvolver os retornos de CSV,
> e ORDER_DETAILED para o arquivo XLS. 
> Tive dificuldade com a manipulação das dependencias.
> 
> Irei continuar o desenvolvimento, aprendi bastante com o desafio proposto e agradeço a oportunidade.

[![Java Version][java-image]][java-url]
[![Spring Version][spring-image]][spring-url]
[![MongoDB][mongodb-image]][mongodb-url]
### Build
```sh
mvn clean install
```

### Application-properties
Adicionar as configurações de conexão passadas no enunciado do teste;
```yml
spring:
    data:
        mongodb:
            host: 
            port:
            authentication-database: 
            username:
            password:
            database:
```


[excel-image]: https://img.shields.io/badge/Microsoft_Excel-217346?style=for-the-badge&logo=microsoft-excel&logoColor=white
[java-image]: https://img.shields.io/badge/Java-v17-brightgreen
[spring-image]: https://img.shields.io/badge/Spring--Boot-v3.0.6-brightgreen
[mongodb-image]: https://img.shields.io/badge/MongoDb-Spring--Data--JPA-brightgreen
[java-url]: https://docs.oracle.com/en/java/javase/17/
[spring-url]: https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-dependencies/3.0.6
[mongodb-url]: https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-mongodb/3.0.6