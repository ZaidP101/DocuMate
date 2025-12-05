import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'path';

export default defineConfig(({ mode }) => ({
  plugins: [react()],
  
  // Important: Use relative paths for production (Electron)
  base: mode === 'production' ? './' : '/',
  
  build: {
    outDir: 'dist',
    emptyOutDir: true,
    rollupOptions: {
      input: {
        main: path.resolve(__dirname, 'index.html')
      }
    }
  },
  
  server: {
    port: 5173,
    proxy: {
      '/api': {
        target: 'http://localhost:8181',
        changeOrigin: true
      }
    },
  },
  
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src')
    }
  }
}));