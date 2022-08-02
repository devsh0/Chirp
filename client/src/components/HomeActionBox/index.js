import SignupForm from "../SignupForm";
import imgChirpLogo from "../../chirp-logo.png";

export default function HomeActionBox() {
  return (
    <div className={"w-10/12 lg:w-1/2 flex flex-col justify-center py-4 text-center text-gray-100"}>
      <div>
        <img className={"m-auto sm:w-20 w-12 sm:w-20"} src={imgChirpLogo} alt={"chirp logo"} />
      </div>
      <h1 className={"text-2xl mb-5 sm:text-3xl font-bold"}>
        Stay in the loop brah! Join Chirp and find out what everyone's up to.
      </h1>
      <div className={"flex-col font-bold space-y-4"}>
        <SignupForm />
      </div>
    </div>
  );
}
