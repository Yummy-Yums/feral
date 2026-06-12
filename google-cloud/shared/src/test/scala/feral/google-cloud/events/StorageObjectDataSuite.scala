package feral.`google-cloud`.events

import munit.FunSuite
import io.circe.literal._

class StorageObjectDataSuite extends FunSuite {

    import StorageObjectDataSuite._

    test("decoder"){
        event.as[StorageObjectData].toTry.get
    }

}

object StorageObjectDataSuite {
    def event = json"""
        {
            "bucket": "some-bucket",
            "cacheControl": "",
            "componentCount": 0,
            "contentDisposition": "",
            "contentEncoding": "",
            "contentLanguage": "",
            "contentType": "text/plain",
            "crc32c": "rTVTeQ==",
            "customerEncryption": {
                "encryptionAlgorithm": "AES256",
                "keySha256": "abc123"
            },
            "etag": "CNHZkbuF/ugCEAE=",
            "eventBasedHold": false,
            "generation": 12345,
            "id": "some-bucket/folder/Test.cs/1587627537231057",
            "kind": "storage#object",
            "kmsKeyName": "",
            "md5Hash": "kF8MuJ5+CTJxvyhHS1xzRg==",
            "mediaLink": "https://www.googleapis.com/download/storage/v1/b/some-bucket/o/folder%2FTest.cs?generation=1587627537231057\u0026alt=media",
            "metadata": {},
            "metageneration": 1,
            "name": "folder/Test.cs",
            "retentionExpirationTime": "",
            "selfLink": "https://www.googleapis.com/storage/v1/b/some-bucket/o/folder/Test.cs",
            "size": 352,
            "storageClass": "MULTI_REGIONAL",
            "temporaryHold": false,
            "timeCreated": "2020-04-23T07:38:57.230Z",
            "timeDeleted": "",
            "timeStorageClassUpdated": "2020-04-23T07:38:57.230Z",
            "updated": "2020-04-23T07:38:57.230Z"
        }
    """
}
