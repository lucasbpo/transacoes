package br.com.lucasbpo.transacoes.consumidor;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableRabbit
@SpringBootApplication
public class ConsumidorApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumidorApplication.class, args);
    }

}
