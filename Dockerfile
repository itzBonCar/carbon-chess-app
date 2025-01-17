# Stage 1: Build the application
FROM maven:3.9.0-eclipse-temurin-19 AS build
WORKDIR /app

# Copy the entire project to the container
COPY . .

# Build the WAR file
RUN mvn clean package -DskipTests && \
    rm -rf src/ && \
    rm -rf ~/.m2/repository && \
    rm -rf pom.xml && \
    rm -rf /tmp/*

# Stage 2: Run the application on Tomcat
FROM tomcat:10.1.34-jre17-temurin-noble
WORKDIR /usr/local/tomcat

# Remove default ROOT web app to avoid conflicts (optional)
RUN rm -rf webapps/ROOT

# Copy the built WAR file from the build stage to the Tomcat webapps directory
COPY --from=build /app/target/carbon-chess-app-0.0.1-SNAPSHOT.war webapps/ROOT.war


# Expose Tomcat's default HTTP port
EXPOSE 8080

# Run Tomcat server
CMD ["catalina.sh", "run"]
