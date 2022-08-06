import FieldError from "../FieldError";

export default function EmailField({ value, onEmailInputChange, error, children }) {
  return (
    <div className={"flex flex-col"}>
      <label className={"text-left py-1"} htmlFor={"email"}>
        {children}
      </label>
      <input
        placeholder={"toph@avatar.com"}
        className={"h-8 rounded-sm w-full bg-transparent border border-gray-700 text-gray-100 px-2"}
        type={"email"}
        name={"email"}
        id={"email"}
        value={value}
        onInput={onEmailInputChange}
      />
      <FieldError message={error} />
    </div>
  );
}
