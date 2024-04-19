import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react'
import DynamicPublicDirectory from "vite-multiple-assets";

const dirAssets = ["public", "../src/main/resources/assets/echoes_of_the_elders/textures"];

// https://vitejs.dev/config/
export default defineConfig({
	plugins: [
		DynamicPublicDirectory(dirAssets),
		react()
	],
})
