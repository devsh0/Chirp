import SignupForm from "../SignupForm";
import imgChirpLogo from "../../chirp-logo.png";
import FormContainer from "../FormContainer";
import LoginForm from "../LoginForm";
import { useState } from "react";

export default function FrontContent() {
  const [signup, setSignup] = useState(true);

  function handleFormSwitch() {
    setSignup(!signup);
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
      <FormContainer>
        {signup ? (
          <SignupForm onLoginButtonClick={handleFormSwitch} />
        ) : (
          <LoginForm onSignupButtonClick={handleFormSwitch} />
        )}
      </FormContainer>
    </main>
  );
}
