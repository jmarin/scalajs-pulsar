name := "scalajs-pulsar"

version := "1.0"

scalaVersion := "2.13.1"

enablePlugins(ScalaJSPlugin, ScalaJSBundlerPlugin, ScalablyTypedConverterPlugin)

useYarn := true

scalaJSUseMainModuleInitializer := true

// TypeScript dependencies

Compile / npmDependencies ++= Seq(
  "@types/pulsar-client" -> "1.0.2",
  "@types/node" -> "14.0"
)
