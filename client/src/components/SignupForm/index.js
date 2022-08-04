import ActionButton from "../ActionButton";
import EmailField from "../EmailField";
import UsernameField from "../UsernameField";
import PasswordField from "../PasswordField";
import { useState } from "react";
import ActionLink from "../ActionLink";

export default function SignupForm({ onLinkClick }) {
  const [email, setEmail] = useState("");
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  function handleEmailInputChange(event) {
    setEmail(event.target.value);
  }

  function handleUsernameInputChange(event) {
    setUsername(event.target.value);
  }

  function handlePasswordInputChange(event) {
    setPassword(event.target.value);
  }

  function handleSubmit() {
    console.log("Submitting sign up form");
    // TODO.
  }

  return (
    <form className={"flex flex-col space-y-2"}>
      <EmailField onEmailInputChange={handleEmailInputChange}>Email</EmailField>
      <UsernameField onUsernameInputChange={handleUsernameInputChange} />
      <PasswordField onPasswordInputChange={handlePasswordInputChange} />
      <ActionButton btnText={"Sign Up"} onSubmit={handleSubmit} />
      <ActionLink id={"login-link"} btnText={"Log In Instead"} onLinkClick={onLinkClick} />
    </form>
  );
}
