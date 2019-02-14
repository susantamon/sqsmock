name := "sqsmock"

version := "1.0.0"

organization := "io.mock.aws"

scalaVersion := "2.12.4"

val akkaVersion = "2.5.11"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,
  "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
  "com.typesafe.akka" %% "akka-http" % "10.1.0",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.5",
  "joda-time" % "joda-time" % "2.9.4",
  "org.scalatest" %% "scalatest" % "3.0.0" % "test",
  "com.amazonaws" % "aws-java-sdk-sqs" % "1.11.488" % "test"
)

licenses += ("MIT", url("https://opensource.org/licenses/MIT"))

parallelExecution in Test := false