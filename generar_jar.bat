@echo off
setlocal

set "SCRIPT_DIR=%~dp0"
if exist "%SCRIPT_DIR%pom.xml" (
    set "PROJECT_DIR=%SCRIPT_DIR%"
) else (
    set "PROJECT_DIR=%SCRIPT_DIR%.."
)

pushd "%PROJECT_DIR%" >nul

where mvn >nul 2>nul
if errorlevel 1 (
    echo Maven no esta instalado o no esta agregado al PATH.
    echo Instala Maven para poder generar el JAR ejecutable.
    popd >nul
    exit /b 1
)

mvn clean package -DskipTests
set "EXIT_CODE=%ERRORLEVEL%"

popd >nul
exit /b %EXIT_CODE%
