FROM node as frontend
WORKDIR /project
COPY frontend/dist/index.html ./dist/index.html
COPY frontend/src ./src
COPY frontend/package.json ./package.json
COPY frontend/package-lock.json ./package-lock.json
COPY frontend/webpack.config.js ./webpack.config.js
RUN npm ci && npm run build

FROM gradle:jdk19 as backend
WORKDIR /project
COPY src ./src
COPY build.gradle.kts ./build.gradle.kts
COPY settings.gradle.kts ./settings.gradle.kts
COPY --from=frontend /project/dist ./src/main/resources/public
RUN gradle clean build

FROM openjdk:21-bullseye as application
WORKDIR /root
COPY --from=backend /project/build/libs/*-boot.jar ./app
ENTRYPOINT ["java", "-jar", "--enable-preview", "/root/app"]
