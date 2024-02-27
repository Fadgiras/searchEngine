// eslint-disable-next-line @typescript-eslint/no-unused-vars
import React, {Dispatch, SetStateAction, useContext, useEffect, useState} from 'react';
import './Accueil.css';
import './../../styles/pages.css';
// import Header from "../../components/Header";
import peakpx from '../../img/peakpx.jpg';
import TextField from "@mui/material/TextField";
import List from "./../../components/List";
import Livres from "./../livres/Livres";
import 'bootstrap/dist/css/bootstrap.min.css'

const Accueil = () => {

  // const [result, setResult] = React.useState("");
  // const [warning, setWarning] = React.useState<string | undefined>();

  const [inputText, setInputText] = useState("");
  let inputHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    //convert input text to lower case
    var lowerCase = e.target.value.toLowerCase();
    setInputText(lowerCase);
  };

  useEffect(() => {
    
    });

  return (
    <>
      <div>
        <Livres />
      </div>
      <div className="main">
        <h1>React Search</h1>
        <div className="search">
          <TextField
            id="outlined-basic"
            onChange={inputHandler}
            variant="outlined"
            fullWidth
            label="Search"
          />
        </div>
        <List input={inputText} />
      </div>
    
      <div className="">
          <div className="text-5xl font-extrabold">
              <span className="bg-clip-text text-transparent bg-gradient-to-r from-primary-500 to-secondary-500">
                  Hello world
              </span>
          </div>
          <div className="">
          <div className="">Accueil colors (3L)</div>
          </div>&&&
          <div className="">
          <div className="">Accueil colors (2L)</div>
          </div>
      </div>
      
      <div className="flex font-sans">
          <div className="flex-none w-56 relative">
              <img src={peakpx} alt="" className="absolute inset-0 w-full h-full object-cover rounded-lg" loading="lazy" />
          </div>
          <form className="flex-auto p-6">
              <div className="flex flex-wrap">
              <h1 className="flex-auto font-medium text-slate-900">
                  Kids Jumpsuit
              </h1>
              <div className="w-full flex-none mt-2 order-1 text-3xl font-bold text-secondary-600">
                  $39.00
              </div>
              <div className="text-sm font-medium text-slate-400">
                  In stock
              </div>
              </div>
              <div className="flex items-baseline mt-4 mb-6 pb-6 border-b border-slate-200">
              <div className="space-x-2 flex text-sm font-bold">
                  <label>
                  <input className="sr-only peer" name="size" type="radio" value="xs" checked />
                  <div className="w-9 h-9 rounded-full flex items-center justify-center text-secondary-400 peer-checked:bg-secondary-600 peer-checked:text-white">
                      XS
                  </div>
                  </label>
                  <label>
                  <input className="sr-only peer" name="size" type="radio" value="s" />
                  <div className="w-9 h-9 rounded-full flex items-center justify-center text-secondary-400 peer-checked:bg-secondary-600 peer-checked:text-white">
                      S
                  </div>
                  </label>
                  <label>
                  <input className="sr-only peer" name="size" type="radio" value="m" />
                  <div className="w-9 h-9 rounded-full flex items-center justify-center text-secondary-400 peer-checked:bg-secondary-600 peer-checked:text-white">
                      M
                  </div>
                  </label>
                  <label>
                  <input className="sr-only peer" name="size" type="radio" value="l" />
                  <div className="w-9 h-9 rounded-full flex items-center justify-center text-secondary-400 peer-checked:bg-secondary-600 peer-checked:text-white">
                      L
                  </div>
                  </label>
                  <label>
                  <input className="sr-only peer" name="size" type="radio" value="xl" />
                  <div className="w-9 h-9 rounded-full flex items-center justify-center text-secondary-400 peer-checked:bg-secondary-600 peer-checked:text-white">
                      XL
                  </div>
                  </label>
              </div>
              </div>
              <div className="flex space-x-4 mb-5 text-sm font-medium">
              <div className="flex-auto flex space-x-4">
                  <button className="h-10 px-6 font-semibold rounded-full bg-secondary-600 text-white" type="submit">
                  Buy now
                  </button>
                  <button className="h-10 px-6 font-semibold rounded-full border border-slate-200 text-slate-900" type="button">
                  Add to bag
                  </button>
              </div>
              <button className="flex-none flex items-center justify-center w-9 h-9 rounded-full text-secondary-600 bg-secondary-50" type="button" aria-label="Like">
                  <svg width="20" height="20" fill="currentColor" aria-hidden="true">
                  <path fill-rule="evenodd" clip-rule="evenodd" d="M3.172 5.172a4 4 0 015.656 0L10 6.343l1.172-1.171a4 4 0 115.656 5.656L10 17.657l-6.828-6.829a4 4 0 010-5.656z" />
                  </svg>
              </button>
              </div>
              <p className="text-sm text-slate-500">
              Free shipping on all continental US orders.
              </p>
          </form>
      </div>
    </>
  )
}


interface CheckboxProps {
  label: string
  value: boolean
  onChange: Dispatch<SetStateAction<boolean>>
  className?: string
}

// interface LinkCheckboxProps {
//   label: string
//   link?: string
//   value: boolean
//   onChange: Dispatch<SetStateAction<boolean>>
//   className?: string
// }

interface NumberInputProps {
  label: string
  value: number | undefined
  image?: string
  onChange: Dispatch<SetStateAction<number | undefined>>
  className?: string
}

export const Checkbox = (props: CheckboxProps) => {
  return (
    <div className={props.className}>
      <label className="checkbox checkbox-text">
        <input className="checkbox-input" type="checkbox" checked={props.value}
               onChange={e => props.onChange(e.target.checked)}/>
        <span>{props.label}</span>
      </label>
    </div>
  );
}

// const SocketCheckbox = (props: LinkCheckboxProps) => {
//   const els = props.link?.split("") ?? props.label.split("");

//   return (
//     <div className={props.className}>
//       <label className="checkbox">
//       </label>
//     </div>
//   );
// }

export const NumberInput = (props: NumberInputProps) => {
  return (
    <label className="numberinput">
      <input className="numberinput-input" placeholder="0" type="number" min="0" max="6" value={props.value}
             onChange={e => {
               const number = Number(e.target.value);
               number === 0 ? props.onChange(undefined) : props.onChange(number)
             }}/>
      {props.label && <span>&nbsp;{props.label}</span>}
    </label>
  );
}

// function imgFromChar(c: string) {
//   switch (c) {
//   }
// }

export default Accueil;
