## 0.4.1 (August 16, 2019)

- Make shadow service account's username pattern configurable.

## 0.4.0 (August 8, 2019)

- Restructured the Java codebase by splitting off some components into separate modules:
  `broker` (the main broker service), `cache-backend-redis`, `database-backend-cloud-datastore`,
  `database-backend-jdbc`, `encryption-backend-cloud-kms`, and `broker-core` (code shared by all the
  aforementioned packages).
- Added support for MySQL and MariaDB database backends (via `JDBCBackend`).
- Modified Session primary key data type from integer to string.
- Add support for pluggable authentication backends, defaults to SPNEGO authentication.

## 0.3.2 (July 25, 2019)

- Made `RefreshTokenProvider` work with all database backends instead of just Cloud Datastore.

## 0.3.1 (July 1, 2019)

- Added `gcp.token.broker.realm` setting in the broker connector to control
  the broker principal's realm.
- Fixed a bug in the Cloud Datastore database backend.

## 0.3.0 (June 28, 2019)

- Added `JDBCDatabaseBackend` to enable storing state in relational databases.
- Added `DummyCache`, `DummyEncryptionBackend`, and an experimental
  `JSONFileCredentialsProvider` to make simple deployments easier.
  These backends are for development and testing only, and should *not*
  be used in production.

## 0.2.0 (May 17, 2019)

- Rewrote server application from Python to Java.
- Simplified KDC trust topology. Now the Broker service does not
  require its own KDC and does not require any connectivity with the
  origin KDC or AD. Also, it is now required to upload a separate
  keytab for each realm so the broker service can authenticate users.

## 0.1.0 (April 5, 2019)

Initial release.