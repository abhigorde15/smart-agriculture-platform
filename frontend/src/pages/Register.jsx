import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { authService } from '../services/api';
import { useAuth } from '../context/AuthContext';
import { Plant } from '@phosphor-icons/react';

const Register = () => {
    const [formData, setFormData] = useState({
        fullName: '',
        email: '',
        password: '',
        role: 'FARMER'
    });
    const [error, setError] = useState('');
    const { login } = useAuth();
    const navigate = useNavigate();

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
           
            const response = await authService.register(formData);
            login(response.data);
            navigate('/dashboard');
        } catch (err) {
            setError('Registration failed. Email might be in use.');
        }
    };

    return (
        <div className="flex items-center justify-center min-h-screen" style={{ background: 'linear-gradient(135deg, var(--color-secondary) 0%, var(--color-bg) 100%)' }}>
            <div className="card register-card" style={{ width: '400px', maxWidth: '100%' }}>
                <div className="text-center mb-4">
                    <Plant size={48} color="var(--color-primary)" weight="fill" />
                    <h2 style={{ color: 'var(--color-primary)', fontSize: '1.5rem', fontWeight: 'bold' }}>Create Account</h2>
                    <p style={{ color: '#666' }}>Join the Smart Agriculture Platform</p>
                </div>

                {error && <div style={{ color: 'var(--color-earth)', marginBottom: '1rem', textAlign: 'center' }}>{error}</div>}

                <form onSubmit={handleSubmit} className="grid gap-4">
                    <div>
                        <label style={{ display: 'block', marginBottom: '0.5rem' }}>Full Name</label>
                        <input
                            type="text"
                            name="fullName"
                            value={formData.fullName}
                            onChange={handleChange}
                            required
                            style={{ width: '100%' }}
                        />
                    </div>
                    <div>
                        <label style={{ display: 'block', marginBottom: '0.5rem' }}>Email</label>
                        <input
                            type="email"
                            name="email"
                            value={formData.email}
                            onChange={handleChange}
                            required
                            style={{ width: '100%' }}
                        />
                    </div>
                    <div>
                        <label style={{ display: 'block', marginBottom: '0.5rem' }}>Password</label>
                        <input
                            type="password"
                            name="password"
                            value={formData.password}
                            onChange={handleChange}
                            required
                            style={{ width: '100%' }}
                        />
                    </div>
                   
                    <button type="submit" className="btn btn-primary" style={{ width: '100%' }}>
                        Register
                    </button>
                </form>
                <div className="text-center mt-4">
                    <p>Already have an account? <Link to="/login" style={{ color: 'var(--color-primary)', fontWeight: 'bold' }}>Login</Link></p>
                </div>
            </div>

            
        </div>
    );
};

export default Register;
