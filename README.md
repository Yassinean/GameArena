# Tournament Management System

A Java-based console application that allows users to manage tournaments, teams, players, and games. The system is designed using modular architecture and employs the Spring framework for dependency injection and transaction management, utilizing JPA for data persistence with Hibernate ORM.

## Features

- **Tournament Management**: Add, update, view, and delete tournaments.
- **Team Management**: Create, update, assign teams to tournaments, and assign players to teams.
- **Player Management**: Add, update, view, and delete players.
- **Game Management**: Add, update, view, and delete games.
- **Database**: Integration with H2 in-memory database for rapid development and testing.

## Technologies Used

- **Java**: Core language.
- **Spring Framework**: Dependency injection, transaction management.
- **Spring JPA / Hibernate**: ORM for database persistence.
- **H2 Database**: In-memory database for testing.
- **JUnit**: For unit testing.
- **Maven**: Dependency management and build tool.

---

## Getting Started

### Prerequisites

- **Java 8+**: Ensure Java is installed and JAVA_HOME is set.
- **Maven**: For building the project and managing dependencies.
- **IDE**: IntelliJ, Eclipse, or any Java IDE for better project management.

### Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/Yassinean/GameArena.git
   ```
   ```bash
   cd tournament-management-system
    ```
   
2. **Configure the Database**:
  The system is set up to use an H2 in-memory database, configured in applicationContext.xml. No setup is required for development and testing.

3. **Build the Project**:

```bash
    mvn clean install
```
4. **Run the Application**:
 Start the application by running the Main class:

```bash
    mvn exec:java -Dexec.mainClass="org.yassine.Main"
   ```
Application Structure
Directory Structure
```bash
src/
├── main/
│   ├── java/
│   │   └── org.yassine/
│   │       ├── dao/                      # Data access layer
│   │       ├── service/                  # Service layer with business logic
│   │       ├── view/                     # Console UI classes
│   │       ├── Main.java                 # Entry point of the application
│   └── resources/
│       └── applicationContext.xml        # Spring application context and bean configuration
└── test/                                 # Unit tests
```

5.**Key Classes**:
    5.1.ConsoleUI: Main console menu handler.
        PlayerUI, TeamUI, GameUI, TournamentUI: Sub-menu UIs for managing players, teams, games, and tournaments, respectively.
    5.2.Service Layer: Interfaces and implementations (PlayerService, GameService, etc.) to handle business logic.
    5.3.DAO Layer: Data Access Objects to handle database interactions.
6. **Configuration**:
    ### applicationContext.xml
The Spring configuration file, applicationContext.xml, sets up:

  - Beans: Configurations for service and DAO beans.
  - Transaction Management: Configured to manage database transactions.
  - Database Connection: Configured for the H2 in-memory database.

7. **Usage**
## Running the Console UI
Once the application is started, the main console UI offers options for managing players, teams, tournaments, and games. Follow these steps to navigate:

1. Player Management: Create, modify, delete, or view players.
2. Game Management: Add, update, delete, or view games.
3. Team Management: Manage team details, assign teams to tournaments, and players to teams.
4. Tournament Management: Add, update, delete, or view tournament details.

8.**Testing**:
   Unit tests are located under src/test/java/org/yassine/. Use the following command to run tests:

```bash
mvn test
```
9.**License**:
This project is licensed under the MIT License - see the LICENSE file for details.

   