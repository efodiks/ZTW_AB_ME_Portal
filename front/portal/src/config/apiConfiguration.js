import axios from 'axios'

const api = axios.create({
    baseURL: 'http://3.127.146.178:8080/api/'
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