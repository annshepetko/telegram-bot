FROM openjdk:20 as final
WORKDIR /app
COPY target/bot.jar app.jar
EXPOSE 8080
CMD ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
