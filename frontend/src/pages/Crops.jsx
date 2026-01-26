import { useState, useEffect } from 'react';
import { cropService } from '../services/api';
import { Plus, Tree, CheckCircle } from '@phosphor-icons/react';

const Crops = () => {
    const [crops, setCrops] = useState([]);
    const [showForm, setShowForm] = useState(false);
    const [formData, setFormData] = useState({
        name: '',
        sowingDate: '',
        expectedHarvestDate: ''
    });

    useEffect(() => {
        loadCrops();
    }, []);

    const loadCrops = async () => {
        try {
            const res = await cropService.getMyCrops();
            setCrops(res.data);
        } catch (err) {
            console.error("Failed to load crops");
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await cropService.addCrop(formData);
            setShowForm(false);
            setFormData({ name: '', sowingDate: '', expectedHarvestDate: '' });
            loadCrops();
        } catch (err) {
            alert('Failed to add crop');
        }
    };

    const handleHarvest = async (id) => {
        try {
            await cropService.updateStatus(id, 'HARVESTED');
            loadCrops();
        } catch (err) {
            alert('Update failed');
        }
    };

    return (
        <div>
            <div className="flex justify-between items-center mb-6">
                <h2 className="section-title mb-0" style={{ marginBottom: 0 }}>My Crops</h2>
                <button onClick={() => setShowForm(!showForm)} className="btn btn-primary">
                    <Plus size={20} />
                    Add New Crop
                </button>
            </div>

            {showForm && (
                <div className="card mb-6" style={{ background: '#f0fff4', border: '1px solid #c6f6d5' }}>
                    <h3 className="font-bold mb-4">Add New Crop</h3>
                    <form onSubmit={handleSubmit} className="grid grid-cols-2 gap-4">
                        <div className="col-span-2">
                            <label className="block mb-1 text-sm font-semibold">Crop Name</label>
                            <input type="text" placeholder="e.g. Wheat, Rice, Cotton" value={formData.name} onChange={e => setFormData({ ...formData, name: e.target.value })} className="w-full" required />
                        </div>
                        <div>
                            <label className="block mb-1 text-sm font-semibold">Sowing Date</label>
                            <input type="date" value={formData.sowingDate} onChange={e => setFormData({ ...formData, sowingDate: e.target.value })} className="w-full" required />
                        </div>
                        <div>
                            <label className="block mb-1 text-sm font-semibold">Exp. Harvest Date</label>
                            <input type="date" value={formData.expectedHarvestDate} onChange={e => setFormData({ ...formData, expectedHarvestDate: e.target.value })} className="w-full" />
                        </div>
                        <div className="col-span-2 flex justify-end gap-2">
                            <button type="button" onClick={() => setShowForm(false)} className="btn btn-outline" style={{ padding: '0.5rem 1rem' }}>Cancel</button>
                            <button type="submit" className="btn btn-primary" style={{ padding: '0.5rem 1rem' }}>Save</button>
                        </div>
                    </form>
                </div>
            )}

            <div className="grid grid-cols-2 gap-4 crops-grid">
                {crops.length === 0 && !showForm && (
                    <div className="col-span-2 text-center py-8 text-gray-500 card">
                        <Tree size={48} className="mx-auto mb-2 opacity-50" />
                        <p>You haven't added any crops yet.</p>
                    </div>
                )}

                {crops.map(crop => (
                    <div key={crop.id} className="card relative overflow-hidden">
                        <div className="flex justify-between items-start">
                            <div>
                                <h3 className="text-xl font-bold text-green-800">{crop.name}</h3>
                                <div className="text-sm text-gray-600 mt-2 space-y-1">
                                    <p>Sown: {crop.sowingDate}</p>
                                    <p>Harvest: {crop.expectedHarvestDate}</p>
                                </div>
                            </div>
                            <span className={`px-3 py-1 rounded-full text-xs font-bold ${crop.status === 'ACTIVE' ? 'bg-green-100 text-green-800' : 'bg-gray-200 text-gray-600'}`}>
                                {crop.status}
                            </span>
                        </div>

                        {crop.status === 'ACTIVE' && (
                            <button
                                onClick={() => handleHarvest(crop.id)}
                                className="mt-4 w-full py-2 bg-yellow-100 text-yellow-800 rounded hover:bg-yellow-200 transition-colors flex justify-center items-center gap-2"
                                style={{ background: '#feebc8', color: '#744210', fontSize: '0.9rem' }}
                            >
                                <CheckCircle size={18} />
                                Mark as Harvested
                            </button>
                        )}
                    </div>
                ))}
            </div>

          
        </div>
    );
};

export default Crops;
