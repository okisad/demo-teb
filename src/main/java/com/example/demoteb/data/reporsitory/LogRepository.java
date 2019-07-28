package com.example.demoteb.data.reporsitory;

import com.example.demoteb.data.entity.LogEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends MongoRepository<LogEntity, ObjectId> {
}
