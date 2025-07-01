# 🎯 Referral Tracking System - Java Backend LLM Assessment

This project implements a backend system for **user signup and referral tracking**, where a referral is considered **successful only after the referred user completes their profile**.

---

## 🚀 Features

- 📝 User Signup with/without referral code
- 🎁 Unique referral code generation
- 🔗 Referral tracking and status updates
- 👤 Profile completion handling
- 📊 Fetch referrals for a given user
- 📄 Generate CSV report of all referrals (Bonus)
- 🧪 Unit test coverage with JUnit

---

## 🧱 Tech Stack

| Layer        | Stack                |
|--------------|----------------------|
| Language     | Java 24              |
| Framework    | Spring Boot 3.x      |
| Database     | MySQL 8+             |
| ORM          | Spring Data JPA      |
| Build Tool   | Maven                |
| Testing      | JUnit                |
| Deployment   | Render (or Railway/EC2/etc) |

---

## 📁 Project Structure

```
src/
├── controller/        // REST API Controllers
├── service/           // Business Logic
├── repository/        // JPA Repositories
├── model/             // Entity Classes
├── dto/               // Request & Response DTOs
├── config/            // Config files (DB, CORS)
└── util/              // Utility Classes
```

---

## 🛠️ API Endpoints

### 📌 1. Signup User
`POST /api/signup`

**Request Body:**
```json
{
  "name": "John Doe",
  "email": "john@example.com",
  "password": "securepass",
  "referralCode": "ABCD1234" // optional
}
```

**Response:**

```json
{
  "message": "Signup successful",
  "referralCode": "XYZ9876"
}
```

---

### 📌 2. Complete Profile

`POST /api/complete-profile`

**Request Body:**

```json
{
  "userId": "UUID-of-user"
}
```

**Response:**

```json
{
  "message": "Profile marked as complete"
}
```

---

### 📌 3. Get Referrals by User

`GET /api/referrals/{userId}`

**Response:**

```json
[
  {
    "referredUser": "Jane Doe",
    "email": "jane@example.com",
    "status": "completed"
  },
  ...
]
```


---

## 💡 Referral Logic

* Every user gets a **unique referral code** on signup.
* If a user signs up with a valid code, they are linked to the **referrer**.
* Referral status is marked:

  * `"pending"` at signup
  * `"completed"` after referred user completes profile

---

## 🧪 Unit Testing

* Covered all business logic and API endpoints using **JUnit**.
* Edge cases tested:

  * Invalid referral codes
  * Double profile completion
  * Circular referrals (prevented)

---

## 💽 Database Schema

### `users` Table

| Field                 | Type    | Description               |
| --------------------- | ------- | ------------------------- |
| id                    | UUID    | Primary Key               |
| name                  | VARCHAR | User name                 |
| email                 | VARCHAR | Unique email              |
| password              | VARCHAR | Encrypted password        |
| referral_code         | VARCHAR | Unique per user           |
| referred_by           | VARCHAR | Referral code of referrer |
| is_profile_complete   | BOOLEAN | Marks profile completion  |

### `referrals` Table

| Field        | Type    | Description                     |
| ------------ | ------- | ------------------------------- |
| id           | UUID    | Primary Key                     |
| referrer_id  | UUID    | FK to Users                     |
| referred_id  | UUID    | FK to Users                     |
| status       | VARCHAR | Either `pending` or `completed` |

---

## ⚙️ How to Run Locally

### ✅ Prerequisites

* Java 24 installed
* Maven installed
* MySQL running locally

### 📦 Steps

1. Clone the repo:

   ```bash
   git clone https://github.com/yourusername/referral-tracker
   cd referral-tracker
   ```

2. Create MySQL DB:

   ```sql
   CREATE DATABASE referral_db;
   ```

3. Configure `src/main/resources/application.properties`:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/referral_db
   spring.datasource.username=root
   spring.datasource.password=my_password
   spring.jpa.hibernate.ddl-auto=update
   ```

4. Build & run:

   ```bash
   mvn spring-boot:run
   ```

5. Server runs at: `http://localhost:8080`

---

## ☁️ Deployment

* Deployed on: [https://referral-tracker.onrender.com](https://referral-tracker.onrender.com) *(replace with your actual URL)*
* Public GitHub Repo: [github.com/yourusername/referral-tracker](https://github.com/yourusername/referral-tracker)

---

## 📄 Sample `postmann` Requests

### Signup:

```bash
 POST http://localhost:9000/api/signup \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice","email":"alice@gmail.com","password":"123456","referralCode":"XYZ123"}'
```

### Complete Profile:

```bash
curl -X POST http://localhost:9000/api/complete-profile \
  -H "Content-Type: application/json" \
  -d '{"userId":"user-uuid"}'
```

### Get Referrals:

```bash
 http://localhost:9000/api/referrals/user-uuid


---

## 🏁 Future Improvements

* Add password encryption (BCrypt)
* Add pagination & filtering to referral list
* Add Swagger/OpenAPI docs
* Add email notifications on successful referrals

---

## 👨‍💻 Author

**Divyansh Saxena**
Backend Developer | Java Enthusiast | LLM Intern Candidate
📫 [divyansh@example.com](mailto:divyansh@example.com) *(replace with your contact)*

---

## 📝 License

This project is open source and free to use under the [MIT License](LICENSE). 
