# UnempX

![Java](https://img.shields.io/badge/Java-17-blue)
![Maven](https://img.shields.io/maven-central/v/com.myorg/UnempX)

_UnempX_ is a Java application for **analyzing US unemployment rates** from **2015 to 2024**.  
The data comes from the **Bureau of Labor Statistics Public Data Query (PDQ) System** ([source](https://data.bls.gov/pdq/SurveyOutputServlet)).

The application allows users to **import, manage, analyze, and visualize unemployment data** to identify trends and patterns.

---

## 🚀 Technologies
- **Language:** Java
- **Build Tool:** Maven# UnempX – Unemployment Data Analyzer

![Java](https://img.shields.io/badge/Java-17-blue)
![Maven](https://img.shields.io/maven-central/v/com.myorg/UnempX)
![License](https://img.shields.io/badge/license-MIT-lightgrey)
![Build](https://img.shields.io/badge/build-passing-brightgreen)

**UnempX** is a Java desktop application for **analyzing US unemployment rates** from **2015 to 2024**.  
The data is sourced from the **Bureau of Labor Statistics Public Data Query (PDQ) System** ([source](https://data.bls.gov/pdq/SurveyOutputServlet)).

The application allows users to **import, manage, analyze, and visualize unemployment data**, enabling identification of trends, patterns, and statistical insights.

---

## 🚀 Technologies & Tools

- **Programming Language:** Java 17
- **Build & Dependency Management:** Maven
- **GUI Framework:** Swing
- **Data Format:** CSV
- **Charting Library:** JFreeChart
- **Logging:** SLF4J + Logback (professional logging setup)
- **Testing:** JUnit 5 (unit tests for core logic)

---

## ✨ Features

- Import unemployment data from CSV files.
- Validate data integrity and handle missing or invalid entries.
- Compute statistics:
    - Annual average unemployment rates.
    - Minimum and maximum unemployment years.
    - Top N years with highest unemployment.
    - Moving average calculations.
- Interactive charts and visualizations using JFreeChart.
- Logging for debugging and production monitoring.
- Fully tested with unit tests covering models and data processing logic.
- Modular design:
    - `model` for data representation.
    - `service` for business logic and calculations.
    - `ui` for GUI components.

---

## 🖼️ Screenshots

**App Overview**  
![App Overview](images/app-overview.png)

**Select Data Window**  
![Select Data Window](images/select-data-window.png)

**Add New Data Window**  
![Add New Data Window](images/add-data-window.png)

---

## 📦 Installation & Running

### 1. Clone the repository
```bash
git clone https://github.com/Nikolaspc/UnempX.git
cd UnempX

- **GUI:** Swing
- **Data Format:** CSV

---

## 🖼️ Screenshots

**App Overview**  
![App Overview](images/app-overview.png)

**Select Data Window**  
![Select Data Window](images/select-data-window.png)

**Add New Data Window**  
![Add New Data Window](images/add-data-window.png)

---

## 📦 Installation & Running

### Using Maven
From the root of the project (where `pom.xml` is located):

```bash
# Build the project
mvn clean install

# Run the application
mvn -Dexec.mainClass="com.myorg.unempx.Main" exec:java
