package br.com.lucasbpo.transacoes.produtor;

import jakarta.annotation.PostConstruct;
import java.util.List;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProdutorTransacao {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public ProdutorTransacao(RabbitTemplate rabbitTemplate, Queue queue) {
        this.rabbitTemplate = rabbitTemplate;
        this.queue = queue;
    }

    @PostConstruct
    public void onInit() {
        List<Transacao> transacoes = LeitorTransacoes.lerTodos();
        transacoes.forEach(transacao -> rabbitTemplate.convertAndSend(queue.getName(), transacao));
    }
}
