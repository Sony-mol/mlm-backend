# Railway Deployment Guide for Multi-Level Market Backend

## Prerequisites
- Railway account (https://railway.app)
- Git repository with your code
- MySQL database (can be provisioned through Railway)

## Step 1: Build the Project Locally
```bash
# Clean and build the project
./mvnw clean package -DskipTests

# Verify the JAR file is created
ls -la target/multi-level-market-0.0.1-SNAPSHOT.jar
```

## Step 2: Deploy to Railway

### Option A: Deploy via Railway CLI
```bash
# Install Railway CLI
npm install -g @railway/cli

# Login to Railway
railway login

# Initialize Railway project
railway init

# Deploy your application
railway up
```

### Option B: Deploy via Railway Dashboard
1. Go to https://railway.app
2. Click "New Project"
3. Choose "Deploy from GitHub repo"
4. Select your repository
5. Railway will automatically detect the Dockerfile and deploy

## Step 3: Configure Environment Variables

In your Railway project dashboard, add these environment variables:

### Database Configuration
- `DATABASE_URL`: Your MySQL connection string
- `DB_USERNAME`: Database username
- `DB_PASSWORD`: Database password

### Email Configuration
- `MAIL_HOST`: SMTP host (e.g., smtp.gmail.com)
- `MAIL_PORT`: SMTP port (e.g., 587)
- `MAIL_USERNAME`: Your email address
- `MAIL_PASSWORD`: Your email app password

### Application Configuration
- `PORT`: Port number (Railway will set this automatically)
- `JAVA_OPTS`: JVM options (optional)

## Step 4: Provision MySQL Database

1. In Railway dashboard, click "New Service"
2. Choose "Database" → "MySQL"
3. Railway will provide connection details
4. Copy the connection string to your `DATABASE_URL` environment variable

## Step 5: Verify Deployment

1. Check the deployment logs in Railway dashboard
2. Visit your application URL (provided by Railway)
3. Test the health endpoint: `https://your-app.railway.app/actuator/health`
4. Test Swagger UI: `https://your-app.railway.app/swagger-ui.html`

## Troubleshooting

### Common Issues:
1. **Build Failures**: Check Maven logs in Railway dashboard
2. **Database Connection**: Verify `DATABASE_URL` format and credentials
3. **Port Issues**: Ensure your app listens on `$PORT` environment variable
4. **Memory Issues**: Adjust `JAVA_OPTS` if needed

### Logs:
- View real-time logs: `railway logs`
- View build logs in Railway dashboard

## Environment Variables Reference

| Variable | Description | Example |
|----------|-------------|---------|
| `DATABASE_URL` | MySQL connection string | `jdbc:mysql://host:port/dbname` |
| `DB_USERNAME` | Database username | `root` |
| `DB_PASSWORD` | Database password | `your_password` |
| `PORT` | Application port | `8080` (set by Railway) |
| `MAIL_HOST` | SMTP server host | `smtp.gmail.com` |
| `MAIL_PORT` | SMTP server port | `587` |
| `MAIL_USERNAME` | Email username | `your_email@gmail.com` |
| `MAIL_PASSWORD` | Email app password | `your_app_password` |

## Security Notes

1. **Never commit sensitive data** like passwords or API keys
2. Use Railway's environment variables for all sensitive configuration
3. Consider using Railway's secrets management for production
4. Regularly rotate database passwords and API keys

## Monitoring

- Use Railway's built-in monitoring dashboard
- Check application metrics at `/actuator/metrics`
- Monitor database performance through Railway's database service
- Set up alerts for application health and performance
