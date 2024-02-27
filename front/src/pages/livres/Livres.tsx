// eslint-disable-next-line @typescript-eslint/no-unused-vars
import React, {Dispatch, SetStateAction, useContext, useEffect, useState} from 'react';
import './Livres.css';
import './../../styles/pages.css';
// import Header from "../../components/Header";
import BookCard from '../../components/bookcard/BookCard';


const Livres = () => {

  const [searchType, setSearchType] = useState<'id' | 'author' | 'text'>('id');
  const [searchValue, setSearchValue] = useState('');

  const handleSearch = () => {
    if (searchType === 'id') {
      // Recherche par id
    } else if (searchType === 'author') {
      // Recherche par auteur
    } else {
      // Recherche par texte
    }
  };

/*<div className='header'>
            <h1 className='title'>Mon Moteur de Recherche</h1>
            <SearchBar onSearch={handleSearch} />
        </div>
*/
  return (
    <>
        <div className='container'>
          
          <h1 className="bg-blue-500 text-white rounded">Recherche sur les Livres</h1>
          
          <input type="text" value={searchValue} onChange={(e) => setSearchValue(e.target.value)} className="border p-2 rounded flex-grow min-w-min max-w-screen-md" />
          <button onClick={handleSearch} className="text-white p-2 rounded">Rechercher</button>
          
          <br></br>
          <br></br>
          <br></br>

          <div className='grid grid-cols-4 gap-4 grid-flow-row auto-rows-[minmax(0,_2fr)]'>
              <BookCard
                  title="Titre du Livre 1"
                  author="Auteur 1"
                  coverImage="https://d.wattpad.com/story_parts/836439491/images/15f14bc9f0e4e3b2605738069606.png"
              />

              <BookCard
                  title="Titre du Livre 2"
                  author="Auteur 2"
                  coverImage="https://d.wattpad.com/story_parts/836439491/images/15f14bc9f0e4e3b2605738069606.png"
              /> 

              <BookCard
                  title="Titre du Livre 3"
                  author="Auteur 3"
                  coverImage="https://d.wattpad.com/story_parts/836439491/images/15f14bc9f0e4e3b2605738069606.png"
              />
              
              <BookCard
                  title="Titre du Livre 1"
                  author="Auteur 1"
                  coverImage="https://d.wattpad.com/story_parts/836439491/images/15f14bc9f0e4e3b2605738069606.png"
              />

              <BookCard
                  title="Titre du Livre 2"
                  author="Auteur 2"
                  coverImage="https://d.wattpad.com/story_parts/836439491/images/15f14bc9f0e4e3b2605738069606.png"
              />

              <BookCard
                  title="Titre du Livre 3"
                  author="Auteur 3"
                  coverImage="https://d.wattpad.com/story_parts/836439491/images/15f14bc9f0e4e3b2605738069606.png"
              />

              <BookCard
                  title="Titre du Livre 1"
                  author="Auteur 1"
                  coverImage="https://d.wattpad.com/story_parts/836439491/images/15f14bc9f0e4e3b2605738069606.png"
              />

              <BookCard
                  title="Titre du Livre 2"
                  author="Auteur 2"
                  coverImage="https://d.wattpad.com/story_parts/836439491/images/15f14bc9f0e4e3b2605738069606.png"
              />
              
              <BookCard
                  title="Titre du Livre 3"
                  author="Auteur 3"
                  coverImage="https://example.com/book3-cover.jpg"
              />  
          </div>
        </div>
    </>
  )
}

export default Livres;
