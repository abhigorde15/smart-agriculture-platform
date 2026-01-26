import axios from 'axios';

const API_URL = 'https://smart-agriculture-platform-2.onrender.com/api';

const api = axios.create({
    baseURL: API_URL,
    headers: {
        'Content-Type': 'application/json',
    },
});

api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});

export const authService = {
    login: (credentials) => api.post('/auth/authenticate', credentials),
    register: (data) => api.post('/auth/register', data),
};

export const profileService = {
    getProfile: () => api.get('/profiles/me'),
    updateProfile: (data) => api.post('/profiles', data),
};

export const cropService = {
    getMyCrops: () => api.get('/crops'),
    addCrop: (data) => api.post('/crops', data),
    updateStatus: (id, status) => api.patch(`/crops/${id}/status`, { status }),
};

export const aiService = {
    recommendCrop: (data) => api.post('/ai/recommend', data),
    detectDisease: (formData) => api.post('/ai/detect', formData, {
        headers: { 'Content-Type': 'multipart/form-data' },
    }),
};

export const weatherService = {
    getAlerts: (location) => api.get('/weather/alerts', { params: { location } }),
};

export const adminService = {
    getAllFarmers: () => api.get('/admin/farmers'),
    getCropRecommendations: () => api.get('/admin/predictions/crops'),
    getDiseaseDetections: () => api.get('/admin/predictions/diseases'),
};

export default api;
