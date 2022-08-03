import { useState } from "react";

export default function UsernameField() {
  const [username, setUsername] = useState("");

  function handleInputChange(event) {
    setUsername(event.target.value);
  }

  return (
    <div className={"flex flex-col relative"}>
      <label className={"w-20 text-left"} htmlFor={"username"}>
        Username
      </label>
      <input
        className={"h-8 rounded-sm w-full bg-transparent border border-gray-700 text-gray-100 px-2"}
        value={username}
        type={"text"}
        name={"username"}
        id={"username"}
        onInput={handleInputChange}
      />
    </div>
  );
}
