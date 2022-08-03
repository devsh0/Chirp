export default function SignupButton({ onSignUp }) {
  return (
    <div className={"font-bold"}>
      <a
        href={"#"}
        onClick={onSignUp}
        className={
          "mt-4 text-center inline-block bg-blue-twitter hover:bg-blue-twitter-dark transition-colors w-full py-2 rounded-sm bg-blue-twitter"
        }
      >
        Sign Up
      </a>
    </div>
  );
}
