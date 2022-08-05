import ActionButton from "../ActionButton";
import EmailField from "../EmailField";
import UsernameField from "../UsernameField";
import PasswordField from "../PasswordField";
import { useContext, useState } from "react";
import ActionLink from "../ActionLink";
import { DialogTrigger } from "../FrontPage";
import axios from "axios";
import AppContext from "../../AppContext";

export default function SignupForm({ onLinkClick }) {
  const appContext = useContext(AppContext);
  const dialog = useContext(DialogTrigger);
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
      console.log(response);
      const result = response.data;
      if (result.success) {
        const message = `Registration successful!
          You will shortly receive an activation email with a link
          to activate your account. Thank you for joining Chirp :)`;
        dialog.trigger("Success!!", message);
      }
    } catch (e) {
      const error = e.response.data.error;
      if (error.email) setEmailError(error.email);
      if (error.username) setUsernameError(error.username);
      if (error.password) setPasswordError(error.password);
    }
  }

  function validateForm() {
    const emailRegex = /^[_a-zA-Z0-9]+@[-a-z0-9A-Z]+.[a-z]{2,4}$/;
    const usernameRegex = /^[a-zA-Z0-9_-]{3,30}$/;
    const passwordRegex = /.{8,}/;
    let success = true;

    if (!emailRegex.test(email)) {
      setEmailError("Invalid email!");
      success = false;
    } else setEmailError("");

    if (!usernameRegex.test(username)) {
      if (username.length < 3 || username.length > 30)
        setUsernameError("Must be between 3-30 characters!");
      else setUsernameError("May only consist of alphanumeric characters and '-'!");
      success = false;
    } else setUsernameError("");

    if (!passwordRegex.test(password)) {
      setPasswordError("Must be at least 8 characters!");
      success = false;
    } else setPasswordError("");

    return success;
  }

  function handleSubmit() {
    if (validateForm()) {
      const user = {
        email: email,
        username: username,
        password: password,
      };
      registerUser(user);
    }
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
