# Bifrost Python Gateway Client

This Python module provides a client for interacting with the Bifrost Gateway API. The client supports uploading payloads and files with specific content types.

## Features:
Support for different content types: JSON, XML, and YAML.
Capability to handle primary and fallback URLs for resilient API interactions.
Methods to upload payloads and files to the Bifrost Gateway API.

## Installation:
Make sure you have the requests library installed.

```bash
pip install requests
```

## Usage:

### Supported Content Types:

The module defines supported content types through the SupportedContentType class:

```python
from bifrost_gateway_client import SupportedContentType

print(SupportedContentType.JSON)  # Outputs: "application/json"
```

### Bifrost Gateway Client:

To use the BifrostGatewayClient, first, initialize it with a primary URL. An optional fallback URL can also be provided:

```python
from bifrost_gateway_client import BifrostGatewayClient

client = BifrostGatewayClient(primary_url="https://api.example.com", fallback_url="https://backup-api.example.com")
```

#### Uploading a Payload:

To upload a payload (e.g., a JSON, XML, or YAML string):

```python
response = client.upload_payload(target="data-target", payload='{"key": "value"}', content_type=SupportedContentType.JSON)
```

#### Uploading a File:
To upload a file (e.g., a JSON, XML, or YAML file):

```python
response = client.upload_file(target="file-target", file_path="path/to/your/file.json", content_type=SupportedContentType.JSON)
```

## Error Handling:

If there's an issue with the request, especially with the last URL (or only URL) in the list, the client will raise a `requests.RequestException`.

# Use The Client

## 1. Direct Copy-Paste:

### a. Copy the Code:
Copy the entire BifrostGatewayClient class along with the SupportedContentType class from the provided code.

### b. Paste into Your Existing Code:
Paste the copied code at the top of your existing Python script.

### c. Use the Client:
Now, in the same script, you can initialize and use the BifrostGatewayClient as demonstrated in the README:

```python
client = BifrostGatewayClient(primary_url="https://api.example.com")
response = client.upload_payload(target="data-target", payload='{"key": "value"}', content_type=SupportedContentType.JSON)
```

## 2. As a Separate Package/Module: (TBD work in progress)

### a. Create a New Python File:

Create a new file named bifrost_gateway_client.py in the same directory as your main script.

### b. Move the Code:
Copy and paste the entire BifrostGatewayClient class and the SupportedContentType class into this new file.

### c. Import in Your Main Script:
In your main script, import the client:

```python
from bifrost_gateway_client import BifrostGatewayClient, SupportedContentType
```

### d. Use the Client:

Now, in your main script, you can initialize and use the BifrostGatewayClient:

```python
client = BifrostGatewayClient(primary_url="https://api.example.com")
response = client.upload_payload(target="data-target", payload='{"key": "value"}', content_type=SupportedContentType.JSON)
```

This approach will help in keeping your code organized and will make the BifrostGatewayClient reusable across different scripts or projects.