import EmailField from "./EmailField";
import ActionButton from "./ActionButton";
import ActionLink from "./ActionLink";
import { useContext, useState } from "react";
import AppContext from "../AppContext";
import { DialogTrigger, SpinnerTrigger } from "./FrontPage";
import axios from "axios";
import { ucFirst } from "../utils";

export default function PasswordRecoveryForm({ onLinkClick }) {
  const appContext = useContext(AppContext);
  const dialog = useContext(DialogTrigger);
  const spinner = useContext(SpinnerTrigger);
  const [email, setEmail] = useState("");
  const [emailError, setEmailError] = useState("");

  function handleEmailInputChange(event) {
    setEmail(event.target.value);
  }

  async function sendRecoveryEmail(user) {
    try {
      const response = await axios.post(appContext.api.paths.auth.password_recovery, user);
      const result = response.data;
      if (result.success) {
        const message = `Check your inbox for a password recovery link, we've just sent that to you.
        Follow that link to reset your password.`;
        dialog.trigger("Recovery Email Sent!!", message);
        resetForm();
      }
      return {};
    } catch (e) {
      console.log(e);
      if (e.response.data) return { email: e.response.data.message };
      return {};
    }
  }

  function resetForm() {
    setEmail("");
  }

  function setOrClearError(result) {
    setEmailError(ucFirst(result.email));
  }

  async function handleSubmit() {
    spinner.trigger();
    const result = await sendRecoveryEmail({ email: email });
    setOrClearError(result);
    spinner.dismiss();
  }

  return (
    <form className={"flex flex-col space-y-2"}>
      <EmailField value={email} error={emailError} onEmailInputChange={handleEmailInputChange}>
        Email
      </EmailField>
      <ActionButton btnText={"Recover Password"} onSubmit={handleSubmit}></ActionButton>
      <p>
        <ActionLink id={"signup-link"} btnText={"Sign Up"} onLinkClick={onLinkClick} />
        <span> &#xB7; </span>
        <ActionLink id={"login-link"} btnText={"Log In"} onLinkClick={onLinkClick} />
      </p>
    </form>
  );
}
