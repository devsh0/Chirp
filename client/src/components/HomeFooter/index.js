export default function HomeFooter() {
  return (
    <div className={"w-full h-10 px-8 flex space-x-2 justify-center items-center text-gray-100"}>
      <a className={"hover:underline"} href={"#"}>
        About
      </a>
      <span>|</span>
      <a className={"hover:underline"} href={"#"}>
        Demo{" "}
      </a>
      <span>|</span>
      <a className={"hover:underline"} href={"#"}>
        Hire
      </a>
    </div>
  );
}
