import imgLoading from "../../loading.png";

export default function Spinner() {
  return (
    <div
      className={
        "bg-opacity-50 bg-dark-primary absolute h-full w-full text-gray-100 p-3 flex justify-center items-center"
      }
    >
      <div className={"text-center p-4 space-y-2 sm:max-w-sm rounded-md"}>
        <figure>
          <img className={"w-10 animate-spin"} src={imgLoading} alt={"loading icon"} />
        </figure>
      </div>
    </div>
  );
}
