import FrontContent from "../FrontContent";
import FrontFooter from "../FrontFooter";

export default function FrontPage() {
  return (
    <div className={"bg-dark-primary w-full flex flex-col justify-center items-center"}>
      <FrontContent />
      <FrontFooter />
    </div>
  );
}
