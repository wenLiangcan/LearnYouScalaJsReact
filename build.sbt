enablePlugins(ScalaJSPlugin)

name := "LearnYouScalaJsReact"

version := "1.0"

scalaVersion := "2.11.8"

updateOptions := updateOptions.value.withCachedResolution(true)

emitSourceMaps := true
persistLauncher := true

libraryDependencies += "com.github.japgolly.scalajs-react" %%% "core" % "0.10.4"
libraryDependencies += "com.github.japgolly.scalacss" %%% "core" % "0.4.0"
libraryDependencies += "com.github.japgolly.scalacss" %%% "ext-react" % "0.4.0"

jsDependencies ++= Seq(
    "org.webjars.bower" % "react" % "0.14.3"
        / "react-with-addons.js"
        minified "react-with-addons.min.js"
        commonJSName "React",

    "org.webjars.bower" % "react" % "0.14.3"
        / "react-dom.js"
        minified "react-dom.min.js"
        dependsOn "react-with-addons.js"
        commonJSName "ReactDOM")

val generatedDir = file("./src/main/resources/generated")
crossTarget in (Compile, fullOptJS) := generatedDir
crossTarget in (Compile, fastOptJS) := generatedDir
crossTarget in (Compile, packageJSDependencies) := generatedDir
crossTarget in (Compile, packageScalaJSLauncher) := generatedDir
crossTarget in (Compile, packageMinifiedJSDependencies) := generatedDir
artifactPath in (Compile, fastOptJS) :=
    file((crossTarget in (Compile, fastOptJS)).value / (moduleName in fastOptJS).value + "-opt.js")


