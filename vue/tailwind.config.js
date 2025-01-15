/** @type {import('tailwindcss').Config} */
export default {
    darkMode: ["class"],
    safelist: ["dark"],
    prefix: "",

    content: [
        './pages/**/*.{js,jsx,vue}',
        './components/**/*.{js,jsx,vue}',
        './app/**/*.{js,jsx,vue}',
        './src/**/*.{js,jsx,vue}',
    ],

  theme: {
    extend: {},
  },
  plugins: [],
}

