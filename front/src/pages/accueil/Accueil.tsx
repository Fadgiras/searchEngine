// eslint-disable-next-line @typescript-eslint/no-unused-vars
import React, {Dispatch, SetStateAction, useContext, useEffect, useState} from 'react';
import './Accueil.css';
import './../../styles/pages.css';
// import Header from "../../components/Header";
import peakpx from '../../img/peakpx.jpg';
// import { List, TextField } from '@mui/material';
import TextField from "@mui/material/TextField";
import List from "./../../components/List";

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
