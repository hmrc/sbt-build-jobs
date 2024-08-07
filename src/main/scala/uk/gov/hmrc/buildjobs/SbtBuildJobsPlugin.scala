/*
 * Copyright 2022 HM Revenue & Customs
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

  private object EnvKeys {
    val versionFilename              = "VERSION_FILENAME"
    val isPublicArtefactFilename     = "IS_PUBLIC_ARTEFACT_FILENAME"
    val projectsFilename             = "PROJECTS_FILENAME"
    val projectsRootFolder           = "PROJECTS_ROOT_FOLDER"
    val unaggregatedProjectsFilename = "UNAGGREGATED_PROJECTS_FILENAME"
  }

  private val currentVersion =
    getClass.getPackage.getImplementationVersion // This requires that the class is in a package unique to that build

  private def getRequiredEnvVar(key: String): String =
    sys.env.getOrElse(key, sys.error(s"Env var '$key' is not defined"))

  object autoImport {
    val writeVersion =
      taskKey[Unit](s"Write the value of `version` to the file specified by the '${EnvKeys.versionFilename}' environment variable")

    val writeIsPublicArtefact =
      taskKey[Unit](s"Write the value of `isPublicArtefact` to the file specified by the '${EnvKeys.isPublicArtefactFilename}' environment variable")

    val writeMeta =
      taskKey[Unit](s"Write meta information to meta.yaml in each project's target directory")

    val writeProjects =
      taskKey[Unit](s"Write project information to the file specified by the '${EnvKeys.projectsFilename}' environment variable. The root folder of the project is written as `_ROOT_` but can be modified with `${EnvKeys.projectsRootFolder}`")

    val writeUnaggregatedProjects =
      taskKey[Unit](s"Write unaggregated subprojects to the file specified by the '${EnvKeys.unaggregatedProjectsFilename}' environment variable")

  }

  import autoImport._

  override def trigger = allRequirements

  override lazy val projectSettings = Seq(
    writeVersion          := writeSettingValue(version         , EnvKeys.versionFilename         ).value,
    writeIsPublicArtefact := writeSettingValue(isPublicArtefact, EnvKeys.isPublicArtefactFilename).value,
    writeMeta             := writeMetaFile().value,
    writeProjects         := writeTaskOutput(
                               { // pwd is the best way to identify the root module, since things like baseDirectory will change per module execution
                                 val pwd  = {import scala.sys.process._; "pwd" !!}.trim
                                 val root = sys.env.getOrElse(EnvKeys.projectsRootFolder, "_ROOT_")
                                 buildStructure.map(
                                   _.allProjects
                                    .map(r =>
                                      s"""|- module: ${r.id}
                                          |  folder: ${if (r.base.getAbsolutePath == pwd) root else r.base.getName}
                                          |  aggregates:${if (r.aggregate.isEmpty) " []" else r.aggregate.sortBy(_.project).map("\n    - " ++ _.project).mkString}
                                          |  root: ${r.base.getAbsolutePath == pwd}""".stripMargin
                                    ).sorted
                                     .mkString("", "\n", "\n")
                                 )
                               },
                               EnvKeys.projectsFilename
                             ).value,
     writeUnaggregatedProjects := writeTaskOutput(
                                    buildStructure.map { x =>
                                      val pwd = {import scala.sys.process._; "pwd" !!}.trim
                                      val aggregatedProjects = x.allProjects.flatMap(_.aggregate).distinct.map(_.project)
                                      x.allProjects
                                      .filterNot(p => aggregatedProjects.contains(p.id) || p.base.getAbsolutePath == pwd)
                                      .map(_.id)
                                      .mkString("", "\n", "\n")
                                    },
                                    EnvKeys.unaggregatedProjectsFilename
                                  ).value
  )

  private def writeSettingValue[T](settingKey: SettingKey[T], pathKey: String) =
    Def.task {
      val file = new File(getRequiredEnvVar(pathKey))
      val value = settingKey.value.toString
      streams.value.log.info(message(name.value, s"Writing value of $settingKey ('$value') to file ${file.getAbsolutePath}"))
      IO.write(file, value + "\n")
    }

  private def writeMetaFile() =
    Def.task {
      val value =
        s"""|name              : "${name.value}"
            |organization      : "${organization.value}"
            |version           : "${version.value}"
            |sbtVersion        : "${sbtVersion.value}"
            |crossScalaVersions: ${crossScalaVersions.value.map(v => s""""$v"""").mkString("[", ",", "]")}
            |publish_skip      : ${(publish / skip).value}
            |""".stripMargin

      val file = new File(s"${target.value}/meta.yaml")
      streams.value.log.info(message(name.value, s"Writing '$value' to file ${file.getAbsolutePath}"))
      IO.write(file, value)
    }

  private def writeTaskOutput[T](task: Def.Initialize[Task[String]], pathKey: String) =
    Def.task {
      val file = new File(getRequiredEnvVar(pathKey))
      val value = task.value.toString
      streams.value.log.info(message(name.value, s"Writing task output to file ${file.getAbsolutePath}"))
      IO.write(file, value)
    }

  private def message(projectName: String, msg: String): String =
    s"SbtBuildJobsPlugin [$currentVersion] ($projectName) - $msg"
}
