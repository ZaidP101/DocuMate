import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'path';

export default defineConfig({
  plugins: [react()],

  build: {
    outDir: path.resolve(__dirname, 'dist'),
  },
  server: {
    port: 5173,
    proxy: {
      '/api': "http://localhost:8181"
    },
  },
});
