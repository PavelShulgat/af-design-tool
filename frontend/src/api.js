import axios from 'axios';

export const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080',
  headers: { "Content-type" : "application/json" },
});

api.interceptors.request.use((config) => {
  const token = localStorage.getItem("accessToken");
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

export const fetchAgroforestryTypes = () =>
  api.get('/api/agroforestry-types').then(res => res.data);

export const fetchTrees = () =>
  api.get('/api/trees').then(res => res.data);

export const fetchPlants = () =>
  api.get('/api/plants').then(res => res.data);

export const fetchTools = () =>
  api.get('/api/tools').then(res => res.data);

export const fetchOperations = () =>
  api.get('/api/operations').then(res => res.data);

export const register = (payload) =>
  api.post("/api/auth/register", payload).then((res) => res.data);

export const login = ({ email, password }) =>
  api.post("/api/auth/login", { email, password }).then((res) => res.data);

export const fetchLivestock = () => api.get("/api/livestock").then(r => r.data);