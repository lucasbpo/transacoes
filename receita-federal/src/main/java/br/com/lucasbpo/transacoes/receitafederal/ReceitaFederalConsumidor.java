package br.com.lucasbpo.transacoes.receitafederal;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class ReceitaFederalConsumidor {

    @RabbitListener(queues = {"${rabbitmq.transacoes.receitafederal.queue}"})
    public void consumirFilaReceitaFederal(@Payload Transacao transacaoSuspeita) {
        System.out.println("Receita Federal");
        System.out.println("Processando transação suspeita: " + transacaoSuspeita);
    }
}
