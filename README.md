# 🌱 AI-Based Smart Agriculture Platform

## 🚀 Overview

An intelligent full-stack agriculture platform that leverages **Artificial Intelligence and Machine Learning** to assist farmers in making **data-driven decisions**.

The system provides:

* **Crop disease detection** using deep learning (CNN)
* **Crop recommendation** based on soil and environmental conditions
* **Real-time weather insights** via API integration

This platform aims to improve agricultural productivity, reduce crop loss, and promote smart farming practices.

---

## 🛠️ Tech Stack

**Frontend**

* React.js
* HTML, CSS, JavaScript

**Backend**

* Spring Boot
* Spring Security (JWT Authentication)
* REST APIs

**Database**

* MySQL

**AI / ML**

* TensorFlow
* Convolutional Neural Networks (CNN)
* Transfer Learning
* scikit-learn

**External Integration**

* Weather API (real-time environmental data)

---

## ✨ Features

### 🌿 Crop Disease Detection

* Upload crop images for analysis
* CNN-based deep learning model for accurate disease prediction
* Uses transfer learning for improved performance

### 🌾 Crop Recommendation System

* Predicts the most suitable crop based on:

  * Soil NPK values
  * Soil type
  * Water availability
* Built using machine learning algorithms (scikit-learn)

### 🌦️ Weather Integration

* Fetches real-time weather data using API
* Helps farmers make better planting decisions

### 🔐 Authentication & Security

* JWT-based secure authentication
* Protected APIs using Spring Security

### 📊 Data-Driven Insights

* Combines ML predictions with environmental data
* Improves decision-making for higher yield

---


## ⚙️ Installation & Setup

### 1️⃣ Clone the Repository

```bash
git clone https://github.com/abhigorde15/smart-agriculture-platform
cd smart-agriculture-platform
```

---

### 2️⃣ Backend Setup (Spring Boot)

```bash
cd backend
mvn clean install
mvn spring-boot:run
```

---

### 3️⃣ Frontend Setup (React)

```bash
cd frontend
npm install
npm run dev
```

---

## 🔑 Environment Variables

### Backend

```
DB_URL=
DB_USERNAME=
DB_PASSWORD=
JWT_SECRET=
WEATHER_API_KEY=
MODEL_PATH=
```

### Frontend

```
REACT_APP_API_URL=
```

---

## 🧠 Machine Learning Workflow

### 1. Disease Detection Model

* Preprocessed image dataset
* Applied **CNN architecture with transfer learning**
* Trained model using TensorFlow
* Achieved optimized accuracy for classification

### 2. Crop Recommendation Model

* Input features:

  * Nitrogen (N), Phosphorus (P), Potassium (K)
  * Soil type
  * Water availability
* Trained using scikit-learn algorithms
* Outputs most suitable crop

---

## 🧑‍💻 My Contribution

* Designed and developed **full-stack architecture**
* Built **CNN-based crop disease detection model using TensorFlow**
* Implemented **transfer learning for improved accuracy**
* Developed **ML-based crop recommendation system**
* Integrated **weather API for real-time environmental insights**
* Secured backend using **Spring Security & JWT authentication**

---

## 📈 Future Improvements

* Mobile application for farmers
* Multilingual support (regional languages)
* IoT sensor integration for real-time soil data
* Advanced AI recommendations using deep learning models
* Deployment on cloud (AWS/GCP)

---

## 🌐 Live Demo

https://smart-agriculture-platform-xi.vercel.app/

---

## 📜 License

This project is licensed under the MIT License.

---

## 👨‍💻 Author

**Abhishek**
B.Tech IT | Full Stack Developer | AI/ML Enthusiast

---
