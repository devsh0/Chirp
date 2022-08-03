import { useState } from "react";

export default function EmailField() {
  const [email, setEmail] = useState("");

  function handleInputChange(event) {
    setEmail(event.target.value);
  }

  return (
    <div className={"flex flex-col relative"}>
      <label className={"w-20 text-left"} htmlFor={"email"}>
        Email
      </label>
      <input
        className={"h-8 rounded-sm w-full bg-transparent border border-gray-700 text-gray-100 px-2"}
        value={email}
        type={"email"}
        name={"email"}
        id={"email"}
        onInput={handleInputChange}
      />
    </div>
  );
}
