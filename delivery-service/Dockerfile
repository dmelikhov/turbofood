FROM maven:3.9-amazoncorretto-17 AS build
COPY . /app
WORKDIR /app
RUN --mount=type=cache,target=/root/.m2 mvn clean package -Dmaven.test.skip=true

FROM gcr.io/distroless/java17-debian12
COPY --from=build /app/target/delivery-service-0.0.1-SNAPSHOT.jar /app/runner.jar
WORKDIR /app
CMD ["runner.jar"]
