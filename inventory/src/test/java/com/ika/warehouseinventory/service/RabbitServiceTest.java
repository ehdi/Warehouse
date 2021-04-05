package com.ika.warehouseinventory.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.ika.warehouseinventory.service.dto.ContainArticlesDTO;
import com.ika.warehouseinventory.service.dto.ProductsDTO;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AnonymousQueue;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.junit.RabbitAvailable;
import org.springframework.amqp.rabbit.test.RabbitListenerTest;
import org.springframework.amqp.rabbit.test.RabbitListenerTestHarness;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@DirtiesContext
@RabbitAvailable
public class RabbitServiceTest {


  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Autowired
  private Queue queue;

  @Autowired
  private RabbitListenerTestHarness harness;

  @Test
  public void queue_ShouldConsumeProduct() throws Exception {

    ProductsDTO productsDTO = new ProductsDTO();
    productsDTO.setName("Dining Chair");

    ContainArticlesDTO containArticlesDTO = new ContainArticlesDTO();
    containArticlesDTO.setArtId(1L);
    containArticlesDTO.setAmountOf(4L);

    List<ContainArticlesDTO> containArticlesDTOList = new ArrayList<>();
    containArticlesDTOList.add(containArticlesDTO);
    productsDTO.setContainArticlesList(containArticlesDTOList);


    this.rabbitTemplate.convertSendAndReceive(this.queue.getName(),
        "messages.key",productsDTO);

    Listener listener = this.harness.getSpy("product");
    assertThat(listener).isNotNull();
    assertThat(listener.product(productsDTO)).isEqualTo("Dining Chair");
  }


  @Configuration
  @RabbitListenerTest(capture = true)
  public static class Config {

    @Bean
    public Listener listener() {
      return new Listener();
    }

    @Bean
    public ConnectionFactory connectionFactory() {
      return new CachingConnectionFactory("localhost");
    }

    @Bean
    public Queue queue() {
      return new AnonymousQueue();
    }

    @Bean
    public RabbitAdmin admin(ConnectionFactory cf) {
      return new RabbitAdmin(cf);
    }

    @Bean
    public RabbitTemplate template(ConnectionFactory cf) {
      final var rabbitTemplate = new RabbitTemplate(cf);
      rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
      return rabbitTemplate;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
      final var rabbitTemplate = new RabbitTemplate(connectionFactory);
      rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
      return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
      return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
        ConnectionFactory cf) {
      SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();
      containerFactory.setConnectionFactory(cf);
      return containerFactory;
    }

  }

  public static class Listener {

    @RabbitListener(id = "product", queues = "#{queue.name}")
    public String product(ProductsDTO productsDTO) {
      return productsDTO.getName();
    }

  }

}
