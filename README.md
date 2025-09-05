# Multi-Level Marketing System

A comprehensive Spring Boot application for managing multi-level marketing operations with user registration, referral systems, wallet management, and admin functionality.

## Features

- **User Management**: User registration, login, and authentication with JWT tokens
- **Referral System**: Multi-tier referral tracking and management
- **Wallet System**: Digital wallet with transaction history
- **Admin Panel**: Administrative controls and user management
- **Item Management**: Product/item catalog system
- **Security**: JWT-based authentication and authorization
- **API Documentation**: Swagger/OpenAPI integration

## Technology Stack

- **Backend**: Spring Boot 3.5.4
- **Database**: MySQL
- **Security**: Spring Security with JWT
- **Documentation**: SpringDoc OpenAPI
- **Build Tool**: Maven
- **Java Version**: 21

## Project Structure

```
src/main/java/com/example/demo/
├── admin/                 # Admin functionality
├── config/               # Configuration classes
├── dto/                  # Data Transfer Objects
├── items/                # Item management
├── JwtUtil/              # JWT utilities
├── referrals/            # Referral system
├── referralTier/         # Referral tier management
├── Repository/           # Data repositories
├── token/                # Token management
├── UserRegistrationController/  # User controllers
├── UserRegistrationService/     # User services
├── users/                # User entities
├── wallet/               # Wallet entities
└── wallet_transactions/  # Transaction management
```

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven 3.6 or higher
- MySQL 8.0 or higher

### Installation

1. Clone the repository:
```bash
git clone <repository-url>
cd multi-level-market
```

2. Configure the database:
   - Create a MySQL database
   - Update `src/main/resources/application.properties` with your database credentials

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The application will be available at `http://localhost:8080`

### API Documentation

Once the application is running, you can access the Swagger UI at:
`http://localhost:8080/swagger-ui.html`

## Configuration

### Database Configuration

Update the following properties in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### JWT Configuration

Configure JWT settings in your application properties:

```properties
jwt.secret=your-secret-key
jwt.expiration=86400000
```

## Deployment

The project includes Docker support and Railway deployment configuration:

- `Dockerfile` - Container configuration
- `railway.toml` - Railway deployment settings
- `deploy-to-railway.bat` - Windows deployment script

## API Endpoints

### Authentication
- `POST /api/auth/register` - User registration
- `POST /api/auth/login` - User login
- `POST /api/auth/forgot-password` - Password reset

### User Management
- `GET /api/users` - Get all users
- `GET /api/users/{id}` - Get user by ID
- `PUT /api/users/{id}` - Update user

### Referral System
- `GET /api/referrals` - Get referral data
- `POST /api/referrals` - Create referral

### Wallet Management
- `GET /api/wallet/{userId}` - Get wallet balance
- `POST /api/wallet/transaction` - Create transaction

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License.

## Support

For support and questions, please open an issue in the repository.
