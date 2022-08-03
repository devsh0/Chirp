import { useState } from "react";
import { BsFillEyeFill as EyeIcon } from "react-icons/bs";
import { BsFillEyeSlashFill as EyeSlashIcon } from "react-icons/bs";

export default function PasswordField({ onPasswordInputChange }) {
  const [visible, setVisible] = useState(false);

  function handleEyeClick(event) {
    event.preventDefault();
    setVisible(!visible);
  }

  return (
    <div className={"flex flex-col relative"}>
      <label className={"w-20 text-left"} htmlFor={"password"}>
        Password
      </label>
      <input
        placeholder={"70PHY0UrU13"}
        className={"h-8 rounded-sm w-full bg-transparent border border-gray-700 text-gray-100 px-2"}
        type={visible ? "text" : "password"}
        name={"password"}
        id={"password"}
        onInput={onPasswordInputChange}
      />
      <button
        className={"p-1 absolute bottom-1 right-1 outline-none"}
        onClick={handleEyeClick}
        title={visible ? "Hide" : "Show"}
      >
        {visible ? <EyeSlashIcon /> : <EyeIcon />}
      </button>
    </div>
  );
}
