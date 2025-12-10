import { defineStore } from 'pinia';
import { api } from '../api';

export const useUsersStore = defineStore('users', {
  state: () => ({
    items: [],
    loading: false,
    error: null,
  }),

  actions: {
    async fetchAll() {
      this.loading = true;
      this.error = null;
      try {
        const { data } = await api.get('/api/users');
        this.items = data;
      } catch (e) {
        this.error = 'Failed to load users';
        console.error(e);
      } finally {
        this.loading = false;
      }
    },

    async createUser(name, email) {
      this.error = null;
      try {
        const { data } = await api.post('/api/users', { name, email });
        this.items.push(data);
      } catch (e) {
        this.error = 'Failed to create user';
        console.error(e);
      }
    },
  },
});
