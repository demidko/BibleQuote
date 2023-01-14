FROM gradle:jdk19 as builder
WORKDIR /project
COPY src ./src
COPY build.gradle.kts ./build.gradle.kts
COPY settings.gradle.kts ./settings.gradle.kts
RUN gradle clean build

FROM openjdk:21-bullseye as backend
WORKDIR /root
COPY --from=builder /project/build/libs/*-boot.jar ./app
ENTRYPOINT ["java", "-jar", "--enable-preview", "/root/app"]
