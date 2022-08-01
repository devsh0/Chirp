import Left from "../Left";
import Right from "../Right";

export default function App({ username }) {
  return (
    <div className={"bg-no-repeat absolute h-full w-full justify-center flex"}>
      <Left />
      <Right />
    </div>
  );
}
