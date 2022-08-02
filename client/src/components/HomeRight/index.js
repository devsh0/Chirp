import HomeActionBox from "../HomeActionBox";
import HomeFooter from "../HomeFooter";

export default function HomeRight() {
  return (
    <div className={"sm:w-1/2 bg-gray-800 flex flex-col justify-center items-center"}>
      <HomeActionBox />
      <HomeFooter />
    </div>
  );
}
