import HomeLeft from "../HomeLeft";
import HomeRight from "../HomeRight";

export default function App() {
  return (
    <div className={"bg-no-repeat absolute h-full w-full justify-center flex"}>
      <HomeLeft />
      <HomeRight />
    </div>
  );
}
