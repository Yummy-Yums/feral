package feral.`google-cloud`.events

import io.circe._
import io.circe.generic.semiauto.deriveDecoder

sealed abstract class StorageObjectData {
  def contentEncoding: String
  def contentDisposition: String
  def cacheControl: String
  def contentLanguage: String
  def metageneration: Int
  def timeDeleted: String
  def contentType: String
  def size: Int
  def timeCreated: String
  def crc32c: String
  def componentCount: Int
  def md5Hash: String
  def etag: String
  def updated: String
  def storageClass: String
  def kmsKeyName: String
  def timeStorageClassUpdated: String
  def temporaryHold: Boolean
  def retentionExpirationTime: String
  def metadata: Map[String, String]
  def eventBasedHold: Boolean
  def name: String
  def id: String
  def bucket: String
  def generation: Int
  def customerEncryption: CustomerEncryption
  def mediaLink: String
  def selfLink: String
  def kind: String
}

object StorageObjectData {

  def apply(
      contentEncoding: String,
      contentDisposition: String,
      cacheControl: String,
      contentLanguage: String,
      metageneration: Int,
      timeDeleted: String,
      contentType: String,
      size: Int,
      timeCreated: String,
      crc32c: String,
      componentCount: Int,
      md5Hash: String,
      etag: String,
      updated: String,
      storageClass: String,
      kmsKeyName: String,
      timeStorageClassUpdated: String,
      temporaryHold: Boolean,
      retentionExpirationTime: String,
      metadata: Map[String, String],
      eventBasedHold: Boolean,
      name: String,
      id: String,
      bucket: String,
      generation: Int,
      customerEncryption: CustomerEncryption,
      mediaLink: String,
      selfLink: String,
      kind: String
  ): StorageObjectData = {
    new Impl(
      contentEncoding,
      contentDisposition,
      cacheControl,
      contentLanguage,
      metageneration,
      timeDeleted,
      contentType,
      size,
      timeCreated,
      crc32c,
      componentCount,
      md5Hash,
      etag,
      updated,
      storageClass,
      kmsKeyName,
      timeStorageClassUpdated,
      temporaryHold,
      retentionExpirationTime,
      metadata,
      eventBasedHold,
      name,
      id,
      bucket,
      generation,
      customerEncryption,
      mediaLink,
      selfLink,
      kind
    )
  }

  implicit val decoder: Decoder[StorageObjectData] = deriveDecoder[Impl].map(identity)

  private final case class Impl(
      contentEncoding: String,
      contentDisposition: String,
      cacheControl: String,
      contentLanguage: String,
      metageneration: Int,
      timeDeleted: String,
      contentType: String,
      size: Int,
      timeCreated: String,
      crc32c: String,
      componentCount: Int,
      md5Hash: String,
      etag: String,
      updated: String,
      storageClass: String,
      kmsKeyName: String,
      timeStorageClassUpdated: String,
      temporaryHold: Boolean,
      retentionExpirationTime: String,
      metadata: Map[String, String],
      eventBasedHold: Boolean,
      name: String,
      id: String,
      bucket: String,
      generation: Int,
      customerEncryption: CustomerEncryption,
      mediaLink: String,
      selfLink: String,
      kind: String
  ) extends StorageObjectData {
    override def productPrefix = "StorageObjectData"
  }
}

sealed abstract class CustomerEncryption {
  def encryptionAlgorithm: String
  def keySha256: String
}

object CustomerEncryption {

  def apply(
      encryptionAlgorithm: String,
      keySha256: String
  ): CustomerEncryption = {
    new Impl(encryptionAlgorithm, keySha256)
  }

  implicit val decoder: Decoder[CustomerEncryption] =
    Decoder.forProduct2("encryptionAlgorithm", "keySha256")(
      CustomerEncryption.apply
    )

  private final case class Impl(encryptionAlgorithm: String, keySha256: String)
      extends CustomerEncryption {
    override def productPrefix = "CustomerEncryption"
  }
}
