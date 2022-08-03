import SignupButton from "../SignupButton";
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

  function handleSignup() {
    // TODO.
  }

  return (
    <div
      className={"border w-10/12 bg-dark-secondary m-auto sm:w-1/2 text-sm rounded font-normal p-8"}
    >
      <form className={"flex flex-col space-y-2"}>
        <EmailField onEmailInputChange={handleEmailInputChange} />
        <UsernameField onUsernameInputChange={handleUsernameInputChange} />
        <PasswordField onPasswordInputChange={handlePasswordInputChange} />
        <SignupButton onSignUp={handleSignup} />
      </form>
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
    </div>
  );
}
