from bifrost_gateway_client import BifrostGatewayClient, SupportedContentType
import unittest

class TestBifrostGatewayClient(unittest.TestCase):

    def setUp(self):
        # Setup the client with primary and fallback URLs
        self.client = BifrostGatewayClient("http://localhost:8080")

    # Test for uploading JSON payload
    def test_upload_json_payload(self):
        target = "target"
        payload = '{"key": "value"}'
        content_type = SupportedContentType.JSON
        response = self.client.upload_payload(target, payload, content_type)

        # Assertions based on expected response
        self.assertIsNotNone(response)
        self.assertEqual(response['status'], "success") # Replace with actual expected response key/value

    # Test for uploading XML payload
    def test_upload_xml_payload(self):
        target = "target"
        payload = '<root><key>value</key></root>'
        content_type = SupportedContentType.XML
        response = self.client.upload_payload(target, payload, content_type)

        # Assertions based on expected response
        self.assertIsNotNone(response)
        self.assertEqual(response['status'], "success") # Replace with actual expected response key/value

    # Test for uploading file
    def test_upload_file(self):
        target = "target"
        file_path = "api/src/test/resources/test_json.json"
        content_type = SupportedContentType.JSON
        response = self.client.upload_file(target, file_path, content_type)

        # Assertions based on expected response
        self.assertIsNotNone(response)
        self.assertEqual(response['status'], "success") # Replace with actual expected response key/value


# Run the tests
if __name__ == "__main__":
    unittest.main()
