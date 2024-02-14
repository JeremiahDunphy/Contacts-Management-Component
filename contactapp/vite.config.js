import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';

// https://vitejs.dev/config/
export default defineConfig({
  plugins: [react()],
  server: {
    proxy: {
      // Proxying API requests. This setup is ideal for a Spring Boot backend
      // as it allows all requests starting with /api to be forwarded to localhost:8080.
      // This is useful for development, avoiding CORS issues, and making it easier to switch
      // between development and production environments.
      '/api': {
        target: 'http://localhost:8080', // Your Spring Boot backend URL
        changeOrigin: true, // Important for handling CORS
        rewrite: (path) => path.replace(/^\/api/, ''), // Removes /api before forwarding to the backend
      }
    }
  },

});
