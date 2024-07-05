
# sbt-build-jobs

 [ ![Download](https://img.shields.io/github/v/release/hmrc/sbt-build-jobs) ](https://open.artefacts.tax.service.gov.uk/ivy2/uk.gov.hmrc/sbt-build-jobs/scala_2.12/sbt_1.0/)

This plugin provides some helper tasks to support sbt based build-jobs.

## Tasks

### writeVersion

Outputs the value of the `version` setting to the file specified by the environment variable `VERSION_FILENAME`

### writeIsPublicArtefact

Outputs the value of the `isPublicArtefact` setting (see [sbt-setting-keys](https://github.com/hmrc/sbt-setting-keys/) to the file specified by the environment variable `IS_PUBLIC_ARTEFACT_FILENAME`

### writeMeta

Writes meta information to `meta.yaml` in each project's target directory

### writeProjects

Writes some project information in yaml format to the file specified by the environment variable `PROJECTS_FILENAME` - The root folder of the project is written as `_ROOT_` but can be modified with `PROJECTS_ROOT_FOLDER`


### Sbt 1.x

This plugin is compiled for sbt `1.x`


### Tests

run `sbt ^test ^publishLocal ^scripted`

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
