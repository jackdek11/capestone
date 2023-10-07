FROM openjdk:11

# Set and full working directory
WORKDIR /code

# Copy over requirements
COPY src/ /code/

RUN javac Driver.java

ENTRYPOINT java GUI GUI.class