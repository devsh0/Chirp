import HomeActionButton from "../HomeActionButton";

export default function SignupForm() {
  return (
    <div className={"bg-zinc-900 m-auto sm:w-1/2 text-sm rounded font-normal p-8"}>
      <form className={"flex flex-col space-y-2"}>
        <label className={"w-20 text-left"} form={"name"}>
          Email
        </label>
        <input className={"h-8 rounded-sm"} value={"a"} type={"text"} name={"email"} id={"name"} />
        <label className={"w-20 text-left"} form={"username"}>
          Username
        </label>
        <input className={"h-8 rounded-sm"} type={"text"} name={"username"} id={"username"} />
        <label className={"w-20 text-left"} form={"password"}>
          Password
        </label>
        <input className={"h-8 rounded-sm"} type={"password"} name={"password"} id={"password"} />
        <HomeActionButton />
      </form>
      <div className={"mt-4"}>
        <a className={"underline text-indigo-400"} href={"https://google.com"}>
          Login
        </a>{" "}
        Instead
      </div>
    </div>
  );
}
