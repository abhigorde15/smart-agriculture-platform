import { useState, useEffect } from 'react';
import { profileService } from '../services/api';
import { FloppyDisk } from '@phosphor-icons/react';

const Profile = () => {
    const [formData, setFormData] = useState({
        landArea: '',
        soilType: 'LOAMY',
        irrigationMethod: 'RAIN_FED',
        state: '',
        district: '',
        preferredLanguage: 'English'
    });
    const [loading, setLoading] = useState(true);
    const [msg, setMsg] = useState('');

    useEffect(() => {
        const fetchProfile = async () => {
            try {
                const res = await profileService.getProfile();
                if (res.data) {
                    setFormData(res.data);
                }
            } catch (err) {
                console.log("No existing profile found or error fetching"+err);
            } finally {
                setLoading(false);
            }
        };
        fetchProfile();
    }, []);

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await profileService.updateProfile(formData);
            setMsg('Profile updated successfully!');
            setTimeout(() => setMsg(''), 3000);
        } catch (err) {
            setMsg('Failed to update profile.'+err);
        }
    };

    if (loading) return <div>Loading Profile...</div>;

    return (
        <div className="card max-w-2xl">
            <h2 className="section-title" style={{ fontSize: '1.5rem', marginBottom: '1.5rem' }}>Farm Details</h2>
            {msg && <div className={`p-3 mb-4 rounded ${msg.includes('success') ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800'}`}>{msg}</div>}

            <form onSubmit={handleSubmit} className="grid grid-cols-2 gap-4 profile-form">
                <div>
                    <label className="block mb-2 font-semibold">Land Area (Acres)</label>
                    <input
                        type="number"
                        step="0.01"
                        name="landArea"
                        value={formData.landArea}
                        onChange={handleChange}
                        className="w-full"
                        required
                    />
                </div>

                <div>
                    <label className="block mb-2 font-semibold">Soil Type</label>
                    <select name="soilType" value={formData.soilType} onChange={handleChange} className="w-full">
                        <option value="SANDY">Sandy</option>
                        <option value="LOAMY">Loamy</option>
                        <option value="CLAY">Clay</option>
                        <option value="SILT">Silt</option>
                        <option value="PEAT">Peat</option>
                        <option value="CHALKY">Chalky</option>
                        <option value="BLACK">Black</option>
                    </select>
                </div>

                <div>
                    <label className="block mb-2 font-semibold">Irrigation Method</label>
                    <select name="irrigationMethod" value={formData.irrigationMethod} onChange={handleChange} className="w-full">
                        <option value="DRIP">Drip</option>
                        <option value="CANAL">Canal</option>
                        <option value="RAIN_FED">Rain Fed</option>
                        <option value="SPRINKLER">Sprinkler</option>
                        <option value="TUBEWELL">Tubewell</option>
                    </select>
                </div>

                <div>
                    <label className="block mb-2 font-semibold">Preferred Language</label>
                    <select name="preferredLanguage" value={formData.preferredLanguage} onChange={handleChange} className="w-full">
                        <option value="English">English</option>
                        <option value="Hindi">Hindi</option>
                        <option value="Marathi">Marathi</option>
                    </select>
                </div>

                <div>
                    <label className="block mb-2 font-semibold">State</label>
                    <input
                        type="text"
                        name="state"
                        value={formData.state || ''}
                        onChange={handleChange}
                        className="w-full"
                    />
                </div>

                <div>
                    <label className="block mb-2 font-semibold">District</label>
                    <input
                        type="text"
                        name="district"
                        value={formData.district || ''}
                        onChange={handleChange}
                        className="w-full"
                    />
                </div>

                <div className="col-span-2 mt-4">
                    <button type="submit" className="btn btn-primary w-full">
                        <FloppyDisk size={20} />
                        Save Profile
                    </button>
                </div>
            </form>

         
        </div>
    );
};

export default Profile;
