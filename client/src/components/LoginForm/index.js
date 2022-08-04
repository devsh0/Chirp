import EmailField from "../EmailField";
import PasswordField from "../PasswordField";
import { useState } from "react";
import ActionButton from "../ActionButton";
import ActionLink from "../ActionLink";

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
      <p>
        <ActionLink id={"signup-link"} btnText={"Sign Up"} onLinkClick={onLinkClick} />
        <span> &#xB7; </span>
        <ActionLink
          id={"password-recovery-link"}
          btnText={"Forgot Password?"}
          onLinkClick={onLinkClick}
        />
      </p>
    </form>
  );
}
