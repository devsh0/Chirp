import EmailField from "../EmailField";
import PasswordField from "../PasswordField";
import { useState } from "react";
import ActionButton from "../SignupButton";

export default function LoginForm({ onLinkClick }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  function handleEmailInputChange(event) {
    setEmail(event.target.value);
  }

  function handlePasswordInputChange(event) {
    setPassword(event.target.value);
  }

  function handleSubmit() {
    console.log("Submitting login form");
  }

  return (
    <form className={"flex flex-col space-y-2"}>
      <EmailField onEmailInputChange={handleEmailInputChange}>Email or Username</EmailField>
      <PasswordField onPasswordInputChange={handlePasswordInputChange} />
      <ActionButton btnText={"Log In"} onSubmit={handleSubmit} />
      <p className={"mt-2 text-sm"}>
        <button
          id={"signup-link"}
          className={"transition-colors hover:text-blue-twitter-dark text-blue-twitter"}
          onClick={onLinkClick}
        >
          Sign Up
        </button>
        <span> &#xB7; </span>
        <button
          id={"password-recovery-link"}
          className={"transition-colors hover:text-blue-twitter-dark text-blue-twitter"}
          onClick={onLinkClick}
        >
          Forgot Password?
        </button>
      </p>
    </form>
  );
}
