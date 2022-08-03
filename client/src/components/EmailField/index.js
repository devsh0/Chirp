export default function EmailField({ onEmailInputChange }) {
  return (
    <div className={"flex flex-col relative"}>
      <label className={"w-20 text-left"} htmlFor={"email"}>
        Email
      </label>
      <input
        placeholder={"toph@avatar.com"}
        className={"h-8 rounded-sm w-full bg-transparent border border-gray-700 text-gray-100 px-2"}
        type={"email"}
        name={"email"}
        id={"email"}
        onInput={onEmailInputChange}
      />
    </div>
  );
}
