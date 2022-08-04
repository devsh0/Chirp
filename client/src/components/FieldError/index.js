export default function FieldError({ message, hidden }) {
  const classes = `${hidden ? "hidden " : "text-xs text-left pt-1"}`;
  return <p className={classes}>{message}</p>;
}
