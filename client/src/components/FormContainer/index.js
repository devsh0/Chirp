export default function FormContainer(props) {
  return (
    <div
      className={
        "transition-all border w-10/12 bg-dark-secondary m-auto sm:w-1/2 text-sm rounded font-normal p-8"
      }
    >
      {props.children}
    </div>
  );
}
