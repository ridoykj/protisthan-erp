import RippleDivRC from 'Frontend/components/effects/ripple/div/RippleDivRC';

export type ButtonRCProps = {
  title?: string;
  children?: React.ReactNode;
  onClick?: () => void;
  className?: string;
};

const ButtonRC = ({ title, children, onClick, className }: ButtonRCProps) => {
  return (
    <button type="button" title={title ?? 'click'} onClick={onClick}>
      <RippleDivRC
        className={`w-full inline-flex items-center border bg-white font-semibold hover:text-indigo-700 rounded-full hover:shadow-md ${className} ${title === undefined ? 'p-3' : 'py-2 px-4'}`}
      >
        {children}
        {title === undefined ? null : (
          <span className={`${children !== undefined ? 'pl-2 text-nowrap' : ''}`}>{title}</span>
        )}
      </RippleDivRC>
    </button>
  );
};

export default ButtonRC;
