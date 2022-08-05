import { BsCheckCircle } from "react-icons/bs";
import ActionButton from "../ActionButton";
import { useContext } from "react";
import { DialogTrigger } from "../FrontPage";

export default function FrontDialog() {
  const dialogTrigger = useContext(DialogTrigger);

  let classList =
    "absolute h-full w-full text-gray-100 p-3 backdrop-blur backdrop-blur-2xl flex justify-center items-center ";

  return (
    <div className={classList}>
      <div className={"bg-dark-secondary text-center p-4 space-y-2 sm:max-w-sm rounded-md"}>
        <p className={"flex justify-center"}>
          <span className={"text-6xl text-green-500"}>
            <BsCheckCircle />
          </span>
        </p>
        <h1 className={"text-2xl font-bold"}>Success!!</h1>
        <p className={""}>
          This is a message. You are reading a message. This can go longer and longer and longer.
        </p>
        <ActionButton btnText={"Dismiss"} onSubmit={() => dialogTrigger.dismiss()} />
      </div>
    </div>
  );
}
