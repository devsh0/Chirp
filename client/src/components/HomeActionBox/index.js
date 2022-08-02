import imgChirpLogo from "../../chirp-logo.png";

export default function HomeActionBox() {
  return (
    <div className={"w-2/3 border-2flex-col text-gray-100"}>
      <div>
        <img className={"w-20 -mx-4"} src={imgChirpLogo} />
      </div>
      <h1 className={"text-4xl font-bold"}>See what's happening in the world right now</h1>
      <h1 className={"text-2xl font-bold my-14 mb-3"}>Join Chirp today.</h1>
      <div className={"flex-col font-bold space-y-4"}>
        <button
          className={
            "bg-blue-twitter hover:bg-blue-twitter-dark transition-colors w-full py-2 rounded-3xl bg-blue-twitter"
          }
        >
          Sign Up
        </button>
        <button
          className={
            "hover:text-gray-100 hover:border-gray-100 transition-colors w-full py-2 rounded-3xl border-blue-twitter border-b-2 border text-blue-twitter"
          }
        >
          Log In
        </button>
      </div>
    </div>
  );
}
