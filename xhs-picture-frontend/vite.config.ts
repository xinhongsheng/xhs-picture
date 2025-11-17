import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { AntDesignVueResolver } from 'unplugin-vue-components/resolvers'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
    // 自动导入 Vue 相关函数
    AutoImport({
      imports: [
        'vue',
        'vue-router',
        'pinia'
      ],
      // 自动导入 Ant Design Vue 的组件类型
      resolvers: [AntDesignVueResolver()],
      // 生成类型声明文件
      dts: true,
      // eslint globals Docs - https://eslint.org/docs/user-guide/configuring/language-options#specifying-globals
      eslintrc: {
        enabled: true,
      },
    }),
    // 自动导入组件
    Components({
      // Ant Design Vue 组件自动导入
      resolvers: [
        AntDesignVueResolver({
          importStyle: false, // css in js
        }),
      ],
      // 生成类型声明文件
      dts: true,
    }),
  ],
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
})
