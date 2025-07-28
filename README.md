# Java TDD Challenge — Secure Resource API

This project is a **Test-Driven Development (TDD)** challenge using Java and Spring Boot. The objective is to implement a secure, validated REST API that complies with a suite of predefined tests. I am going to develop the controller, service, repository, validation logic, and security configuration, ensuring all tests pass successfully.

---

## 🎯 Challenge Objective
- Implement resource management using Java and Spring Boot
- Apply data validation and error handling
- Secure endpoints using JWT-based authentication and role-based access control
- Pass all the predefined test cases

---
## ✅ Provided Tests

| Test Name                                                 | Description                          |
| --------------------------------------------------------- | ------------------------------------ |
| `findAllShouldReturnAllResourcesSortedByName`             | Returns all resources sorted by name |
| `findAllShouldReturnPagedResources`                       | Returns paginated data               |
| `insertShouldInsertResourceWhenAdminLoggedAndCorrectData` | Allows admin to insert data          |
| `insertShouldReturn422WhenAdminLoggedAndBlankName`        | Fails if name is blank               |
| `insertShouldReturn422WhenAdminLoggedAndPastDate`         | Fails if date is in the past         |
| `insertShouldReturn422WhenAdminLoggedAndNullCity`         | Fails if city is null                |
| `insertShouldReturn403WhenClientLogged`                   | Clients are not authorized to insert |
| `insertShouldReturn401WhenIvalidToken`                    | Fails if token is invalid or missing |

---
## 🔐 Access Control Rules

- ✅ Public routes: Only GET requests to events and cities are public (no authentication required)
- ✅ POST /events: Accessible to users with roles CLIENT and ADMIN
- ❌ Other routes (PUT, DELETE, etc.): Restricted to users with role ADMIN only

---

## 🧪 Validation Rules
🔹 City
- name: must not be empty

🔹 Event
- name: must not be empty
- date: must not be in the past
- city: must not be null

🔹Invalid input should return a 422 Unprocessable Entity status with detailed validation messages.
---


## 📚 Technologies Used

- Java 17
- Spring Boot
- Spring Security
- JWT (JSON Web Tokens)
- Spring Data JPA / Hibernate
- H2 (for tests)
- JUnit & Mockito
- Maven

---