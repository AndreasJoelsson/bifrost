from bifrost_gateway_client import BifrostGatewayClient, SupportedContentType
import unittest
import os

class TestBifrostGatewayClient(unittest.TestCase):

    def setUp(self):
        # Setup the client with primary and fallback URLs
        primary_endpoint = os.getenv("PRIMARY_ENDPOINT", "http://localhost:8080")
        self.client = BifrostGatewayClient(primary_endpoint)

    # Test for uploading JSON payload
    def test_upload_json_payload(self):
        target = "target"
        payload = '{"key": "value"}'
        content_type = SupportedContentType.JSON
        response = self.client.upload_payload(target, payload, content_type)

        # Assertions based on expected response
        self.assertIsNotNone(response)
        self.assertEqual(response.status_code, 200)
        self.assertIsNotNone(response.json()['fileName'])
        self.assertIsNotNone(response.json()['bucket'])
        self.assertEqual(response.json()['bucket'], "test-bucket") # Replace with actual expected response key/value

    # Test for uploading XML payload
    def test_upload_xml_payload(self):
        target = "target"
        payload = '<root><key>value</key></root>'
        content_type = SupportedContentType.XML
        response = self.client.upload_payload(target, payload, content_type)

        # Assertions based on expected response
        self.assertIsNotNone(response)
        self.assertEqual(response.status_code, 200)
        self.assertIsNotNone(response.json()['fileName'])
        self.assertIsNotNone(response.json()['bucket'])
        self.assertEqual(response.json()['bucket'], "test-bucket") # Replace with actual expected response key/value

    # Test for uploading file
    def test_upload_file(self):
        target = "target"
        file_path = "resources/test_json.json"
        content_type = SupportedContentType.JSON
        response = self.client.upload_file(target, file_path, content_type)

        # Assertions based on expected response
        self.assertIsNotNone(response)
        self.assertEqual(response.status_code, 200)
        self.assertIsNotNone(response.json()['fileName'])
        self.assertIsNotNone(response.json()['bucket'])
        self.assertIsNotNone(response.json()['originalFilename'])
        self.assertEqual(response.json()['bucket'], "test-bucket") # Replace with actual expected response key/value


# Run the tests
if __name__ == "__main__":
    unittest.main()
