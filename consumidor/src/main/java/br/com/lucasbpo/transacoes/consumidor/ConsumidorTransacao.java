package br.com.lucasbpo.transacoes.consumidor;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ConsumidorTransacao {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;

    public ConsumidorTransacao(
            RabbitTemplate rabbitTemplate,
            @Value("${rabbitmq.transacoes.exchange}") String exchange) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
    }

    @RabbitListener(queues = {"${rabbitmq.transacoes.queue}"})
    public void consumirFilaTransacaoFinanceira(@Payload Transacao transacao) {
        try {
            Thread.sleep(1000); // Simule uma espera programada de 1 segundo

            if (transacao.valor() >= 40000.0) {
                rabbitTemplate.convertAndSend(exchange, "", transacao);
            }

            System.out.println(transacao);

        } catch (InterruptedException e) {
            System.out.println("Erro ao processar transação: " + e.getMessage());
        }
    }
}
