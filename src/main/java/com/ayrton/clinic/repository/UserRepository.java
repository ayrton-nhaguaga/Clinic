package com.ayrton.clinic.repository;

import com.ayrton.clinic.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {


}
