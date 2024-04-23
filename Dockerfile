###### BUILD ######
FROM maven:3.8.3-openjdk-17 AS builder

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package
###### BUILD END ######

###### RUN ######
FROM arm64v8/openjdk:17-ea-16-jdk

WORKDIR /app

COPY --from=builder /app/target/mcaBackend-1.0.jar .

EXPOSE 8080

CMD ["java", "-jar", "mcaBackend-1.0.jar"]
###### RUN END ######
