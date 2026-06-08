FROM maven:3-eclipse-temurin-23 AS build
WORKDIR /app
COPY Bancosol/pom.xml .
COPY Bancosol/src ./src
RUN mvn package -DskipTests -q

FROM eclipse-temurin:23-jre-alpine
WORKDIR /app
COPY --from=build /app/target/Bancosol-0.0.1-SNAPSHOT.war app.war
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.war"]
