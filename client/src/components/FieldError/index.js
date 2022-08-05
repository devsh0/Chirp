export default function FieldError({ message }) {
  const hidden = !message || message.length === 0;
  const classes = `${hidden ? "hidden " : "text-xs text-left pt-1 text-red-500"}`;
  return <p className={classes}>{message}</p>;
}
