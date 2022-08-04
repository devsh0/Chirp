import EmailField from "../EmailField";
import ActionButton from "../SignupButton";

export default function PasswordRecoveryForm({ onLinkClick }) {
  return (
    <form className={"flex flex-col space-y-2"}>
      <EmailField>Email</EmailField>
      <ActionButton btnText={"Recover Password"}></ActionButton>
      <p className={"mt-2 text-sm"}>
        <button
          id={"signup-link"}
          className={"transition-colors hover:text-blue-twitter-dark text-blue-twitter"}
          onClick={onLinkClick}
        >
          Sign Up
        </button>
        <span> &#xB7; </span>
        <button
          id={"login-link"}
          className={"transition-colors hover:text-blue-twitter-dark text-blue-twitter"}
          onClick={onLinkClick}
        >
          Log In
        </button>
      </p>
    </form>
  );
}
