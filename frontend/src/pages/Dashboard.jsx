import { useState, useEffect } from 'react';
import { weatherService, cropService } from '../services/api';
import { CloudRain, Warning, Plant, ArrowRight } from '@phosphor-icons/react';
import { Link } from 'react-router-dom';

const Dashboard = () => {
    const [alerts, setAlerts] = useState([]);
    const [crops, setCrops] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                // Fetch weather alerts
                const weatherRes = await weatherService.getAlerts();
                setAlerts(weatherRes.data);

                // Fetch recent crops
                const cropRes = await cropService.getMyCrops();
                setCrops(cropRes.data.slice(0, 3)); // Show only 3 recent
            } catch (err) {
                console.error("Failed to fetch dashboard data", err);
            }
        };
        fetchData();
    }, []);

    return (
        <div className="grid gap-6">
            {/* Weather Alerts Section */}
            {alerts.length > 0 && (
                <div className="card" style={{ borderLeft: '5px solid var(--color-earth)' }}>
                    <h3 className="section-title" style={{ fontSize: '1.25rem', marginBottom: '1rem', borderLeft: 'none', paddingLeft: 0 }}>Critical Alerts</h3>
                    <div className="grid gap-3">
                        {alerts.map((alert, idx) => (
                            <div key={idx} className="flex items-center gap-3 p-3 bg-red-50 rounded-md" style={{ background: '#fff5f5', border: '1px solid #fed7d7' }}>
                                <Warning size={32} color="var(--color-earth)" />
                                <div>
                                    <h4 style={{ fontWeight: 'bold', color: 'var(--color-earth)' }}>{alert.type.replace('_', ' ')}</h4>
                                    <p>{alert.message}</p>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            )}

            <div className="grid grid-cols-2 gap-6 dashboard-grid">
                {/* Active Crops Summary */}
                <div className="card">
                    <div className="flex justify-between items-center mb-4">
                        <h3 style={{ fontSize: '1.25rem', fontWeight: 'bold', color: 'var(--color-primary)' }}>My Crops</h3>
                        <Link to="/crops" style={{ color: 'var(--color-primary)', display: 'flex', alignItems: 'center' }}>View All <ArrowRight /></Link>
                    </div>

                    {crops.length === 0 ? (
                        <p style={{ color: '#666' }}>No crops added yet. <Link to="/crops" style={{ fontWeight: 'bold' }}>Add one now?</Link></p>
                    ) : (
                        <div className="grid gap-3">
                            {crops.map(crop => (
                                <div key={crop.id} className="flex justify-between items-center p-3 bg-green-50 rounded-md" style={{ background: 'var(--color-secondary)' }}>
                                    <div className="flex items-center gap-3">
                                        <div style={{ background: 'white', padding: '0.5rem', borderRadius: '50%' }}>
                                            <Plant size={20} color="var(--color-primary)" />
                                        </div>
                                        <div>
                                            <p className="font-bold">{crop.name}</p>
                                            <p className="text-xs">Sown: {crop.sowingDate}</p>
                                        </div>
                                    </div>
                                    <span style={{
                                        fontSize: '0.8rem',
                                        padding: '0.25rem 0.5rem',
                                        borderRadius: '1rem',
                                        background: crop.status === 'ACTIVE' ? '#c6f6d5' : '#e2e8f0',
                                        color: crop.status === 'ACTIVE' ? '#22543d' : '#4a5568'
                                    }}>
                                        {crop.status}
                                    </span>
                                </div>
                            ))}
                        </div>
                    )}
                </div>

                {/* Quick Actions */}
                <div className="grid gap-4">
                    <Link to="/recommendations" className="card flex items-center justify-between hover:no-underline" style={{ background: 'linear-gradient(to right, #2d6a4f, #40916c)', color: 'white' }}>
                        <div>
                            <h3 style={{ fontSize: '1.25rem', fontWeight: 'bold' }}>New Crop Recommendation</h3>
                            <p style={{ opacity: 0.9 }}>AI-powered suggestions for your soil.</p>
                        </div>
                        <ArrowRight size={24} />
                    </Link>

                    <Link to="/disease-detect" className="card flex items-center justify-between hover:no-underline" style={{ background: 'white', border: '2px solid var(--color-primary)' }}>
                        <div>
                            <h3 style={{ fontSize: '1.25rem', fontWeight: 'bold', color: 'var(--color-primary)' }}>Check Plant Health</h3>
                            <p style={{ color: '#666' }}>Upload a photo to detect diseases.</p>
                        </div>
                        <Plant size={24} color="var(--color-primary)" />
                    </Link>
                </div>
            </div>

           
        </div>
    );
};

export default Dashboard;
