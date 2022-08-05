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
  const [emailError, setEmailError] = useState("");
  const [usernameError, setUsernameError] = useState("");
  const [passwordError, setPasswordError] = useState("");

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
    const emailRegex = /^[_a-zA-Z0-9]+@[-a-z0-9A-Z]+.[a-z]{2,4}$/;
    const usernameRegex = /^[a-zA-Z0-9_-]{3,30}$/;
    const passwordRegex = /.{8,}/;

    if (!emailRegex.test(email)) setEmailError("Invalid email!");
    else setEmailError("");

    if (!usernameRegex.test(username)) {
      if (username.length < 3 || username.length > 30)
        setUsernameError("Must be between 3-30 characters!");
      else setUsernameError("May only consist of alphanumeric characters and '-'!");
    } else setUsernameError("");

    if (!passwordRegex.test(password)) setPasswordError("Must be at least 8 characters!");
    else setPasswordError("");
  }

  return (
    <form className={"flex flex-col space-y-2"}>
      <EmailField onEmailInputChange={handleEmailInputChange} error={emailError}>
        Email
      </EmailField>
      <UsernameField onUsernameInputChange={handleUsernameInputChange} error={usernameError} />
      <PasswordField onPasswordInputChange={handlePasswordInputChange} error={passwordError} />
      <ActionButton btnText={"Sign Up"} onSubmit={handleSubmit} />
      <ActionLink id={"login-link"} btnText={"Log In Instead"} onLinkClick={onLinkClick} />
    </form>
  );
}
