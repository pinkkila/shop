import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import path from "path"

// https://vite.dev/config/
export default defineConfig({
  plugins: [react()],
  resolve: {
    alias: {
      "@": path.resolve(__dirname, "./src"),
    },
  },
  base: '/shop',
  server: {
    host: '127.0.0.1',
    port: 5173,
    watch: {
      usePolling: true,
    },
  },
})
