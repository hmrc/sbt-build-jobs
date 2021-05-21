
# sbt-build-jobs

 [ ![Download](https://img.shields.io/github/v/release/hmrc/sbt-build-jobs) ](https://open.artefacts.tax.service.gov.uk/ivy2/uk.gov.hmrc/sbt-build-jobs/scala_2.12/sbt_1.0/)

This plugin provides some helper tasks to support sbt based build-jobs.

## Tasks

### writeReleaseFile

Outputs the value of the `version` setting to the file specified by the `releaseFileName` setting

> The releaseFileName can also be set by the env var `RELEASE_FILENAME`

### writeIsPublicArtefact

Outputs the value of the `isPublicArtefact` setting to the file specified by the environment variable `IS_PUBLIC_ARTEFACT_FILENAME`

### Sbt 1.x

This plugin is cross compiled for sbt `0.13.18` and `1.3.4`

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
