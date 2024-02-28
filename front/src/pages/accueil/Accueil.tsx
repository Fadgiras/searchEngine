import React, {Dispatch, SetStateAction, useEffect, useState} from 'react';
import './Accueil.css';
import './../../styles/pages.css';
import TextField from "@mui/material/TextField";
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
    </>
  )
}

export default Accueil;
