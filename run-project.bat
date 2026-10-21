@echo off
setlocal
cd /d "%~dp0"

echo Building Parent-Teacher Companion...
call mvnw.cmd clean package
if errorlevel 1 (
    echo.
    echo Build failed. Fix the errors above, then run this file again.
    pause
    exit /b 1
)

echo.
echo Starting the application...
echo The login page will open automatically at http://localhost:8082
call mvnw.cmd spring-boot:run
