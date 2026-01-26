import { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import { authService } from '../services/api';
import { useAuth } from '../context/AuthContext';
import { Plant, User } from '@phosphor-icons/react';

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const { login } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
          
            const response = await authService.login({ email, password });
            login(response.data);
            if (response.data.role === 'ADMIN') {
                navigate('/admin');
            } else {
                navigate('/dashboard');
            }
        } catch (err) {
            setError('Invalid email or password');
        }
    };

    return (
        <div className="flex items-center justify-center min-h-screen bg-green-50" style={{ background: 'linear-gradient(135deg, var(--color-secondary) 0%, var(--color-bg) 100%)' }}>
            <div className="card login-card" style={{ width: '400px', maxWidth: '100%' }}>
                <div className="text-center mb-4">
                    <Plant size={48} color="var(--color-primary)" weight="fill" />
                    <h2 style={{ color: 'var(--color-primary)', fontSize: '1.5rem', fontWeight: 'bold' }}>Farmer Helper</h2>
                    <p style={{ color: '#666' }}>Welcome back, please login.</p>
                </div>

                {error && <div style={{ color: 'var(--color-earth)', marginBottom: '1rem', textAlign: 'center' }}>{error}</div>}

                <form onSubmit={handleSubmit} className="grid gap-4">
                    <div>
                        <label style={{ display: 'block', marginBottom: '0.5rem' }}>Email</label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                            style={{ width: '100%' }}
                            placeholder="farmer@example.com"
                        />
                    </div>
                    <div>
                        <label style={{ display: 'block', marginBottom: '0.5rem' }}>Password</label>
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                            style={{ width: '100%' }}
                        />
                    </div>
                    <button type="submit" className="btn btn-primary" style={{ width: '100%' }}>
                        Login
                    </button>
                </form>
                <div className="text-center mt-4">
                    <p>Don't have an account? <Link to="/register" style={{ color: 'var(--color-primary)', fontWeight: 'bold' }}>Register</Link></p>
                </div>
            </div>

           
        </div>
    );
};

export default Login;
