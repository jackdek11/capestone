FROM openjdk:11

# Set and full working directory
WORKDIR /code
ENV DEBIAN_FRONTEND='noninteractive'

# Copy over requirements
COPY ./ /code/

ENV DISPLAY=:0

RUN apt update -y
RUN apt-get -y install libxrender1 libxtst6 libxi6

RUN javac Driver.java

ENTRYPOINT java GUI GUI.class