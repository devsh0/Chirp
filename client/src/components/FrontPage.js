import React from "react";
import FrontContent from "./FrontContent";
import FrontFooter from "./FrontFooter";
import BgSocialMedia from "../social-bg.png";
import FrontDialog from "./FrontDialog";
import { useState } from "react";
import Spinner from "./Spinner";

export const DialogTrigger = React.createContext(null);
export const SpinnerTrigger = React.createContext(null);

export default function FrontPage() {
  const [dialogProps, setDialogProps] = useState({ show: false, title: "", message: "" });
  const [showSpinner, setShowSpinner] = useState(false);

  function triggerDialog(title, message) {
    setDialogProps({ show: true, title: title, message: message });
  }

  function dismissDialog() {
    setDialogProps({ show: false });
  }

  function triggerSpinner() {
    setShowSpinner(true);
  }

  function dismissSpinner() {
    setShowSpinner(false);
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
        <SpinnerTrigger.Provider value={{ trigger: triggerSpinner, dismiss: dismissSpinner }}>
          <FrontContent />
          <FrontFooter />
          {dialogProps.show ? (
            <FrontDialog title={dialogProps.title} message={dialogProps.message} />
          ) : (
            ""
          )}
        </SpinnerTrigger.Provider>
      </DialogTrigger.Provider>
      {showSpinner ? <Spinner /> : ""}
    </div>
  );
}
