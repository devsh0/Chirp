import React from "react";

const apiBase = "localhost:8080/api/v1";
const authBase = `${apiBase}/auth`;

const state = {
  api: {
    paths: {
      auth: {
        registration: `${authBase}/register`,
        email_verification: `${authBase}/verify`,
        login: `${authBase}/login`,
        password_reset: `${authBase}/reset-password`,
        new_password_creation: `${authBase}/create-new-password`,
        password_recovery: `${authBase}/recover-password`,
        logout: `${authBase}/logout`,
      },
    },
  },
  showDialog: false,
};

const AppContext = React.createContext(state);
export default AppContext;
