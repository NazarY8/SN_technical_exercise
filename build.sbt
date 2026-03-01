ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.18"

lazy val root = (project in file("."))
  .settings(
    name := "SN_technical_exercise",
    // By default, sbt runs the program inside its own JVM process.
    // This means stdin is consumed by sbt itself, not by our program.
    // fork := true launches a separate JVM process for `sbt run`,
    // and connectInput := true pipes stdin from the terminal into that process.
    // Without these two settings, `cat data.txt | sbt run` would result in
    // an empty stdin reaching our program.
    run / fork := true,
    run / connectInput := true
  )
