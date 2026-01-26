import React from 'react';
import { Link } from 'react-router-dom';
import { Plant, CloudSun, Scan, ChartBar } from '@phosphor-icons/react';
import "./Home.css"
const Home = () => {
  return (
    <div className="home-container">
      {/* Navbar */}
      <nav className="navbar">
        <div className="logo">
          <Plant size={32} weight="fill" />
          <span>SmartAgri</span>
        </div>
        <div className="nav-links">
          <Link to="/login" className="btn-link ">Login</Link>
          <Link to="/register" className="btn btn-primary ">Get Started</Link>
        </div>
      </nav>

      {/* Hero Section */}
      <header className="hero">
        <div className="hero-content">
          <h1>Empowering Farmers with AI & Data</h1>
          <p>
            Maximize your yield with real-time weather alerts,
            AI-driven disease detection, and smart crop recommendations.
          </p>
          <div className="hero-cta">
            <Link to="/register" className="btn btn-lg btn-primary">Join Now</Link>
            <Link to="/login" className="btn btn-lg btn-outline">Farmer Login</Link>
          </div>
        </div>
        <div className="hero-shape"></div>
      </header>

      {/* Features Section */}
      <section className="features">
        <h2>Why Choose SmartAgri?</h2>
        <div className="feature-grid">
          <div className="feature-card">
            <CloudSun size={48} color="var(--color-primary)" />
            <h3>Live Weather Alerts</h3>
            <p>Stay ahead of the forecast with localized weather updates and severe weather warnings.</p>
          </div>
          <div className="feature-card">
            <Scan size={48} color="var(--color-primary)" />
            <h3>Disease Detection</h3>
            <p>Snap a photo of your crop and get instant diagnosis and treatment suggestions using AI.</p>
          </div>
          <div className="feature-card">
            <ChartBar size={48} color="var(--color-primary)" />
            <h3>Crop Recommendations</h3>
            <p>Data-driven insights to help you decide the best crops to plant for your soil type.</p>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer>
        <p>&copy; 2026 Smart Agriculture Platform. Built for Farmers.</p>
      </footer>

    
    </div>
  );
};

export default Home;
