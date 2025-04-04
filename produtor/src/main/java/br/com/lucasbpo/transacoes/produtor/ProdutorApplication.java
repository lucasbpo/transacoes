package br.com.lucasbpo.transacoes.produtor;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class ProdutorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProdutorApplication.class, args);
    }

}
