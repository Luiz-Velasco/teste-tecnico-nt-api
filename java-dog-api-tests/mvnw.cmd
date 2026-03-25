@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements.  See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership.  The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License.  You may obtain a copy of the License at
@REM
@REM    https://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied.  See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@REM Apache Maven Wrapper startup batch script, version 3.3.2

@SETLOCAL EnableExtensions DisableDelayedExpansion

@REM Auto-detect Temurin/Eclipse Adoptium JDK if JAVA_HOME not set
@IF "%JAVA_HOME%"=="" (
  FOR /D %%D IN ("C:\Program Files\Eclipse Adoptium\jdk-*") DO (
    IF EXIST "%%D\bin\java.exe" SET "JAVA_HOME=%%D"
  )
)
@IF NOT "%JAVA_HOME%"=="" SET "PATH=%JAVA_HOME%\bin;%PATH%"
@SET "JAVA_EXEC=java"
@IF NOT "%JAVA_HOME%"=="" SET "JAVA_EXEC=%JAVA_HOME%\bin\java"

@REM Strip trailing backslash from %~dp0 to form a valid property value
@SET "MAVEN_PROJECTBASEDIR=%~dp0"
@IF "%MAVEN_PROJECTBASEDIR:~-1%"=="\" SET "MAVEN_PROJECTBASEDIR=%MAVEN_PROJECTBASEDIR:~0,-1%"

@SET "WRAPPER_JAR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"

@IF EXIST "%WRAPPER_JAR%" GOTO runWrapper

@ECHO Downloading Maven Wrapper JAR...
@IF NOT EXIST "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\" MKDIR "%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\"

@SET "WRAPPER_URL="
@FOR /F "usebackq tokens=1,* delims==" %%A IN ("%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.properties") DO (
  IF "%%A"=="wrapperUrl" SET "WRAPPER_URL=%%B"
)

@IF "%WRAPPER_URL%"=="" (
  ECHO Could not read wrapperUrl from maven-wrapper.properties
  EXIT /B 1
)

@powershell -Command "(New-Object Net.WebClient).DownloadFile('%WRAPPER_URL%', '%WRAPPER_JAR%')"
@IF ERRORLEVEL 1 (
  ECHO Download failed.
  EXIT /B 1
)

:runWrapper
@"%JAVA_EXEC%" -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%" -classpath "%WRAPPER_JAR%" org.apache.maven.wrapper.MavenWrapperMain %*

@ENDLOCAL
