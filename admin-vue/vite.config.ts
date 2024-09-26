import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vitejs.dev/config/
export default defineConfig({
  base: './',
  plugins: [vue()],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  //请求代理
  server: {
    proxy: {
      '/admin': {
        target: 'http://127.0.0.1:8081', // 实际请求地址
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/admin/, "/admin"),
      },
      '/static': {
        target: 'http://127.0.0.1:8081', // 实际请求地址
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/static/, "/static"),
      },
    },
  }
})
