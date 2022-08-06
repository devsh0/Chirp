import ActionButton from "../ActionButton";
import EmailField from "../EmailField";
import UsernameField from "../UsernameField";
import PasswordField from "../PasswordField";
import { useContext, useState } from "react";
import ActionLink from "../ActionLink";
import { DialogTrigger, SpinnerTrigger } from "../FrontPage";
import axios from "axios";
import AppContext from "../../AppContext";
import { ucFirst } from "../../utils";

export default function SignupForm({ onLinkClick }) {
  const appContext = useContext(AppContext);
  const dialog = useContext(DialogTrigger);
  const spinner = useContext(SpinnerTrigger);
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

  async function registerUser(user) {
    try {
      const response = await axios.post(appContext.api.paths.auth.registration, user);
      const result = response.data;
      if (result.success) {
        const message = `Registration successful!
          You will shortly receive an activation email with a link
          to activate your account. Thank you for joining Chirp :)`;
        dialog.trigger("Success!!", message);
        return {};
      }
    } catch (e) {
      console.log(e);
      if (e.response.data) return e.response.data.error;
      return {};
    }
  }

  function validateForm() {
    const emailRegex = /^[_a-zA-Z0-9]+@[-a-z0-9A-Z]+.[a-z]{2,4}$/;
    const usernameRegex = /^[a-zA-Z0-9_-]{3,30}$/;
    const passwordRegex = /.{8,}/;
    const errors = { email: "", username: "", password: "" };

    if (!emailRegex.test(email)) errors.email = "Invalid email!";
    if (!usernameRegex.test(username)) {
      if (username.length < 3 || username.length > 30)
        errors.username = "Must be between 3-30 characters!";
      else errors.username = "May only consist of alphanumeric characters and '-'!";
    }
    if (!passwordRegex.test(password)) errors.password = "Must be at least 8 characters!";
    return errors;
  }

  function setErrorState(error) {
    setEmailError(ucFirst(error.email));
    setUsernameError(ucFirst(error.username));
    setPasswordError(ucFirst(error.password));
  }

  async function handleSubmit() {
    spinner.trigger();
    let error = validateForm();
    setErrorState(error);
    const valid = !error.email && !error.username && !error.password;
    if (valid) {
      const user = {
        email: email,
        username: username,
        password: password,
      };
      error = await registerUser(user);
      setErrorState(error);
    }
    spinner.dismiss();
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
