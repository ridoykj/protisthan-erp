import { useViewConfig } from '@vaadin/hilla-file-router/runtime.js';
import type { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Signal } from '@vaadin/hilla-react-signals';
import { useEffect, useState } from 'react';

const config: ViewConfig = {
  title: 'Login',
  menu: {
    exclude: true,
  },
};
const vaadin = window.Vaadin as {
  documentTitleSignal: Signal<string>;
};

const LoginView = () => {
  const currentTitle = useViewConfig()?.title ?? '';
  const [name, setName] = useState('');
  useEffect(() => {
    vaadin.documentTitleSignal.value = currentTitle;
  });
  return (
    <div className="font-[sans-serif] text-[#333]">
      <div className="grid lg:grid-cols-2 gap-4 bg-gradient-to-r from-blue-500 to-blue-700 sm:p-8 p-4 h-[320px]">
        <div>
          {/* <a href="javascript:void(0)">
            <img
              src="https://readymadeui.com/readymadeui-white.svg"
              alt="logo"
              className="w-40"
            />
          </a> */}
          <div className="max-w-lg mt-16 px-6 max-lg:hidden">
            <h3 className="text-3xl font-bold text-white">Streamlining Your Business, One Process at a Time</h3>
          </div>
        </div>
        <div className="bg-white my-4 rounded-xl sm:px-6 px-4 py-8 max-w-md w-full h-max shadow-[0_2px_10px_-3px_rgba(6,81,237,0.3)] max-lg:mx-auto">
          <form>
            <div className="mb-10">
              <h3 className="text-3xl font-extrabold">Sign in</h3>
            </div>
            <div className="flex items-center justify-center space-x-4 max-sm:space-y-4 mb-4">
              <img src="icons/user.png" alt="" className="rounded-full w-16 h-16 bg-gray-200 flex-shrink-0" />
            </div>
            <div>
              <span className="text-sm mb-2 block">User name</span>
              <div className="relative flex items-center">
                <input
                  name="username"
                  type="text"
                  required
                  className="w-full text-sm border border-gray-300 px-4 py-3 rounded-md outline-blue-600"
                  placeholder="Enter user name"
                />
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="#bbb"
                  stroke="#bbb"
                  className="w-[18px] h-[18px] absolute right-4"
                  viewBox="0 0 24 24"
                >
                  <circle cx="10" cy="7" r="6" data-original="#000000" />
                  <path
                    d="M14 15H6a5 5 0 0 0-5 5 3 3 0 0 0 3 3h12a3 3 0 0 0 3-3 5 5 0 0 0-5-5zm8-4h-2.59l.3-.29a1 1 0 0 0-1.42-1.42l-2 2a1 1 0 0 0 0 1.42l2 2a1 1 0 0 0 1.42 0 1 1 0 0 0 0-1.42l-.3-.29H22a1 1 0 0 0 0-2z"
                    data-original="#000000"
                  />
                </svg>
              </div>
            </div>
            <div className="mt-6">
              <span className="text-sm mb-2 block">Password</span>
              <div className="relative flex items-center">
                <input
                  name="password"
                  type="password"
                  required
                  className="w-full text-sm border border-gray-300 px-4 py-3 rounded-md outline-blue-600"
                  placeholder="Enter password"
                />
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="#bbb"
                  stroke="#bbb"
                  className="w-[18px] h-[18px] absolute right-4 cursor-pointer"
                  viewBox="0 0 128 128"
                >
                  <path
                    d="M64 104C22.127 104 1.367 67.496.504 65.943a4 4 0 0 1 0-3.887C1.367 60.504 22.127 24 64 24s62.633 36.504 63.496 38.057a4 4 0 0 1 0 3.887C126.633 67.496 105.873 104 64 104zM8.707 63.994C13.465 71.205 32.146 96 64 96c31.955 0 50.553-24.775 55.293-31.994C114.535 56.795 95.854 32 64 32 32.045 32 13.447 56.775 8.707 63.994zM64 88c-13.234 0-24-10.766-24-24s10.766-24 24-24 24 10.766 24 24-10.766 24-24 24zm0-40c-8.822 0-16 7.178-16 16s7.178 16 16 16 16-7.178 16-16-7.178-16-16-16z"
                    data-original="#000000"
                  />
                </svg>
              </div>
            </div>
            <div className="mt-4 text-right">
              <a href="jajvascript:void(0);" className="text-blue-600 text-sm font-semibold hover:underline">
                Forgot your password?
              </a>
            </div>
            <div className="mt-10">
              <button
                type="button"
                className="w-full shadow-xl py-2.5 px-4 text-sm font-semibold rounded text-white bg-blue-600 hover:bg-blue-700 focus:outline-none"
              >
                Log in
              </button>
            </div>
            <p className="text-sm mt-6 text-center">
              Dont have an account
              {/* <a
                href="javascript:void(0);"
                className="text-blue-600 font-semibold hover:underline ml-1 whitespace-nowrap"
              >
                Register here
              </a> */}
            </p>
          </form>
        </div>
      </div>
    </div>
  );
};

export default LoginView;
