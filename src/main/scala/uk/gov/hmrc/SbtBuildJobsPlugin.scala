/*
 * Copyright 2021 HM Revenue & Customs
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

package uk.gov.hmrc.buildjobs

import sbt.Keys._
import sbt._
import uk.gov.hmrc.sbtsettingkeys.Keys.isPublicArtefact

object SbtBuildJobsPlugin extends sbt.AutoPlugin {

  // Environment variables
  private val ENV_KEY_VERSION_FILENAME            = "VERSION_FILENAME"
  private val ENV_KEY_IS_PUBLIC_ARTEFACT_FILENAME = "IS_PUBLIC_ARTEFACT_FILENAME"

  private val currentVersion = getClass.getPackage.getImplementationVersion // This requires that the class is in a package unique to that build

  private def getRequiredEnvVar(key: String): String =
    sys.env.get(key).getOrElse(sys.error(s"Env var '$key' is not defined"))

  object autoImport {
    val writeVersion =
      taskKey[Unit](s"Write the release version to the file specified by the '$ENV_KEY_VERSION_FILENAME' environment variable")

    val writeIsPublicArtefact =
      taskKey[Unit](s"Write the value of `isPublicArtefact` to the file specified by the '$ENV_KEY_IS_PUBLIC_ARTEFACT_FILENAME' environment variable")
  }

  import autoImport._

  override def trigger = allRequirements

  override lazy val projectSettings = Seq(
    writeVersion          := writeSettingValue(version         , ENV_KEY_VERSION_FILENAME           ).value,
    writeIsPublicArtefact := writeSettingValue(isPublicArtefact, ENV_KEY_IS_PUBLIC_ARTEFACT_FILENAME).value
  )

 private def writeSettingValue[T](settingKey: SettingKey[T], pathKey: String) =
    Def.task {
      val file = new File(getRequiredEnvVar(pathKey))
      val value = settingKey.value.toString
      streams.value.log.info(message(name.value, s"Writing value of $settingKey ('$value') to file ${file.getAbsolutePath}"))
      IO.write(file, value)
    }

  private def message(projectName: String, msg: String): String =
    s"SbtBuildJobsPlugin [$currentVersion] ($projectName) - $msg"
}
