# Containerization of a Microservice Using Docker

This is a simple Java 17 Spring Boot REST microservice. It returns a JSON
message from `GET http://localhost:8081/api/data` and can be packaged in a
Docker image.

## Project structure

```text
app-service/
├── .dockerignore
├── Dockerfile
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── com/example/demo/
        │       ├── DataController.java
        │       └── DemoApplication.java
        └── resources/
            └── application.properties
```

## Prerequisites on Windows

Install the following software before starting:

1. **JDK 17**: install a Java 17 JDK and add its `bin` folder to `PATH`.
2. **Apache Maven**: install Maven and add its `bin` folder to `PATH`.
3. **Docker Desktop**: install it, start Docker Desktop, and wait until it
   reports that the Docker engine is running.
4. **VS Code**: install VS Code. The Extension Pack for Java is recommended
   because it provides Java editing, Maven support, and debugging.

Open PowerShell and check the installations:

```powershell
java -version
mvn -version
docker --version
```

The Java version should be 17 or another Java 17 runtime. Maven and Docker
should each print their installed version.

## Run from VS Code

1. Open VS Code.
2. Select **File > Open Folder...** and choose the project folder.
3. Open the VS Code terminal with **Terminal > New Terminal**.
4. Make sure the terminal is in the folder containing `pom.xml`.
5. Build the executable Spring Boot JAR:

   ```powershell
   mvn clean package
   ```

   `clean` removes old build output. `package` compiles the source code,
   runs tests if present, and creates the executable JAR in `target`.

6. Run the application directly with Maven to test it:

   ```powershell
   mvn spring-boot:run
   ```

7. Open a browser and visit:

   ```text
   http://localhost:8081/api/data
   ```

   The response is:

   ```json
   {
     "message": "Microservice is running successfully!",
     "status": "Success"
   }
   ```

8. Stop the application with `Ctrl+C` in the terminal.

## Build and run with Docker

Run these commands in the VS Code PowerShell terminal from the project root.

### Step 1: Build the Spring Boot application

```powershell
mvn clean package
```

This creates `target/app-service-0.0.1-SNAPSHOT.jar`. The Dockerfile copies
this file into the image, so this step must be completed before the Docker
image is built.

### Step 2: Build the Docker image

```powershell
docker build -t app-service:v1 .
```

`docker build` reads the Dockerfile. `-t app-service:v1` gives the image a
name and tag. The final `.` means that the current folder is the build
context.

### Step 3: Run the Docker container

```powershell
docker run --name app-service-container -p 8081:8081 app-service:v1
```

The `-p 8081:8081` option maps the Windows host port to the container port.
The first value is the host port and the second value is the port exposed by
the Spring Boot application. The container stays attached to the terminal so
that its logs are visible.

### Step 4: Verify the REST API

Open this URL in a browser:

```text
http://localhost:8081/api/data
```

You can also verify it in PowerShell:

```powershell
Invoke-RestMethod http://localhost:8081/api/data
```

## Useful Docker commands

Stop the running container from another terminal:

```powershell
docker stop app-service-container
```

Remove the stopped container:

```powershell
docker rm app-service-container
```

List running containers:

```powershell
docker ps
```

View container logs:

```powershell
docker logs app-service-container
```

If the same container name is already in use, stop and remove the old
container, then run the `docker run` command again.

## Troubleshooting

### `mvn` is not recognized

Install Maven, then add Maven's `bin` directory to the Windows `PATH`.
Close and reopen VS Code after changing `PATH`, then run `mvn -version`.

### Java version is not 17

Install a Java 17 JDK and set `JAVA_HOME` to its installation directory.
Restart VS Code and check with `java -version` and `mvn -version`.

### Docker daemon is unavailable

Start Docker Desktop and wait for the engine to finish starting. Then retry
the Docker command.

### Docker cannot find the JAR file

Run `mvn clean package` from the project root and confirm that
`target/app-service-0.0.1-SNAPSHOT.jar` exists. Then run `docker build` again.

### The host port is already in use

Find and stop the process currently using port 8081, or stop an existing
container that was started with that port mapping:

```powershell
docker ps
docker stop <container-id>
```

### The endpoint returns a connection error

Confirm that the container is running with `docker ps`, inspect its logs with
`docker logs app-service-container`, and verify that the URL is exactly
`http://localhost:8081/api/data`.
