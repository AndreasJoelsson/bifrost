from bifrost_gateway_client import BifrostGatewayClient, SupportedContentType
import unittest
import os
from requests.exceptions import HTTPError
from parameterized import parameterized


class TestBifrostGatewayClient(unittest.TestCase):

    def setUp(self):
        # Setup the client with primary and fallback URLs
        primary_endpoint = os.getenv("PRIMARY_ENDPOINT", "http://localhost:8080")
        self.client = BifrostGatewayClient(primary_endpoint)
    """
    @parameterized.expand([
            ('{"key": "value"}', SupportedContentType.JSON),
            ('<root><key>value</key></root>', SupportedContentType.XML),
            ('key: value', SupportedContentType.YAML),
        ])
    def test_upload_payload(self, payload, content_type):
        target = "target"
        response = self.client.upload_payload(target, payload, content_type)

        # Assertions based on expected response
        self.assertIsNotNone(response)
        self.assertEqual(response.status_code, 200)
        self.assertIsNotNone(response.json()['fileName'])
        self.assertIsNotNone(response.json()['bucket'])
        self.assertEqual(response.json()['bucket'], "test-bucket") # Replace with actual expected response key/value

    @parameterized.expand([
            ("resources/test_json.json", SupportedContentType.JSON),
            ("resources/test_yaml.yml", SupportedContentType.YAML),
            ("resources/test_xml.xml", SupportedContentType.XML),
        ])
    def test_upload_file(self, file_path, content_type):
        target = "target"
        response = self.client.upload_file(target, file_path, content_type)

        # Assertions based on expected response
        self.assertIsNotNone(response)
        self.assertEqual(response.status_code, 200)
        self.assertIsNotNone(response.json()['fileName'])
        self.assertIsNotNone(response.json()['bucket'])
        self.assertIsNotNone(response.json()['originalFilename'])
        self.assertEqual(response.json()['bucket'], "test-bucket") # Replace with actual expected response key/value


    @parameterized.expand([
        ('key: value', SupportedContentType.JSON),
        ('key: value', SupportedContentType.XML),
        ('{"key": "value"}', SupportedContentType.XML),
        ('<root><key>value</key></root>', SupportedContentType.JSON)
    ])
    def test_upload_with_wrong_content_type(self, payload, content_type):
        target = "target"

        with self.assertRaises(HTTPError) as context:
            response = self.client.upload_payload(target, payload, content_type)

        self.assertEqual(context.exception.response.status_code, 400)
        self.assertEqual(context.exception.response.json()['detail'], "Failed to read request")
    """
    def test_upload_file_with_wrong_content_type(self):
        file_path = "resources/test_json.json"
        content_type = SupportedContentType.XML
        target = "target"

        with self.assertRaises(HTTPError) as context:
            response = self.client.upload_file(target, file_path, content_type)

        self.assertEqual(context.exception.response.status_code, 400)
        self.assertEqual(context.exception.response.json()['detail'], "Failed to read request")



# Run the tests
if __name__ == "__main__":
    unittest.main()
