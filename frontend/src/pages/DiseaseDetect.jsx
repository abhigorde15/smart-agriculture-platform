import { useState } from 'react';
import { aiService } from '../services/api';
import { Scan, WarningCircle, CheckCircle } from '@phosphor-icons/react';

const DiseaseDetect = () => {
    const [file, setFile] = useState(null);
    const [preview, setPreview] = useState(null);
    const [result, setResult] = useState(null);
    const [loading, setLoading] = useState(false);

    const handleFileChange = (e) => {
        const selected = e.target.files[0];
        if (selected) {
            setFile(selected);
            setPreview(URL.createObjectURL(selected));
            setResult(null);
        }
    };

    const handleUpload = async () => {
        if (!file) return;
        setLoading(true);
        const formData = new FormData();
        formData.append('file', file);

        try {
           
            const res = await aiService.detectDisease(formData);
            setResult(res.data);
        } catch (err) {
            alert('Detection failed: ' + err);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="disease-detect-page">
            <div className="grid grid-cols-2 gap-8 disease-detect-container">
                {/* Upload Section */}
                <div className="card">
                    <h2 className="section-title text-xl mb-4">Upload Leaf Image</h2>
                    <div
                        className="border-2 border-dashed border-gray-300 rounded-lg p-8 text-center bg-gray-50 mb-4 relative"
                        style={{ border: '2px dashed #cbd5e0', background: '#f7fafc', padding: '2rem' }}
                    >
                        {preview ? (
                            <img
                                src={preview}
                                alt="Preview"
                                className="max-h-64 mx-auto rounded shadow-sm"
                                style={{ maxHeight: '16rem', borderRadius: '0.5rem' }}
                            />
                        ) : (
                            <div className="text-gray-400">
                                <Scan size={48} className="mx-auto mb-2" />
                                <p>Click to upload or drag and drop</p>
                            </div>
                        )}
                        <input
                            type="file"
                            accept="image/*"
                            onChange={handleFileChange}
                            className="absolute inset-0 w-full h-full opacity-0 cursor-pointer"
                        />
                    </div>
                    <button
                        onClick={handleUpload}
                        disabled={!file || loading}
                        className={`btn btn-primary w-full ${!file ? 'opacity-50 cursor-not-allowed' : ''}`}
                    >
                        {loading ? 'Analyzing...' : 'Analyze Image'}
                    </button>
                </div>

                {/* Result Section */}
                <div>
                    {result ? (
                        <div className="card h-full bg-white">
                            <div
                                className={`p-4 rounded-t-lg ${
                                    result.disease === 'Healthy'
                                        ? 'bg-green-100 text-green-800'
                                        : 'bg-red-100 text-red-800'
                                }`}
                                style={{ background: result.disease === 'Healthy' ? '#c6f6d5' : '#fed7d7' }}
                            >
                                <h2 className="text-2xl font-bold flex items-center gap-2">
                                    {result.disease === 'Healthy' ? <CheckCircle /> : <WarningCircle />}
                                    {result.disease}
                                </h2>
                                <p className="font-semibold opacity-75">
                                    Confidence: {Math.round(result.confidence * 100)}%
                                </p>
                            </div>
                            <div className="p-6">
                                <h3 className="font-bold text-lg mb-2 text-gray-800">Treatment / Suggestion:</h3>
                                <p className="text-gray-600 leading-relaxed bg-gray-50 p-4 rounded-lg border">
                                    {result.suggestion}
                                </p>
                            </div>
                        </div>
                    ) : (
                        <div className="card h-full flex flex-col items-center justify-center text-gray-400 text-center p-8">
                            <Scan size={64} className="mb-4 opacity-50" />
                            <h3 className="font-bold text-lg">AI Disease Detection</h3>
                            <p>
                                Upload a clear image of a crop leaf. Our AI will analyze it for signs of diseases and
                                suggest remedies.
                            </p>
                        </div>
                    )}
                </div>
            </div>

          
        </div>
    );
};

export default DiseaseDetect;
