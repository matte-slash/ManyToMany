FROM openjdk:17 as builder
WORKDIR ManyToMany
ARG JAR_FILE=target/*.jar
EXPOSE 8080
COPY ${JAR_FILE} ManyToMany.jar
RUN java -Djarmode=layertools -jar ManyToMany.jar extract     #estrae layers

FROM openjdk:17
WORKDIR ManyToMany
COPY --from=builder ManyToMany/dependencies/ ./
COPY --from=builder ManyToMany/spring-boot-loader ./
COPY --from=builder ManyToMany/snapshot-dependencies ./
COPY --from=builder ManyToMany/application/ ./
ENTRYPOINT ["java","org.springframework.boot.loader.JarLauncher"]