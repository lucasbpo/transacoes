package br.com.lucasbpo.transacoes.policiafederal;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PoliciaFederalConsumidor {

    @RabbitListener(queues = {"${rabbitmq.transacoes.policiafederal.queue}"})
    public void consumirFilaPoliciaFederal(@Payload Transacao transacaoSuspeita) {
        System.out.println("Policia Federal");
        System.out.println("Processando transação suspeita: " + transacaoSuspeita);
    }

}
