package com.ika.warehouseinventory.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@Configuration
public class JacksonConfiguration {

  /*
   * Module for serialization/deserialization of RFC7807 Problem.
   */
  @Bean
  public ProblemModule problemModule() {
    return new ProblemModule();
  }

  /*
   * Module for serialization/deserialization of ConstraintViolationProblem.
   */
  @Bean
  public ConstraintViolationProblemModule constraintViolationProblemModule() {
    return new ConstraintViolationProblemModule();
  }

  @Bean
  public MessageConverter jsonMessageConverter(){
    return new Jackson2JsonMessageConverter();
  }

}
