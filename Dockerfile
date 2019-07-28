FROM openjdk:latest

ADD target/demo-teb-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-Dspring.data.mongodb.host=teb-mongo","-Djava.security.egd=file:/dev/./urandom","-Dspring.kafka.consumer.bootstrap-servers=kafka:9092","-Dspring.kafka.producer.bootstrap-servers=kafka:9092","-jar","app.jar"]

EXPOSE 8080