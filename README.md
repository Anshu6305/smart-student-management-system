# 🎓 Smart Student Management System

A **full-stack web application** for managing student records with a modern, dark-themed dashboard UI. Built with **Java Servlets + JDBC + MySQL** on the backend and **HTML/CSS/JavaScript** on the frontend.

> Designed to be **beginner-friendly**, **interview-ready**, and **visually impressive**.

---

## ✨ Features

| Feature | Description |
|---------|-------------|
| 🔐 **Secure Login** | Username/password authentication with session management |
| 📊 **Dashboard** | Statistics cards showing total students, avg CGPA, avg attendance, branches |
| 👥 **Student CRUD** | Add, View, Edit, and Delete student records |
| 🔍 **Search** | Real-time search by name, roll number, or branch |
| 🏷️ **Filter** | Filter students by branch |
| ✅ **Form Validation** | Client-side validation for all form fields |
| 🌙 **Dark Theme** | Professional dark UI with glassmorphism effects |
| 📱 **Responsive** | Works on desktop, tablet, and mobile |
| 🔔 **Notifications** | Toast notifications for success/error actions |
| ⚠️ **Error Handling** | Proper error handling on both client and server |

---

## 🛠️ Tech Stack

| Layer | Technology |
|-------|-----------|
| **Frontend** | HTML5, CSS3, JavaScript (ES6) |
| **Backend** | Java (Servlets) |
| **Database** | MySQL |
| **Connectivity** | JDBC (MySQL Connector/J) |
| **Server** | Apache Tomcat 9/10 |
| **Font** | Google Fonts — Inter |

---

## 📁 Folder Structure

```
Smart-Student-Management-System/
├── README.md                              # This file
├── database/
│   └── schema.sql                         # Database setup + sample data
├── src/
│   └── main/
│       ├── java/
│       │   └── com/studentms/
│       │       ├── model/
│       │       │   └── Student.java       # Student POJO
│       │       ├── dao/
│       │       │   └── StudentDAO.java    # Database operations
│       │       ├── servlet/
│       │       │   ├── LoginServlet.java
│       │       │   ├── DashboardServlet.java
│       │       │   ├── StudentServlet.java
│       │       │   └── LogoutServlet.java
│       │       └── util/
│       │           └── DBConnection.java  # DB connection helper
│       └── webapp/
│           ├── WEB-INF/
│           │   └── web.xml                # Servlet mappings
│           ├── index.html                 # Login page
│           ├── dashboard.html             # Main dashboard (SPA)
│           ├── css/
│           │   └── style.css              # All styles
│           └── js/
│               ├── login.js               # Login logic
│               └── app.js                 # Dashboard logic
└── lib/
    └── mysql-connector-j-8.x.x.jar       # MySQL JDBC driver (download)
```

---

## 🚀 Setup Instructions

### Prerequisites

- **Java JDK** 8 or above → [Download](https://www.oracle.com/java/technologies/downloads/)
- **Apache Tomcat** 9 or 10 → [Download](https://tomcat.apache.org/download-90.cgi)
- **MySQL** 8.0+ → [Download](https://dev.mysql.com/downloads/mysql/)
- **MySQL Connector/J** → [Download](https://dev.mysql.com/downloads/connector/j/)

### Step 1: Database Setup

1. Open MySQL terminal or MySQL Workbench
2. Run the schema file:

```sql
source /path/to/Smart-Student-Management-System/database/schema.sql;
```

This creates the database, tables, an admin user, and 15 sample students.

### Step 2: Configure Database Credentials

Open `src/main/java/com/studentms/util/DBConnection.java` and update:

```java
private static final String URL = "jdbc:mysql://localhost:3306/student_management_system";
private static final String USER = "root";           // Your MySQL username
private static final String PASSWORD = "root";       // Your MySQL password
```

### Step 3: Add MySQL JDBC Driver

1. Download `mysql-connector-j-8.x.x.jar` from the link above
2. Place it in the `lib/` folder of this project
3. Also copy it to `TOMCAT_HOME/lib/` folder

### Step 4: Compile Java Files

```bash
# Navigate to the project directory
cd Smart-Student-Management-System

# Create output directory
mkdir -p build/WEB-INF/classes

# Compile all Java files
javac -cp "lib/*:TOMCAT_HOME/lib/servlet-api.jar" \
      -d build/WEB-INF/classes \
      src/main/java/com/studentms/model/Student.java \
      src/main/java/com/studentms/util/DBConnection.java \
      src/main/java/com/studentms/dao/StudentDAO.java \
      src/main/java/com/studentms/servlet/*.java
```

> **Windows users**: Replace `:` with `;` in the classpath (`-cp`).

### Step 5: Prepare WAR Structure

```bash
# Copy web.xml
cp src/main/webapp/WEB-INF/web.xml build/WEB-INF/

# Copy frontend files
cp src/main/webapp/index.html build/
cp src/main/webapp/dashboard.html build/
cp -r src/main/webapp/css build/
cp -r src/main/webapp/js build/

# Copy JDBC driver
cp lib/mysql-connector-j-*.jar build/WEB-INF/lib/
```

### Step 6: Deploy to Tomcat

1. Copy the entire `build/` folder to `TOMCAT_HOME/webapps/StudentMS`
2. Start Tomcat: `TOMCAT_HOME/bin/startup.sh` (Linux/Mac) or `startup.bat` (Windows)
3. Open browser: **http://localhost:8080/StudentMS/**

### Login Credentials

| Username | Password |
|----------|----------|
| `admin` | `admin123` |

---

## 📸 Screenshots

> Add your screenshots here after running the project.

| Login Page | Dashboard |
|:---:|:---:|
| _screenshot_ | _screenshot_ |

| Student List | Add Student |
|:---:|:---:|
| _screenshot_ | _screenshot_ |

---

## 🧠 Java OOP Concepts Used

| Concept | Where Used |
|---------|-----------|
| **Encapsulation** | `Student.java` — private fields, public getters/setters |
| **Abstraction** | `StudentDAO.java` — hides SQL complexity behind simple methods |
| **Modularity** | Separate packages: model, dao, servlet, util |
| **Inheritance** | All servlets extend `HttpServlet` |
| **Method Overriding** | `doGet()`, `doPost()`, `doPut()`, `doDelete()` in servlets |

---

## 🗄️ API Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/api/login` | Authenticate user |
| `GET` | `/api/dashboard` | Get dashboard statistics |
| `GET` | `/api/students` | Get all students |
| `GET` | `/api/students?id=1` | Get student by ID |
| `GET` | `/api/students?search=keyword` | Search students |
| `POST` | `/api/students` | Add new student |
| `PUT` | `/api/students?id=1` | Update student |
| `DELETE` | `/api/students?id=1` | Delete student |
| `GET` | `/api/logout` | Logout user |

---

## 🔮 Future Improvements

- [ ] Password hashing (BCrypt)
- [ ] Role-based access control (Admin, Faculty, Student)
- [ ] Pagination for student list
- [ ] Export to PDF/Excel
- [ ] Profile picture upload
- [ ] Email notifications
- [ ] Attendance marking system
- [ ] Grade management module
- [ ] Connection pooling (HikariCP)
- [ ] Migration to Spring Boot

---

## 📄 License

This project is for **educational purposes**. Feel free to use it for learning, college projects, and interviews.

---

**Made with ❤️ for B.Tech CSE Students**
