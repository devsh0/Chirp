export default function ActionLink({ id, btnText, onLinkClick }) {
  return (
    <button
      id={id}
      className={
        "text-xs transition-colors hover:text-blue-twitter-dark text-blue-twitter font-bold"
      }
      onClick={onLinkClick}
    >
      {btnText}
    </button>
  );
}
