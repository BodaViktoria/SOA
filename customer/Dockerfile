FROM amazoncorretto:21-alpine3.17 AS base
ENV APP_HOME=/usr/app
WORKDIR $APP_HOME

FROM base AS prepare
ENV APP_HOME=/usr/app/
WORKDIR $APP_HOME

USER root

# Download gradle
RUN apk add unzip && \
  mkdir /usr/gradle && \
  wget -P /usr/gradle https://services.gradle.org/distributions/gradle-8.4-bin.zip && \
  unzip /usr/gradle/gradle-8.4-bin.zip -d /usr/gradle
ENV GRADLE_HOME=/usr/gradle/gradle-8.4
ENV PATH=$PATH:$GRADLE_HOME/bin

FROM prepare AS download-dependencies

COPY ./build.gradle ./settings.gradle $APP_HOME

COPY ./build.gradle $APP_HOME/build.gradle
COPY ./settings.gradle $APP_HOME/settings.gradle

RUN gradle clean build --no-daemon > /dev/null 2>&1 || true

FROM download-dependencies AS build

COPY ./src $APP_HOME/src

RUN gradle bootJar

FROM base as deploy
WORKDIR $APP_HOME
COPY --from=build $APP_HOME/build/libs/customer-0.0.1-SNAPSHOT.jar $APP_HOME/customer-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "customer-0.0.1-SNAPSHOT.jar"]
