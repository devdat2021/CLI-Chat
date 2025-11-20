# 📨 CLI Chat App

A **Java** project that implements a simple **live chat room** using **Multi-threading** and a **MySQL database** for storing and retrieving messages. The application runs in the terminal and uses ANSI codes for colorized, real-time message updates.

## 🛠️ Tech Stack

  - **Core Language:** Java
  - **Database:** MySQL
  - **Connectivity:** JDBC (Java Database Connectivity)
  - **Concurrency:** Uses a separate `Thread` (`MessageFetch`) for real-time polling.

-----

<!-- ## ⚙️ Features

- ✅ Real-time message retrieval using multi-threading
- ✅ ANSI colorized usernames for visual distinction
- ✅ Lightweight, no-login session system (username entered at runtime)
- ✅ Database-backed message storage and retrieval
- ✅ Auto-timestamped messages handled by MySQL
- ✅ Simple console-based interface for quick communication

----- -->
## ⚙️ Features

### 🟢 Live Chat Mode
- **Real-time Messaging:** Uses multi-threading to poll and display messages instantly.
- **User Identification:** ANSI colorized usernames for visual distinction.
- **Database Persistence:** All chats are stored in a MySQL database with auto-timestamps.
- **Easter Eggs & Commands:** Special commands for fun visuals and utilities.

### 🤖 AI Chat Mode
- **Gemini Integration:** Chat directly with an AI bot (Gemini 2.5 Flash).
Allowing for more enhanced user experience with the application.

### 🎨 Easter Eggs & Utilities
Use these commands inside the **Live Chat** to trigger special effects:
- `/weather <city>` : Fetches current weather for the specified city (e.g., `/weather Mangalore`).
- `/list` : Lists all available ASCII art commands.
- **ASCII Art:** `/red`, `/butterfly`, `/aah`, `/dance`, `/kitty`

-----

## 📸 Demo Preview

```text
Enter username: Devdat
Select mode:
1. Live Chat
2. AI Chat
Enter choice: 1
Attempting to connect to server...
Connection successful!
--- Chat room (Live) ---
[21:30:00]  Devdat : Hey everyone, this chat finally works!
[21:31:10]  Pramukh  : Nice! I was just testing the system.
> 
```

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
3. **Update credentials:** Ensure you change the API Key, DB Connection info with your actual information before compiling.
4.  **Compile:** Compile the Java files (`App.java`, `message.java`, `DBConnection.java`,`LiveChat.java`,`AIChat.java`,`extra.java`,).
5.  **Run:** Execute the compiled `App` class, ensuring the JDBC driver is on the classpath.




## 👨‍💻 Contributors

* **[Devdat](https://github.com/devdat2021)** — Developer, Design & Core Logic
* **[Pramukh](https://github.com/pramukhnayak7)** — Collaboration, Testing, and Bug Fixing

----

