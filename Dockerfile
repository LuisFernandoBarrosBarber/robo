FROM adoptopenjdk/openjdk16:ubi
COPY build/libs/robo-0.0.1-SNAPSHOT.jar robo-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/robo-0.0.1-SNAPSHOT.jar"]

