## Contract Module

The Contract module defines the data structures, responses, and exceptions that are used across the Bifrost Gateway project. It serves as a shared contract that ensures consistency and understanding between different parts of the system. Here's an in-depth look at the key components:

### Responses

#### S3ObjectResponse

This class represents the response for an S3 object operation. It includes details such as:

- **Received Time**: The time when the object was received.
- **Stored Time**: The time when the object was stored.
- **File Name**: The name of the file.
- **Original Filename**: The original name of the file.
- **Bucket**: The S3 bucket where the object is stored.

#### S3PayloadResponse

Similar to `S3ObjectResponse`, this class represents the response for an S3 payload operation, including the same details.

### Exceptions

The Contract module defines several custom exceptions to handle specific error scenarios:

#### BadRequestException

Represents a 400 Bad Request error, including an associated error message.

#### InternalServerError

Represents a 500 Internal Server Error, including details about the underlying exception or a custom message.

#### ForbiddenException

Represents a 403 Forbidden error, including an associated error message.

#### NotFoundException

Represents a 404 Not Found error, including an associated error message.

### Error Message

The `ErrorMessage` class provides a structured way to represent error details, including:

- **Status**: The HTTP status code.
- **Code**: An internal error code.
- **Message**: A user-friendly error message.
- **Developer Message**: A developer-friendly error message.
- **Link**: A link to more information about the error.

### POJOs

#### MinioHeaders

Represents the headers associated with a Minio operation, encapsulating them in a map.

#### MinioObjectWriteResponse

Represents the response from a Minio object write operation, including details such as:

- **ETag**: The ETag of the object.
- **Version ID**: The version ID of the object.
- **Bucket**: The bucket where the object is stored.
- **Object**: The object's key.
- **Region**: The region where the object is stored.
- **Headers**: The associated headers.

### Conclusion

The Contract module plays a crucial role in defining the shared structures and behaviors across the Bifrost Gateway project. By encapsulating responses, exceptions, and other common elements, it ensures a consistent and well-understood interface for all interacting components.
