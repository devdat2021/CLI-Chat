# 📨 CLI Chat App

A **Java** project that implements a simple **live chat room** using **Multi-threading** and a **MySQL database** for storing and retrieving messages. The application runs in the terminal and uses ANSI codes for colorized, real-time message updates.

## 🛠️ Tech Stack

  - **Core Language:** Java
  - **Database:** MySQL
  - **Connectivity:** JDBC (Java Database Connectivity)
  - **Concurrency:** Uses a separate `Thread` (`MessageFetch`) for real-time polling.

-----

## 🚀 How to Run & Collaborate

### 1\. Database Setup (Admin)

Before running, you must set up the `Chatapp` database and create a table for messages.

| Component | SQL Type | Description |
| :--- | :--- | :--- |
| `id` | `INT` (PK, AUTO\_INCREMENT) | Primary key, used by the Java application to track new messages. |
| `username` | `VARCHAR` | The user's chosen display name. |
| `message_text` | `VARCHAR` | The content of the message. |
| `color_code` | `VARCHAR` | ANSI code for username color. |
| `timestamp` | `DATETIME / TIMESTAMP` | Timestamp of the message (set by the database). |

#### SQL Schema:

```sql
CREATE DATABASE IF NOT EXISTS Chatapp;
USE Chatapp;

CREATE TABLE messages (
    id INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    message_text TEXT NOT NULL,
    color_code VARCHAR(15),
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

### 2\. Compile and Run

1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/devdat2021/CLI-Chat
    cd CLI-Chat
    ```
2.  **Download JDBC Driver:** Ensure you have the MySQL JDBC Connector (`mysql-connector-java-[version].jar`) and place it in a location accessible to your build system.
3.  **Compile:** Compile the Java files (`App.java`, `message.java`, `DBConnection.java`).
4.  **Run:** Execute the compiled `App` class, ensuring the JDBC driver is on the classpath.