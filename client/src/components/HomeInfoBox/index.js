import { BsSearch as SearchIcon } from "react-icons/bs";
import { BsPeople as PeopleIcon } from "react-icons/bs";
import { BiMessageRounded as MessageIcon } from "react-icons/bi";

export default function HomeInfoBox() {
  return (
    <div className={"w-4/5 h-60 text-white font-bold flex-col justify-around"}>
      <h1 className={"h-1/3 flex items-center px-8 space-x-2"}>
        <p>
          <SearchIcon className={"text-2xl"} />
        </p>
        <p>Follow your interests.</p>
      </h1>
      <h1 className={"h-1/3 flex items-center px-8 space-x-2"}>
        <p>
          <PeopleIcon className={"text-2xl"} />
        </p>
        <p>Hear what people are talking about.</p>
      </h1>
      <h1 className={"h-1/3 flex items-center px-8 space-x-2"}>
        <p>
          <MessageIcon className={"text-2xl"} />
        </p>
        <p>Join the conversation.</p>
      </h1>
    </div>
  );
}
