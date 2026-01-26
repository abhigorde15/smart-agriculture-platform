import { useState, useEffect } from 'react';
import { adminService } from '../services/api';
import { Users, Warning, Tree } from '@phosphor-icons/react';

const AdminDashboard = () => {
    const [farmers, setFarmers] = useState([]);
    const [cropPreds, setCropPreds] = useState([]);
    const [diseasePreds, setDiseasePreds] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [fRes, cRes, dRes] = await Promise.all([
                    adminService.getAllFarmers(),
                    adminService.getCropRecommendations(),
                    adminService.getDiseaseDetections()
                ]);
                setFarmers(fRes.data);
                setCropPreds(cRes.data);
                setDiseasePreds(dRes.data);
            } catch (err) {
                console.error("Admin data fetch failed");
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, []);

    if (loading) return <div>Loading Admin Data...</div>;

    return (
        <div className="grid gap-8">
            {/* Stats Cards */}
            <div className="grid grid-cols-3 gap-6 admin-stats-grid">
                <div className="card text-center p-6 bg-blue-50 border-blue-200" style={{ background: '#ebf8ff', border: '1px solid #bee3f8' }}>
                    <Users size={40} className="mx-auto text-blue-600 mb-2" />
                    <h2 className="text-3xl font-bold text-blue-900">{farmers.length}</h2>
                    <p className="text-blue-700">Total Users</p>
                </div>
                <div className="card text-center p-6 bg-green-50 border-green-200" style={{ background: '#f0fff4', border: '1px solid #c6f6d5' }}>
                    <Tree size={40} className="mx-auto text-green-600 mb-2" />
                    <h2 className="text-3xl font-bold text-green-900">{cropPreds.length}</h2>
                    <p className="text-green-700">Crop Recommendations</p>
                </div>
                <div className="card text-center p-6 bg-orange-50 border-orange-200" style={{ background: '#fffaf0', border: '1px solid #feebc8' }}>
                    <Warning size={40} className="mx-auto text-orange-600 mb-2" />
                    <h2 className="text-3xl font-bold text-orange-900">{diseasePreds.length}</h2>
                    <p className="text-orange-700">Disease Scans</p>
                </div>
            </div>

            <div className="grid grid-cols-2 gap-8 admin-content-grid">
                {/* Farmers List */}
                <div className="card">
                    <h3 className="section-title text-xl mb-4">Registered Farmers</h3>
                    <div className="overflow-auto max-h-64">
                        <table className="w-full text-left" style={{ width: '100%', borderCollapse: 'collapse' }}>
                            <thead className="bg-gray-100 sticky top-0" style={{ background: '#f7fafc' }}>
                                <tr>
                                    <th className="p-2">Name</th>
                                    <th className="p-2">Email</th>
                                    <th className="p-2">Role</th>
                                </tr>
                            </thead>
                            <tbody>
                                {farmers.map(f => (
                                    <tr key={f.id} className="border-b hover:bg-gray-50">
                                        <td className="p-2">{f.fullName}</td>
                                        <td className="p-2 text-sm text-gray-600">{f.email}</td>
                                        <td className="p-2 text-xs font-bold">{f.role}</td>
                                    </tr>
                                ))}
                            </tbody>
                        </table>
                    </div>
                </div>

                {/* Recent Disease Scans */}
                <div className="card">
                    <h3 className="section-title text-xl mb-4">Recent Disease Scans</h3>
                    <div className="overflow-auto max-h-64 space-y-3">
                        {diseasePreds.slice(-5).reverse().map(d => (
                            <div key={d.id} className="p-3 border rounded flex justify-between items-center">
                                <div>
                                    <p className="font-bold text-gray-800">{d.detectedDisease}</p>
                                    <p className="text-xs text-gray-500">Confidence: {(d.confidence * 100).toFixed(0)}%</p>
                                </div>
                                <span className="text-xs bg-gray-100 px-2 py-1 rounded text-gray-600">
                                    {new Date(d.detectedAt).toLocaleDateString()}
                                </span>
                            </div>
                        ))}
                    </div>
                </div>
            </div>

           
        </div>
    );
};

export default AdminDashboard;
