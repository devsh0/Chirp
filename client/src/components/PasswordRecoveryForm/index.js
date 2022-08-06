import EmailField from "../EmailField";
import ActionButton from "../ActionButton";
import ActionLink from "../ActionLink";
import { useState } from "react";

export default function PasswordRecoveryForm({ onLinkClick }) {
  const [email, setEmail] = useState("");
  return (
    <form className={"flex flex-col space-y-2"}>
      <EmailField value={email} error={""}>
        Email
      </EmailField>
      <ActionButton btnText={"Recover Password"}></ActionButton>
      <p>
        <ActionLink id={"signup-link"} btnText={"Sign Up"} onLinkClick={onLinkClick} />
        <span> &#xB7; </span>
        <ActionLink id={"login-link"} btnText={"Log In"} onLinkClick={onLinkClick} />
      </p>
    </form>
  );
}
