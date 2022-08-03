export default function UsernameField({ onUsernameInputChange }) {
  return (
    <div className={"flex flex-col relative"}>
      <label className={"py-1 text-left"} htmlFor={"username"}>
        Username
      </label>
      <input
        placeholder={"metalbender-yo"}
        className={"h-8 rounded-sm w-full bg-transparent border border-gray-700 text-gray-100 px-2"}
        type={"text"}
        name={"username"}
        id={"username"}
        onInput={onUsernameInputChange}
      />
    </div>
  );
}
