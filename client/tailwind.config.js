/** @type {import("tailwindcss").Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      colors: {
        "blue-twitter": "#4D9FEB",
        "blue-twitter-dark": "#3a8de7",
        "dark-primary": "#202124",
        "dark-secondary": "rgb(24, 24, 27)",
      },
    },
  },
  plugins: [],
};
