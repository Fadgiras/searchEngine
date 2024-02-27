import React, {Dispatch, SetStateAction, useEffect, useState} from 'react';
import './Accueil.css';
import './../../styles/pages.css';
import TextField from "@mui/material/TextField";
import List from "./../../components/List";
import Livres from "./../livres/Livres";
import 'bootstrap/dist/css/bootstrap.min.css'

const Accueil = () => {

  const [inputText, setInputText] = useState("");
  let inputHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    var lowerCase = e.target.value.toLowerCase();
    setInputText(lowerCase);
  };

  useEffect(() => { });

  return (
    <>
      <div>
        <Livres />
      </div>

      <div className="main text-primary-700">
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
    </>
  )
}


interface CheckboxProps {
  label: string
  value: boolean
  onChange: Dispatch<SetStateAction<boolean>>
  className?: string
}

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

export default Accueil;
