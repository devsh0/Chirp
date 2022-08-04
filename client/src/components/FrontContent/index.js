import SignupForm from "../SignupForm";
import imgChirpLogo from "../../chirp-logo.png";
import FormContainer from "../FormContainer";
import LoginForm from "../LoginForm";
import { useState } from "react";
import PasswordRecoveryForm from "../PasswordRecoveryForm";

export default function FrontContent() {
  const [visibleForm, setVisibleForm] = useState("signup");

  function handleFormSwitch(event) {
    const clickedId = event.target.id;
    if (clickedId.indexOf("signup") >= 0) setVisibleForm("signup");
    if (clickedId.indexOf("login") >= 0) setVisibleForm("login");
    if (clickedId.indexOf("recover") >= 0) setVisibleForm("recovery");
  }

  function getVisibleForm() {
    if (visibleForm === "signup") return <SignupForm onLinkClick={handleFormSwitch} />;
    if (visibleForm === "login") return <LoginForm onLinkClick={handleFormSwitch} />;
    if (visibleForm === "recovery") return <PasswordRecoveryForm onLinkClick={handleFormSwitch} />;
  }

  return (
    <main className={"w-full lg:w-1/2 flex flex-col justify-center py-4 text-center text-gray-100"}>
      <figure>
        <img className={"m-auto sm:w-20 w-12 sm:w-20"} src={imgChirpLogo} alt={"chirp logo"} />
      </figure>
      <header className={"text-2xl mb-5 sm:text-3xl font-bold"}>
        Stay in the loop! <br />
        Join Chirp and find out what everyone's up to.
      </header>
      <FormContainer>{getVisibleForm()}</FormContainer>
    </main>
  );
}
