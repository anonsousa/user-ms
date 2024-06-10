package io.anonsousa.user_ms.services;

import io.anonsousa.user_ms.dtos.UserRecordDto;
import io.anonsousa.user_ms.models.UserModel;
import io.anonsousa.user_ms.producers.UserProducer;
import io.anonsousa.user_ms.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    UserProducer userProducer;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public UserModel saveUser(UserRecordDto userRecordDto){
        var userModel = new UserModel();
        BeanUtils.copyProperties(userRecordDto, userModel);
        userRepository.save(userModel);

        userProducer.publishMessageEmail(userModel);
        return userModel;
    }
}
