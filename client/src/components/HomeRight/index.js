import HomeActionBox from "../HomeActionBox";
import HomeFooter from "../HomeFooter";

export default function HomeRight() {
  return (
    <div className={"bg-zinc-1000 w-full flex flex-col justify-center items-center"}>
      <HomeActionBox />
      <HomeFooter />
    </div>
  );
}
