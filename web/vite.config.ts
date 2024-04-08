import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'

// https://vitejs.dev/config/
export default defineConfig({
	plugins: [react()],

	publicDir: "../src/main/resources/assets/echoes_of_the_elders/textures"
})
