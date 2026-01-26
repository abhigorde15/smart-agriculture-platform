import { useState, useEffect } from 'react';
import { aiService, profileService } from '../services/api';
import { MagicWand, Plant } from '@phosphor-icons/react';

const Recommendations = () => {
    const [formData, setFormData] = useState({
        N: 50, P: 50, K: 50,
        temperature: 25, humidity: 60, ph: 6.5, rainfall: 100
    });
    const [result, setResult] = useState(null);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        // Pre-fill from profile
        profileService.getProfile()
            .then(res => {
                if (res.data) {
                    setFormData(prev => ({
                        ...prev,
                        soilType: res.data.soilType || 'LOAMY',
                        landArea: res.data.landArea || 0
                    }));
                }
            })
            .catch(() => {});
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);

        try {
          
            const res = await aiService.recommendCrop(formData);
           
            setResult({
                recommendedCrop: res.data.recommended_crop,
                confidence: res.data.confidence
            });
        } catch (err) {
            alert('Recommendation failed: ' + err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="recommendations-page">
            <div className="grid grid-cols-2 gap-8 recommendations-container">
                {/* Form Section */}
                <div className="card">
                    <h2 className="text-xl font-bold mb-4 flex items-center gap-2">
                        <MagicWand size={24} color="var(--color-accent)" />
                        Find Best Crop
                    </h2>
                    <form onSubmit={handleSubmit} className="grid grid-cols-2 gap-4">
                        {/* Input fields */}
                        {['N', 'P', 'K', 'ph', 'temperature', 'humidity', 'rainfall'].map(key => (
                            <div key={key}>
                                <label className="text-sm font-semibold">{key}</label>
                                <input
                                    type="number"
                                    step={key === 'ph' ? "0.1" : undefined}
                                    value={formData[key]}
                                    onChange={e => setFormData({ ...formData, [key]: Number(e.target.value) })}
                                    className="w-full"
                                    required
                                />
                            </div>
                        ))}

                        <div className="col-span-2 mt-4">
                            <button type="submit" className="btn btn-primary w-full" disabled={loading}>
                                {loading ? 'Analyzing...' : 'Get Recommendation'}
                            </button>
                        </div>
                    </form>
                </div>

                {/* Result Section */}
                <div>
                    {result ? (
                        <div className="card text-center py-12 bg-green-50 border-2 border-green-200">
                            <Plant size={64} className="mx-auto text-green-600 mb-4" />
                            <h3 className="text-gray-500 uppercase tracking-wider text-sm font-bold">Recommended Crop</h3>
                            <h1 className="text-4xl font-bold text-green-800 my-2">{result.recommendedCrop}</h1>
                            <div className="inline-block bg-white px-4 py-1 rounded-full shadow-sm text-sm font-semibold text-gray-600 mt-2">
                                {Math.round(result.confidence * 100)}% Confidence
                            </div>
                            <div className="mt-8 p-4 bg-white rounded-lg text-left mx-8 shadow-sm">
                                <h4 className="font-bold mb-2">Why this crop?</h4>
                                <p className="text-sm text-gray-600">
                                    Based on your soil's NPK values and current weather conditions, {result.recommendedCrop} has the highest probability of optimal yield.
                                </p>
                            </div>
                        </div>
                    ) : (
                        <div className="card h-full flex flex-col items-center justify-center text-gray-400 text-center p-8">
                            <MagicWand size={64} className="mb-4 opacity-50" />
                            <p>Fill in the soil details to get an AI-powered crop recommendation tailored for your farm.</p>
                        </div>
                    )}
                </div>
            </div>

          
        </div>
    );
};

export default Recommendations;
