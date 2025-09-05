@echo off
echo ========================================
echo Multi-Level Market Backend Deployment
echo ========================================
echo.

echo [1/4] Building the project...
call .\mvnw.cmd clean package -DskipTests
if %ERRORLEVEL% neq 0 (
    echo ERROR: Build failed!
    pause
    exit /b 1
)
echo ✓ Build completed successfully!

echo.
echo [2/4] Verifying JAR file...
if exist "target\multi-level-market-0.0.1-SNAPSHOT.jar" (
    echo ✓ JAR file created: target\multi-level-market-0.0.1-SNAPSHOT.jar
) else (
    echo ERROR: JAR file not found!
    pause
    exit /b 1
)

echo.
echo [3/4] Ready for Railway deployment!
echo.
echo Next steps:
echo 1. Push your code to GitHub
echo 2. Go to https://railway.app
echo 3. Create new project
echo 4. Choose "Deploy from GitHub repo"
echo 5. Select this repository
echo 6. Railway will automatically detect the Dockerfile
echo.
echo OR use Railway CLI:
echo npm install -g @railway/cli
echo railway login
echo railway init
echo railway up

echo.
echo [4/4] Environment variables to configure in Railway:
echo.
echo Database:
echo - DATABASE_URL=your_mysql_connection_string
echo - DB_USERNAME=your_db_username
echo - DB_PASSWORD=your_db_password
echo.
echo Email:
echo - MAIL_HOST=smtp.gmail.com
echo - MAIL_PORT=587
echo - MAIL_USERNAME=your_email@gmail.com
echo - MAIL_PASSWORD=your_app_password
echo.
echo Application:
echo - PORT=8080 (set automatically by Railway)
echo - JAVA_OPTS=-Xmx512m -Xms256m (optional)
echo.
echo ========================================
echo Deployment preparation completed!
echo ========================================
pause
