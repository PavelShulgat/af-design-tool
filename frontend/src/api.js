import axios from 'axios';

export const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || 'http://localhost:8080',
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