import React, {Dispatch, SetStateAction, useEffect, useState} from 'react';
import './Accueil.css';
import './../../styles/pages.css';
import TextField from "@mui/material/TextField";
import Livres from "./../livres/Livres";
import 'bootstrap/dist/css/bootstrap.min.css'
import image1 from './../../img/woman-6784555_1920.jpg';
import image2 from './../../img/tu-delft-1723434_1920.jpg';

const Accueil = () => {

  const [inputText, setInputText] = useState("");
  let inputHandler = (e: React.ChangeEvent<HTMLInputElement>) => {
    var lowerCase = e.target.value.toLowerCase();
    setInputText(lowerCase);
  };

  useEffect(() => { });

  return (
    <>
      <section className="relative flex items-center justify-center h-[80vh] w-screen p-0 block">
        <div className="relative z-1 text-center p-4 sm:p-6 lg:p-8 flex flex-col items-center justify-center">
          <span className="block text-white text-lg md:text-xl lg:text-2xl animate-slide-up">Bienvenue à la bibliothèque,</span>
          <h2 className="mt-4 text-white text-2xl md:text-3xl lg:text-4xl animate-slide-up">Recherchez et trouvez des livres à lire en ligne gratuitement</h2>
          <span className="block mt-2 text-white text-lg md:text-xl lg:text-2xl animate-slide-up">Commencez votre voyage de lecture maintenant!</span>
        </div>
        <div className="absolute inset-0">
          <img src={image1} alt="Background" className="w-full h-full object-cover" />
        </div>
      </section>
      <section className="bg-white dark:bg-gray-900">
        <div className="gap-16 items-center py-8 px-4 mx-auto max-w-screen-xl lg:grid lg:grid-cols-2 lg:py-16 lg:px-6">
          <div className="font-light text-gray-500 sm:text-lg dark:text-gray-400">
              <h2 className="mb-4 text-4xl tracking-tight font-extrabold text-gray-900 dark:text-white">Bienvenue à notre bibliothèque en ligne</h2>
              <p className="mb-4">Nous sommes une plateforme dédiée à la promotion de la lecture. Simple mais efficace, notre bibliothèque en ligne offre une large gamme de livres pour tous les goûts.</p>
              <p>Commencez à explorer notre collection dès maintenant et découvrez le plaisir de la lecture en ligne.</p>
          </div>
          <div className="grid grid-cols-2 gap-4 mt-8">
              <img className="w-full rounded-lg" src="https://flowbite.s3.amazonaws.com/blocks/marketing-ui/content/office-long-2.png" alt="office content 1" />
              <img className="mt-4 w-full lg:mt-10 rounded-lg" src="https://flowbite.s3.amazonaws.com/blocks/marketing-ui/content/office-long-1.png" alt="office content 2" />
          </div>
        </div>
      </section>
      <section className="relative flex items-center justify-center h-[80vh] w-screen m-0 p-0 block">
        <div className="relative z-1 text-center p-4 sm:p-6 lg:p-8 flex flex-col items-center justify-center">
          <span className="block text-white text-lg md:text-xl lg:text-2xl animate-slide-up">Découvrez notre collection,</span>
          <h2 className="mt-4 text-white text-2xl md:text-3xl lg:text-4xl animate-slide-up">Des milliers de livres à votre portée</h2>
          <span className="block mt-2 text-white text-lg md:text-xl lg:text-2xl animate-slide-up">Commencez à explorer maintenant!</span>
        </div>
        <div className="absolute inset-0">
          <img src={image2} alt="Background" className="w-full h-full object-cover" />
        </div>
      </section>
    </>
  )
}

export default Accueil;
