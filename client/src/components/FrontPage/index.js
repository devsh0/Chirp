import React from "react";
import FrontContent from "../FrontContent";
import FrontFooter from "../FrontFooter";
import BgSocialMedia from "../../social-bg.png";
import FrontDialog from "../FrontDialog";
import { useState } from "react";

export const DialogTrigger = React.createContext(null);

export default function FrontPage() {
  const [showDialog, setShowDialog] = useState(false);

  function triggerDialog(title, message) {
    setShowDialog(true);
  }

  function dismissDialog() {
    setShowDialog(false);
  }

  const bgImageProps = {
    backgroundImage: `url(${BgSocialMedia})`,
    backgroundSize: 600,
  };

  return (
    <div
      style={bgImageProps}
      className={"bg-dark-primary w-full h-full flex flex-col justify-center items-center relative"}
    >
      <DialogTrigger.Provider value={{ trigger: triggerDialog, dismiss: dismissDialog }}>
        <FrontContent />
        <FrontFooter />
        {showDialog ? <FrontDialog /> : ""}
      </DialogTrigger.Provider>
    </div>
  );
}
