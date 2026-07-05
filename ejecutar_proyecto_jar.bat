@echo off
setlocal

set "SCRIPT_DIR=%~dp0"
set "JAR_FILE=build/ecommerce-belgorodsky-mato-solis-final-1.0.0.jar"

if exist "%SCRIPT_DIR%%JAR_FILE%" (
    set "JAR=%SCRIPT_DIR%%JAR_FILE%"
) else if exist "%SCRIPT_DIR%build\%JAR_FILE%" (
    set "JAR=%SCRIPT_DIR%build\%JAR_FILE%"
) else (
    echo No se encontro el JAR ejecutable en:
    echo %SCRIPT_DIR%%JAR_FILE%
    echo %SCRIPT_DIR%build\%JAR_FILE%
    echo Primero ejecuta generar_jar.bat
    exit /b 1
)

where java >nul 2>nul
if errorlevel 1 (
    echo Java no esta instalado o no esta agregado al PATH.
    exit /b 1
)

java -jar "%JAR%"
exit /b %ERRORLEVEL%
