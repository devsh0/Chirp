import SignupForm from "../SignupForm";
import imgChirpLogo from "../../chirp-logo.png";

export default function FrontContent() {
  return (
    <main
      className={"w-10/12 lg:w-1/2 flex flex-col justify-center py-4 text-center text-gray-100"}
    >
      <figure>
        <img className={"m-auto sm:w-20 w-12 sm:w-20"} src={imgChirpLogo} alt={"chirp logo"} />
      </figure>
      <header className={"text-2xl mb-5 sm:text-3xl font-bold"}>
        Stay in the loop! Join Chirp and find out what everyone's up to.
      </header>
      <SignupForm />
    </main>
  );
}
