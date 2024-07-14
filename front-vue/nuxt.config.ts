// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  postcss: {
    plugins: {
      'postcss-px-to-viewport': {
        viewportWidth: 1920,
      }
    }
  },
  runtimeConfig: {
    public: {

    }
  },

  routeRules: {
    '/**': { isr: true }
  },

  modules: ["@nuxt/ui"],

  plugins: [
    '~/plugins/element-ui'
  ],
  vite: {
    //请求代理
    server: {
      proxy: {
        '/api': {
          target: 'http://127.0.0.1:8080', // 实际请求地址
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/api/, "/api"),
        },
        '/static': {
          target: 'http://127.0.0.1:8080', // 实际请求地址
          changeOrigin: true,
          rewrite: (path) => path.replace(/^\/static/, "/static"),
        },
      },
    }
  },
  compatibilityDate: '2024-07-06',
})