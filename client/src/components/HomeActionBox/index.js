import imgChirpLogo from "../../chirp-logo.png";

export default function HomeActionBox() {
  return (
    <div className={"w-10/12 sm:w-2/3 text-center flex-col text-gray-100"}>
      <div>
        <img className={"w-20 m-auto sm:-mx-4"} src={imgChirpLogo} alt={"chirp logo"} />
      </div>
      <h1 className={"text-4xl sm:text-left font-bold"}>
        See what's happening in the world right now
      </h1>
      <h1 className={"text-2xl sm:text-left font-bold my-14 mb-3"}>Join Chirp today.</h1>
      <div className={"flex-col font-bold space-y-4"}>
        <a
          href={"#"}
          className={
            "text-center inline-block bg-blue-twitter hover:bg-blue-twitter-dark transition-colors w-full py-2 rounded-3xl bg-blue-twitter"
          }
        >
          Sign Up
        </a>
        <a
          href={"#"}
          className={`hover:text-gray-100 hover:border-gray-100 transition-colors w-full py-2 rounded-3xl border-blue-twitter
          inline-block border-b-2 border text-blue-twitter`}
        >
          Log In
        </a>
      </div>
    </div>
  );
}
