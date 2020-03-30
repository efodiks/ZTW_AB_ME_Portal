import axios from 'axios'

const onFulfilled = requestConfig => {
    const token = localStorage.getItem('token');
    console.log(requestConfig);
    if (token) {
        requestConfig.headers.Authorization = `Bearer ${token}`
    }
};

axios.interceptors.request.use(onFulfilled);