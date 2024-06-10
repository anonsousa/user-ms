package io.anonsousa.user_ms.producers;

import io.anonsousa.user_ms.dtos.EmailDto;
import io.anonsousa.user_ms.models.UserModel;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UserProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value(value = "${broker.queue.email.name}" )
    private String routingKey;

    public void publishMessageEmail(UserModel userModel){
        var emailDto = new EmailDto();
        emailDto.setUserId(userModel.getUserId());
        emailDto.setEmailTo(userModel.getEmail());
        emailDto.setSubject("Cadastro Realizado com Sucesso!");
        emailDto.setText(userModel.getName() + ", seja bem vindo(a)! \n Agradecemos o seu cadastro, aproveite todos os nossos recursos!");

        rabbitTemplate.convertAndSend("", routingKey, emailDto);

    }

}
