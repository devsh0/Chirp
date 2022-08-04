export default function ActionLink({ id, btnText, onLinkClick }) {
  return (
    <button
      id={id}
      className={"transition-colors hover:text-blue-twitter-dark text-blue-twitter"}
      onClick={onLinkClick}
    >
      {btnText}
    </button>
  );
}
