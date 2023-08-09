## API Module

The API module serves as the primary interface for interacting with the Bifrost Gateway. It provides endpoints for handling S3 objects and payloads, and it's organized into the following components:

### Bifrost Gateway Application

The main entry point for the application, responsible for initializing and running the Spring Boot application. It sets up the necessary beans and scans the base packages for components.

### Gateway Object Controller

This controller is responsible for operations related to S3 objects, such as:

- **Uploading Files**: Provides an endpoint for uploading one or multiple images for a given source.
- **Logging**: Includes detailed logging for tracking file uploads and responses.
- **Error Handling**: Implements comprehensive error handling for various response codes.

### Gateway Payload Controller

This controller handles operations to store payloads to S3 or Kafka, with features including:

- **Writing Payloads**: Supports writing JSON, XML, or YAML to the target path, allowing for versatile data storage.
- **Endpoint Configuration**: The endpoint is configured to consume various media types, including JSON, XML, and YAML, and produces JSON responses.
- **Response Handling**: Includes detailed response handling, with specific cases for OK, BAD_REQUEST, and INTERNAL_SERVER_ERROR statuses.
- **Logging**: Detailed logging is included to track payload uploads and responses.
- **Error Handling**: Implements comprehensive error handling, including throwing an InternalServerError exception when needed.

### Conclusion

The API module is a critical part of the Bifrost Gateway, providing robust and flexible endpoints for handling S3 objects and payloads. With comprehensive logging, error handling, and support for various media types, it offers a versatile interface for interacting with the underlying services.
