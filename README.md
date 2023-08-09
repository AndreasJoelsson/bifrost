# Bifrost Gateway

## Introduction

Bifrost Gateway is a project that facilitates operations related to S3 buckets, including uploading objects and handling various services. The project is organized into different modules, including API, client, contract, and core functionalities.

## Modules

### API

The API module contains the main application and controllers for handling requests and responses. Additional information is found [here](docs/api.md).

#### Bifrost Gateway Application

The main entry point for the application, responsible for initializing and running the Spring Boot application.

#### Gateway Object Controller

This controller handles operations related to S3 objects, such as uploading files to specific targets. It provides endpoints for uploading one or multiple images for a given source and includes detailed logging and error handling.

#### Gateway Payload Controller

This controller is responsible for operations to store payloads to S3 or Kafka. It provides an endpoint for writing JSON, XML, or YAML to the target path and includes detailed logging and error handling.

### Client

The client module contains interfaces and implementations for client-side interactions with the gateway.

#### Object Client

An interface that defines methods for uploading objects to S3 buckets. (Note: Some code is commented out in the current version.)

### Core

The core module includes essential functionalities, configurations, and services. Additional information can be found [here](docs/core.md).

#### Context Interface

Defines methods for publishing objects to specific targets, including handling different media types and input streams.

### Contract Module

The Contract module in the Bifrost Gateway project serves as a foundational layer, defining the shared data structures, responses, exceptions, and other common elements used across the system. It ensures consistency and clear communication between different parts of the application. See additional information [here](docs/contract.md).

#### Key Components

- **Responses**: Defines classes for S3 object and payload responses.
- **Exceptions**: Includes custom exceptions for handling specific error scenarios, such as BadRequest, InternalServerError, Forbidden, and NotFound.
- **Error Message**: Provides a structured way to represent error details.
- **POJOs**: Contains Plain Old Java Objects (POJOs) for encapsulating details like Minio headers and object write responses.

The Contract module plays a vital role in maintaining a well-structured and understandable interface across the Bifrost Gateway. For a detailed overview, please refer to the [Contract Module README](path/to/contract/README.md).

## Build and Run Fat JAR Script (`build-and-run-fat-jar.sh`)

This script is used to compile and run the Bifrost Gateway project locally as a standalone JAR. It also provides an option to set a local profile.

### Usage

- **Compile and Run**: `./build-and-run-fat-jar.sh`
- **Compile and Run with Local Profile**: `./build-and-run-fat-jar.sh --local`

### Details

1. **Compile and Install**: Executes Maven to clean, compile, and install the project, including running tests.
   ```bash
   ./mvnw clean install -DskipTests=false
   ```
2. **Set Profile (Optional)**: If the script is run with the -l or --local flag, it sets the Spring profile to "local" and configures the Spring config location.
   ```bash
   export SPRING_CONFIG_NAME="application-local"
   export SPRING_CONFIG_LOCATION=file:${PWD}/
   ```
3. **Set Application Configuration**: Exports the path to the test-config.yml file as an environment variable.
   ```bash
   export APP_CONFIG="$PWD/test-config.yml"
   ```
4. **Run the Application**: Starts the application by running the compiled JAR file.
   ```bash
   java -jar api/target/api-0.0.1-SNAPSHOT.jar
   ```

### Example Output

The script will print the path to the test-config.yml file:

## Bifrost Gateway Python Client (`bifrost_gateway_client.py`)

This Python client provides a convenient way to interact with the Bifrost Gateway's REST API. It supports various content types and includes methods for uploading payloads and files.

### Supported Content Types

The `SupportedContentType` class defines the content types supported by the client:

- **JSON**: `application/json`
- **XML**: `application/xml`
- **YAML**: `application/yaml`

### BifrostGatewayClient Class

#### Initialization

```python
client = BifrostGatewayClient(primary_url="http://localhost:8080", fallback_url="http://fallback-url.com")
```

 * primary_url: The primary URL for the API.
 * fallback_url (Optional): An optional fallback URL for the API.

#### Methods

```python
_request(method, url_path, **kwargs)
```

An internal method to handle HTTP requests with the primary and optional fallback URL.

 * method: The HTTP method (e.g., requests.post).
 * url_path: The endpoint path.
 * kwargs: Additional keyword arguments for the request.

```python
upload_payload(target, payload, content_type) -> requests.Response
```

Uploads a payload (JSON, XML, YAML) to a target path.

 * target: The target path.
 * payload: The payload data.
 * content_type: The content type (e.g., SupportedContentType.JSON).

```python
upload_file(target, file_path, content_type) -> requests.Response
```

Uploads a file to a target path.

 * target: The target path.
 * file_path: The path to the file to be uploaded.
 * content_type: The content type (e.g., SupportedContentType.JSON).

#### Error Handling

The client raises exceptions for 4xx and 5xx status codes.
It checks if the provided content type is supported, raising an assertion error if not.

#### Example Usage

```python
from bifrost_gateway_client import BifrostGatewayClient, SupportedContentType

client = BifrostGatewayClient(primary_url="http://localhost:8080")
response = client.upload_payload(target="example", payload="{\"key\":\"value\"}", content_type=SupportedContentType.JSON)
```

This client simplifies the process of interacting with the Bifrost Gateway's REST API from Python applications, providing a clear and concise interface for uploading payloads and files.


