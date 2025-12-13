/** @type {import('tailwindcss').Config} */
module.exports = {
  darkMode: 'class',   // 👈 REQUIRED for toggle to work
  content: [
    "./src/**/*.{html,ts,scss}"
  ],
  theme: {
    extend: {},
  },
  plugins: [
    require('tailwindcss-filters')
  ],
}
