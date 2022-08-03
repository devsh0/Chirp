import SignupButton from "../SignupButton";
import EmailField from "../EmailField";
import UsernameField from "../UsernameField";
import PasswordField from "../PasswordField";

export default function SignupForm() {
  return (
    <div className={"bg-dark-secondary m-auto sm:w-1/2 text-sm rounded font-normal p-8"}>
      <form className={"flex flex-col space-y-2"}>
        <EmailField />
        <UsernameField />
        <PasswordField />
        <SignupButton />
      </form>
      <p className={"mt-2 text-sm"}>
        or <button className={"underline text-blue-twitter font-bold"}>Log In</button>
      </p>
    </div>
  );
}
