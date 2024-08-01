import { Tooltip as ReactTooltip } from 'react-tooltip';

const CostNodeRC = ({
  lable,
  value,
  hints,
  className,
}: {
  lable: string;
  value: string;
  hints?: string;
  className?: string;
}) => {
  return (
    <div className={`${className} w-full rounded-lg place-content-center text-center font-bold p-1`}>
      <h1 className="text-white sm:text-xs md:text-base truncate" data-tooltip-id={`pos_${lable}`}>
        {lable}
      </h1>
      <h1 className="text-white sm:text-sm md:text-base">{value}</h1>
      {hints && <ReactTooltip id={`pos_${lable}`} place="top" content={hints} />}
    </div>
  );
};

export default CostNodeRC;
