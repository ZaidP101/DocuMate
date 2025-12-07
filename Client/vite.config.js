import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react';
import path from 'path';

export default defineConfig(({ mode }) => ({
  plugins: [react()],
  
  // CRITICAL: Always use './' for Electron
  base: './',
  
  build: {
    outDir: 'dist',
    emptyOutDir: true,
    // Add sourcemap for debugging
    sourcemap: mode === 'development',
    rollupOptions: {
      input: {
        main: path.resolve(__dirname, 'index.html')
      },
      output: {
        // Ensure relative paths
        assetFileNames: 'assets/[name].[hash][extname]'
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
    // Enable CORS for Electron
    cors: true
  },
  
  resolve: {
    alias: {
      '@': path.resolve(__dirname, './src')
    }
  },
  
  // Optimize for Electron
  optimizeDeps: {
    exclude: ['electron']
  }
}));