import axios from 'axios'

const api = axios.create({
    baseURL: 'http://localhost:8080/api/'
});

const onFulfilled = requestConfig => {
    const token = localStorage.getItem('token');
    if (token) {
        requestConfig.headers.Authorization = `Bearer ${token}`
    }
    return requestConfig
};

api.interceptors.request.use(onFulfilled);

export default api;