# Codeforces README Generator

A Java Servlet-based web application that connects to a MySQL database and provides a web interface for managing and displaying Codeforces problems.

## 🚀 Features

- Java Servlet backend
- MySQL database integration
- Codeforces problem data storage
- REST-style API for problems
- HTML, CSS and JavaScript frontend
- Apache Tomcat 10 deployment
- MySQL Connector/J integration
- Simple project structure for learning Java backend development

## 🛠️ Technologies Used

| Technology | Purpose |
|------------|---------|
| Java 21 | Backend programming |
| Jakarta Servlet | Web/API backend |
| Apache Tomcat 10 | Web server |
| MySQL | Database |
| HTML5 | Frontend structure |
| CSS3 | Frontend styling |
| JavaScript | Frontend functionality |
| Git & GitHub | Version control |

## 📁 Project Structure

```text
Codeforces-README-Generator/
│
├── backend/
│   ├── lib/
│   │   └── mysql-connector-j-9.4.0.jar
│   │
│   ├── src/
│   │   ├── Database.java
│   │   ├── Main.java
│   │   ├── Problem.java
│   │   └── ProblemServlet.java
│   │
│   └── webapp/
│       ├── index.html
│       ├── script.js
│       ├── style.css
│       │
│       └── WEB-INF/
│           ├── classes/
│           ├── lib/
│           │   └── mysql-connector-j-9.4.0.jar
│           └── web.xml
│
├── database/
│   └── codeforces.sql
│
├── frontend/
│   ├── index.html
│   ├── script.js
│   └── style.css
│
├── .gitignore
└── README.md

⚙️ Requirements
Java JDK 21
Apache Tomcat 10
MySQL Server
Git
Ubuntu/Linux

Check Java:

java -version

Check Tomcat:

systemctl status tomcat10

Check MySQL:

mysql --version
🗄️ Database Setup

Open MySQL:

mysql -u root -p

Then execute:

SOURCE database/codeforces.sql;

Or directly:

mysql -u root -p < database/codeforces.sql

Make sure the MySQL credentials in Database.java match your configuration.

🔨 Compile Backend

Copy MySQL Connector:

cp backend/lib/mysql-connector-j-9.4.0.jar backend/webapp/WEB-INF/lib/

Compile the Java files:

javac -cp "backend/lib/mysql-connector-j-9.4.0.jar:/usr/share/tomcat10/lib/servlet-api.jar" -d backend/webapp/WEB-INF/classes backend/src/*.java

Verify:

find backend/webapp/WEB-INF/classes -type f

Expected:

Database.class
Main.class
Problem.class
ProblemServlet.class
🚢 Deploy to Tomcat
sudo cp -r backend/webapp /var/lib/tomcat10/webapps/Codeforces-README-Generator

Restart Tomcat:

sudo systemctl restart tomcat10

Check:

systemctl status tomcat10 --no-pager
🌐 Run the Application

Open in your browser:

http://localhost:8080/Codeforces-README-Generator/

API:

http://localhost:8080/Codeforces-README-Generator/api/problems
🔌 API
Get Problems
GET /api/problems

Example:

http://localhost:8080/Codeforces-README-Generator/api/problems
🧩 Architecture
                 ┌─────────────────────┐
                 │       Browser       │
                 │ HTML/CSS/JavaScript │
                 └──────────┬──────────┘
                            │
                            ▼
                 ┌─────────────────────┐
                 │    Java Servlet     │
                 │   ProblemServlet    │
                 └──────────┬──────────┘
                            │
                            ▼
                 ┌─────────────────────┐
                 │        JDBC         │
                 │     Database.java   │
                 └──────────┬──────────┘
                            │
                            ▼
                 ┌─────────────────────┐
                 │        MySQL        │
                 │  Codeforces Data    │
                 └─────────────────────┘
🔐 Configuration

Configure your MySQL connection in:

backend/src/Database.java

Do not commit real passwords or secrets to GitHub.

Use environment variables or another secure configuration method for production.

🧹 Git

Recommended .gitignore:

*.class

.idea/
.vscode/

target/
build/

.DS_Store

*.log

.env
📌 Current Status

The project is configured with:

Java 21
Apache Tomcat 10
Jakarta Servlet API
MySQL
JDBC
MySQL Connector/J 9.4.0
HTML
CSS
JavaScript

Tomcat deployment has been tested successfully.

📚 What I Learned
Java Servlets
JDBC
MySQL database connectivity
REST API development
Apache Tomcat deployment
Frontend-backend communication
Git and GitHub
Java web application structure
Linux server configuration
👨‍💻 Author

Aaryan Goswami

GitHub:
https://github.com/AARYANG0SWAMI

⭐ Future Improvements
Codeforces API integration
Automatically fetch new submissions
Automatically generate README sections
User authentication
Improved UI/UX
Pagination and filtering
Online deployment
Docker support
Automated GitHub synchronization
📄 License

This project is intended for learning and personal development.
EOF
