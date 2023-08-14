import requests

# Class to define supported content types
class SupportedContentType:
    JSON = "application/json"  # JSON content type
    XML = "application/xml"    # XML content type
    YAML = "application/yaml"  # YAML content type

# Client class for interacting with the Bifrost Gateway API
class BifrostGatewayClient:

    def __init__(self, primary_url, fallback_url=None):
        self.urls = [primary_url]  # Primary URL for the API
        if fallback_url:
            self.urls.append(fallback_url)  # Optional Fallback URL for the API

    # Internal method to handle HTTP requests with primary and optional fallback URL
    def _request(self, method, url_path, **kwargs):
        for url in [base_url + url_path for base_url in self.urls]:
            try:
                response = method(url, **kwargs)  # Make the HTTP request
                response.raise_for_status()  # Raise exception for 4xx and 5xx status codes
                return response  # Return response if successful
            except requests.RequestException:
                # If it's the last URL in the list, raise the exception
                if url == self.urls[-1] + url_path:
                    raise
        return None

    # Method to upload payload (JSON, XML, YAML) to a target path
    def upload_payload(self, target, payload, content_type: SupportedContentType) -> requests.Response:
        # Check if provided content type is supported
        assert content_type in vars(SupportedContentType).values(), "Unsupported content type!"
        url_path = f"/api/v1/payload/{target}"  # Endpoint path
        headers = {'Content-Type': content_type}  # Content-Type header
        response = self._request(requests.post, url_path, data=payload, headers=headers)  # Make POST request
        return response

    # Method to upload a file to a target path
    def upload_file(self, target, file_path, content_type: SupportedContentType) -> requests.Response:
        # Check if provided content type is supported
        assert content_type in vars(SupportedContentType).values(), "Unsupported content type!"
        url_path = f"/api/v1/object/{target}"  # Endpoint path
        with open(file_path, 'rb') as file_content:
            files = {'file': (file_path, file_content, content_type)}  # File to be uploaded
            response = self._request(requests.post, url_path, files=files)  # Make POST request
        return response
