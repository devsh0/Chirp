import HomeActionButton from "../HomeActionButton";
import EmailField from "../EmailField";
import UsernameField from "../UsernameField";
import PasswordField from "../PasswordField";

export default function SignupForm() {
  return (
    <div className={"bg-zinc-900 m-auto sm:w-1/2 text-sm rounded font-normal p-8"}>
      <form className={"flex flex-col space-y-2"}>
        <EmailField />
        <UsernameField />
        <PasswordField />
        <HomeActionButton />
      </form>
      <div className={"mt-4"}>
        <a className={"underline text-indigo-400"} href={"#"}>
          Login
        </a>{" "}
        Instead
      </div>
    </div>
  );
}
