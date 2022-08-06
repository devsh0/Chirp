import { useState } from "react";
import { BsFillEyeFill as EyeIcon } from "react-icons/bs";
import { BsFillEyeSlashFill as EyeSlashIcon } from "react-icons/bs";
import FieldError from "../FieldError";

export default function PasswordField({ value, onPasswordInputChange, error }) {
  const [visible, setVisible] = useState(false);

  function handleEyeClick(event) {
    event.preventDefault();
    setVisible(!visible);
  }

  return (
    <div className={"flex flex-col"}>
      <label className={"py-1 text-left"} htmlFor={"password"}>
        Password
      </label>
      <div className={"relative"}>
        <input
          placeholder={"70PHY0UrU13"}
          className={
            "h-8 rounded-sm w-full bg-transparent border border-gray-700 text-gray-100 px-2"
          }
          type={visible ? "text" : "password"}
          name={"password"}
          id={"password"}
          value={value}
          onInput={onPasswordInputChange}
        />
        <button
          className={"focus:border p-1 absolute bottom-1 right-1 outline-none"}
          onClick={handleEyeClick}
          title={visible ? "Hide" : "Show"}
        >
          {visible ? <EyeSlashIcon /> : <EyeIcon />}
        </button>
      </div>
      <FieldError message={error} />
    </div>
  );
}
