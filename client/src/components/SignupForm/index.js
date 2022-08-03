import ActionButton from "../SignupButton";
import EmailField from "../EmailField";
import UsernameField from "../UsernameField";
import PasswordField from "../PasswordField";
import { useState } from "react";

export default function SignupForm() {
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
      <ActionButton onSubmit={handleSubmit} />
      <p className={"mt-2 text-sm"}>
        or{" "}
        <button
          className={
            "underline transition-colors hover:text-blue-twitter-dark text-blue-twitter font-bold"
          }
        >
          Log In
        </button>
      </p>
    </form>
  );
}
