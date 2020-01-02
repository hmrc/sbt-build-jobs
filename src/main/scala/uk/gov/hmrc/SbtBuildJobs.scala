/*
 * Copyright 2020 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc

import sbt.Keys._
import sbt._

object SbtBuildJobs extends sbt.AutoPlugin {

  private val defaultVersionFile          = "/tmp/RELEASE_VERSION"

  object autoImport {
    val releaseFileName = settingKey[String]("Name of the file to use for the 'writeReleaseFile' task")
    val writeReleaseFile = taskKey[Unit](s"Write the release version to the file specified by the 'releaseFileName' setting")
  }

  import autoImport._

  override def trigger = allRequirements

  override lazy val buildSettings = Seq(
    releaseFileName := defaultVersionFile,
    writeReleaseFile := writeReleaseFileTask.value)

  lazy val writeReleaseFileTask =
    Def.task {
      val path = releaseFileName.value
      val file = new File(path)
      streams.value.log.info(s"Writing ${version.value} to file ${file.getAbsolutePath}")
      IO.write(file, version.value)
    }

}
