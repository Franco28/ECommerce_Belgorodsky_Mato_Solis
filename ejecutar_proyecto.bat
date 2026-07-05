@echo off
setlocal enabledelayedexpansion

set "ROOT=%~dp0"
set "OUT=%ROOT%out"
set "SOURCES=%ROOT%sources.txt"

if exist "%OUT%" rmdir /s /q "%OUT%"
mkdir "%OUT%"

del /q "%SOURCES%" 2>nul
for /r "%ROOT%src\main\java" %%f in (*.java) do (
    echo %%f>>"%SOURCES%"
)

javac -d "%OUT%" @"%SOURCES%"
if errorlevel 1 (
    echo.
    echo Error al compilar.
    exit /b 1
)

java -cp "%OUT%" ecommerce.Main
set "EXIT_CODE=%ERRORLEVEL%"

del /q "%SOURCES%" 2>nul
exit /b %EXIT_CODE%
