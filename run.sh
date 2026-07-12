#!/bin/bash
# Runs RPGamesEngine on Apple Silicon via Rosetta 2, using LWJGL2's x86_64 macOS natives.
set -e
cd "$(dirname "$0")"

JAVA_HOME_X64="/Library/Java/JavaVirtualMachines/temurin-8-x64.jdk/Contents/Home"
JAVA="$JAVA_HOME_X64/bin/java"
CP="bin:lib/jars/lwjgl.jar:lib/jars/lwjgl_util.jar:lib/jars/slick-util.jar:lib/jars/PNGDecoder(1).jar"

mkdir -p bin
"$JAVA_HOME_X64/bin/javac" -cp "$CP" -d bin $(find src -name "*.java")

arch -x86_64 "$JAVA" \
  -Djava.library.path=lib/natives-macos \
  -cp "$CP" \
  dev.prince.rpgGameEngine.Launcher
