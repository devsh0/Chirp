import EmailField from "./EmailField";
import PasswordField from "./PasswordField";
import { useContext, useState } from "react";
import ActionButton from "./ActionButton";
import ActionLink from "./ActionLink";
import AppContext from "../AppContext";
import axios from "axios";
import { SpinnerTrigger } from "./FrontPage";
import { ucFirst } from "../utils";

export default function LoginForm({ onLinkClick }) {
  const appContext = useContext(AppContext);
  const spinner = useContext(SpinnerTrigger);
  const [email, setEmail] = useState("");
  const [emailError, setEmailError] = useState("");
  const [password, setPassword] = useState("");

  function handleEmailInputChange(event) {
    setEmail(event.target.value);
  }

  function handlePasswordInputChange(event) {
    setPassword(event.target.value);
  }

  async function loginUser(user) {
    try {
      const response = await axios.post(appContext.api.paths.auth.login, user);
      const result = response.data;
      if (result.success) {
        localStorage.setItem("user", result.user);
        localStorage.setItem("token", result.token);
        // TODO: redirect to user page or something.
      }
      return {};
    } catch (e) {
      console.log(e);
      if (e.response.data) return { email: e.response.data.message };
      return {};
    }
  }

  function setOrClearError(result) {
    setEmailError(ucFirst(result.email));
  }

  async function handleSubmit() {
    // No validation necessary.
    spinner.trigger();
    const result = await loginUser({ user: email, password: password });
    setOrClearError(result);
    spinner.dismiss();
  }

  return (
    <form className={"flex flex-col space-y-2"}>
      <EmailField error={emailError} value={email} onEmailInputChange={handleEmailInputChange}>
        Email or Username
      </EmailField>
      <PasswordField
        error={""}
        value={password}
        onPasswordInputChange={handlePasswordInputChange}
      />
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
