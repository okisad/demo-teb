package com.example.demoteb.data.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "logs")
@Getter
@Setter
public class LogEntity {

    @Id
    private ObjectId id;
    private String level;
    private String city;
    private String message;
    private Date date;

    public static LogEntity newInstance(String level, String city,String message,Date date){
        LogEntity logEntity = new LogEntity();
        logEntity.setLevel(level);
        logEntity.setCity(city);
        logEntity.setMessage(message);
        logEntity.setDate(date);
        return logEntity;
    }
}
