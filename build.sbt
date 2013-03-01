name := "DataStructure"

version := "0.7"

scalaVersion := "2.10.0"

artifactName := { (_, _, _) => "DataStructure.jar" }

scalaSource in Compile <<= baseDirectory(_ / "src" / "main")

scalaSource in Test <<= baseDirectory(_ / "src" / "test")

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10" % "1.9.1" % "test",
  "org.bizzle.tester" % "Tester" % "31b7f32" % "test" from
    "https://www.dropbox.com/s/nf2uwp8mz979rw0/Tester-e875882.jar"
)

mainClass in Compile := None
