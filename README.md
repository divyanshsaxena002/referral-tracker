# 🎯 Referral Tracking System

A Java Spring Boot backend for user signup and referral tracking, with profile completion logic and API endpoints.  
This README includes real API usage examples and database screenshots.

---

## 🚀 Features

- User signup with/without referral code
- Unique referral code generation
- Referral tracking and status updates
- Profile completion handling
- Fetch referrals for a given user
- MySQL database integration

---

## 🧱 Tech Stack

- **Language:** Java 17+
- **Framework:** Spring Boot 3.x
- **Database:** MySQL 8+
- **ORM:** Spring Data JPA
- **Build Tool:** Maven
- **Testing:** Postman

---

## 📦 Database Example

Here’s how the `users` table looks in MySQL:

![Users Table](https://github.com/divyanshsaxena002/referral-tracker/blob/main/img/img4.png)

- `referral_code`: Unique code for each user.
- `referred_by`: Referral code of the user who referred them (if any).
- `is_profile_complete`: 1 if the user has completed their profile, 0 otherwise.

---

## 🛠️ API Endpoints & Usage

### 1. Get Referrals by User

**Endpoint:**  
`GET /api/referrals/{userId}`

**Example Request:**  
```
GET http://localhost:8080/api/referrals/61d48adb-2c73-4ee8-b1a0-fc1270b3cae2
```

**Example Response (Pending):**
```json
[
  {
    "referredUser": "Vipul",
    "email": "vipul@example.com",
    "status": "pending"
  }
]
```
![Get Referrals Pending](https://github.com/divyanshsaxena002/referral-tracker/blob/main/img/img1.png)

---

### 2. Complete Profile

**Endpoint:**  
`POST /api/complete-profile`

**Example Request:**
```json
POST http://localhost:8080/api/complete-profile
Content-Type: application/json

{
  "userId": "ba796fe8-0666-43fa-a94b-2bfe8d1990bb"
}
```

**Example Response:**
```json
{
  "message": "Profile marked as complete"
}
```
![Complete Profile](https://github.com/divyanshsaxena002/referral-tracker/blob/main/img/img2.png)

---

### 3. Referral Status Update

After the referred user completes their profile, the referral status changes to `"completed"`.

**Example Response (Completed):**
```json
[
  {
    "referredUser": "Vipul",
    "email": "vipul@example.com",
    "status": "completed"
  }
]
```
![Get Referrals Completed](https://github.com/divyanshsaxena002/referral-tracker/blob/main/img/img3.png)

---

## 💡 How the System Works

1. **User Signup:**  
   Users can sign up with or without a referral code. If a referral code is used, a referral relationship is created.

2. **Referral Tracking:**  
   The referrer can view their referrals and see their status (`pending` or `completed`).

3. **Profile Completion:**  
   When a referred user completes their profile, the referral status is updated to `completed`.

4. **Database:**  
   All user and referral data is stored in MySQL, as shown in the screenshots.

---

## ⚙️ Setup & Run

1. **Clone the repo:**
   ```sh
   git clone https://github.com/divyanshsaxena002/referral-tracker.git
   cd referral-tracker
   ```

2. **Configure your database in `src/main/resources/application.properties`:**
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/referral_db
   spring.datasource.username=YOUR_DB_USER
   spring.datasource.password=YOUR_DB_PASSWORD
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
   ```

3. **Build and run:**
   ```sh
   mvn clean install
   java -jar target/*.jar
   ```

4. **Test the APIs using Postman or curl.**

---

By: Divyansh Saxena
