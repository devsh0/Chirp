import FrontContent from "../FrontContent";
import FrontFooter from "../FrontFooter";
import BgSocialMedia from "../../social-bg.png";

export default function FrontPage() {
  const bgImageProps = {
    backgroundImage: `url(${BgSocialMedia})`,
    backgroundSize: 600,
  };
  return (
    <div
      style={bgImageProps}
      className={"bg-dark-primary w-full h-full flex flex-col justify-center items-center"}
    >
      <FrontContent />
      <FrontFooter />
    </div>
  );
}
