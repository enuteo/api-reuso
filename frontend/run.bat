@echo off
REM ============================================================
REM  Forge - Workshop Management System
REM  Build & run helper (Windows)
REM ============================================================
setlocal ENABLEDELAYEDEXPANSION

set ACTION=%1
if "%ACTION%"=="" set ACTION=up

echo.
echo === Forge Workshop ^| action: %ACTION% ===
echo.

REM --- Verify Docker is available ---
where docker >nul 2>nul
if errorlevel 1 (
    echo [ERROR] Docker is not installed or not in PATH.
    echo Install Docker Desktop: https://www.docker.com/products/docker-desktop
    exit /b 1
)

docker info >nul 2>nul
if errorlevel 1 (
    echo [ERROR] Docker daemon is not running. Please start Docker Desktop.
    exit /b 1
)

if /I "%ACTION%"=="build" goto BUILD
if /I "%ACTION%"=="up"    goto UP
if /I "%ACTION%"=="run"   goto UP
if /I "%ACTION%"=="start" goto UP
if /I "%ACTION%"=="down"  goto DOWN
if /I "%ACTION%"=="stop"  goto DOWN
if /I "%ACTION%"=="logs"  goto LOGS
if /I "%ACTION%"=="rebuild" goto REBUILD
if /I "%ACTION%"=="clean" goto CLEAN
if /I "%ACTION%"=="help"  goto HELP
if /I "%ACTION%"=="/?"    goto HELP

echo [ERROR] Unknown action: %ACTION%
goto HELP

:BUILD
echo [1/1] Building Docker image...
docker compose build
if errorlevel 1 goto FAIL
goto DONE

:UP
echo [1/2] Building Docker image...
docker compose build
if errorlevel 1 goto FAIL
echo [2/2] Starting container on http://localhost:8080 ...
docker compose up -d
if errorlevel 1 goto FAIL
echo.
echo App is running at: http://localhost:8080
echo View logs:        run.bat logs
echo Stop:             run.bat down
goto DONE

:DOWN
echo Stopping containers...
docker compose down
if errorlevel 1 goto FAIL
goto DONE

:LOGS
docker compose logs -f
goto DONE

:REBUILD
echo Rebuilding without cache...
docker compose build --no-cache
if errorlevel 1 goto FAIL
docker compose up -d
if errorlevel 1 goto FAIL
goto DONE

:CLEAN
echo Removing containers, images and build cache for this project...
docker compose down --rmi local --volumes --remove-orphans
goto DONE

:HELP
echo.
echo Usage: run.bat [action]
echo.
echo Actions:
echo   up        Build image and start the app at http://localhost:8080  (default)
echo   build     Build the Docker image only
echo   rebuild   Rebuild without cache and restart
echo   down      Stop and remove the container
echo   logs      Tail container logs
echo   clean     Remove container + image + volumes for this project
echo   help      Show this message
echo.
goto DONE

:FAIL
echo.
echo [ERROR] Command failed. See output above.
endlocal
exit /b 1

:DONE
echo.
echo Done.
endlocal
exit /b 0
