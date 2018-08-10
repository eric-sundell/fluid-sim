#!/bin/sh

class_dir=classes
jar_name=WaterSim.jar

rm -rf "$class_dir"

mkdir "$class_dir"

find src -type f -iname '*.java' -exec javac -d "$class_dir" '{}' \+

jar cfm "$jar_name" manifest.txt -C "$class_dir" water
