package br.com.lucasbpo.transacoes.produtor;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class RabbitMQConfiguration {

    @Value("${rabbitmq.transacoes.financeiras.queue}")
    private String transacoesFinanceiras;

    @Value("${rabbitmq.transacoes.policia-federal.queue}")
    private String policiaFederal;

    @Value("${rabbitmq.transacoes.receita-federal.queue}")
    private String receitaFederal;

    @Value("${rabbitmq.transacoes.suspeitas.exchange}")
    private String transacoesSuspeitas;

    // Queues
    @Bean
    @Primary
    public Queue criarFilaTransacoesFinanceiras() {
        return QueueBuilder
                .durable(transacoesFinanceiras)
                .build();
    }

    @Bean
    public Queue criarFilaPoliciaFederal() {
        return QueueBuilder
                .durable(policiaFederal)
                .build();
    }

    @Bean
    public Queue criarFilaReceitaFederal() {
        return QueueBuilder
                .durable(receitaFederal)
                .build();
    }

    // Exchange
    @Bean
    public FanoutExchange criarFanoutExchangeTransacoesSuspeitas() {
        return ExchangeBuilder
                .fanoutExchange(transacoesSuspeitas)
                .build();
    }

    // Bindings
    @Bean
    public Binding criarBindingPoliciaFederal() {
        return BindingBuilder
                .bind(criarFilaPoliciaFederal())
                .to(criarFanoutExchangeTransacoesSuspeitas());
    }

    @Bean
    public Binding criarBindingReceitaFederal() {
        return BindingBuilder
                .bind(criarFilaReceitaFederal())
                .to(criarFanoutExchangeTransacoesSuspeitas());
    }

    @Bean
    public RabbitAdmin criarRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> inicializarAdmin(RabbitAdmin rabbitAdmin) {
        return event -> rabbitAdmin.initialize();
    }

    // JSON
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
