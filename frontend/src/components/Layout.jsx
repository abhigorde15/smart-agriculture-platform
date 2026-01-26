import { Outlet, Link, useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import {
  Plant,
  User,
  SignOut,
  House,
  Tree,
  CloudSun,
  Leaf,
  ChartLine,
  List,
  X
} from '@phosphor-icons/react';
import { useState } from 'react';
import './Layout.css';

const Layout = () => {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [mobileMenuOpen, setMobileMenuOpen] = useState(false);

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  const isActive = (path) => location.pathname === path;

  const NavItem = ({ to, icon, label }) => (
    <Link
      to={to}
      onClick={() => setMobileMenuOpen(false)}
      className={`nav-item ${isActive(to) ? 'active' : ''}`}
    >
      {icon}
      <span>{label}</span>
    </Link>
  );

  return (
    <div className="layout">
      {/* Backdrop (Mobile only) */}
      {mobileMenuOpen && (
        <div
          className="backdrop"
          onClick={() => setMobileMenuOpen(false)}
        />
      )}

      {/* Sidebar */}
      <aside className={`sidebar ${mobileMenuOpen ? 'open' : ''}`}>
        <div className="sidebar-header">
          <div className="logo">
            <Plant size={32} weight="fill" />
            <h1>Smart Agri</h1>
          </div>

          <button className="close-btn" onClick={() => setMobileMenuOpen(false)}>
            <X size={24} />
          </button>
        </div>

        <nav className="nav">
          <NavItem to="/dashboard" icon={<House size={20} />} label="Dashboard" />
          <NavItem to="/profile" icon={<User size={20} />} label="My Farm Profile" />
          <NavItem to="/crops" icon={<Tree size={20} />} label="My Crops" />
          <NavItem to="/recommendations" icon={<Leaf size={20} />} label="Get Recommendations" />
          <NavItem to="/disease-detect" icon={<Plant size={20} />} label="Disease Detection" />
          {user?.role === 'ADMIN' && (
            <NavItem to="/admin" icon={<ChartLine size={20} />} label="Admin Panel" />
          )}
        </nav>

        <div className="sidebar-footer">
          <div className="user-box">
            <div className="avatar">
              {user?.fullName?.charAt(0) || 'U'}
            </div>
            <div>
              <p className="name">{user?.fullName}</p>
              <p className="role">{user?.role}</p>
            </div>
          </div>

          <button className="logout-btn" onClick={handleLogout}>
            <SignOut size={20} />
            Logout
          </button>
        </div>
      </aside>

      {/* Main Content */}
      <main className="content">
        <header className="header">
          <div className="header-left">
            <button className="hamburger" onClick={() => setMobileMenuOpen(true)}>
              <List size={28} />
            </button>

            <h2>
              {location.pathname === '/dashboard' && 'Dashboard'}
              {location.pathname === '/profile' && 'Farm Profile'}
              {location.pathname === '/crops' && 'Crop Management'}
              {location.pathname === '/recommendations' && 'Crop Recommendations'}
              {location.pathname === '/disease-detect' && 'Disease Detection'}
              {location.pathname === '/admin' && 'Admin Dashboard'}
            </h2>
          </div>

          <div className="weather">
            <CloudSun size={24} />
            <span>28°C, Partly Cloudy</span>
          </div>
        </header>

        <Outlet />
      </main>
    </div>
  );
};

export default Layout;
