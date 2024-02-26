// eslint-disable-next-line @typescript-eslint/no-unused-vars
import React, {Dispatch, SetStateAction,useState, useContext, useEffect} from 'react';
import './Livres.css';
import './../../styles/pages.css';
import BookCard from '../../components/BookCard';

// import Header from "../../components/Header";

function Livres(){

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
        <div className='container'>
          
          <h1 className="bg-blue-500 text-white rounded">Recherche sur les Livres</h1>
          
          <input type="text" value={searchValue} onChange={(e) => setSearchValue(e.target.value)} className="border p-2 rounded flex-grow min-w-min max-w-screen-md" />
          <button onClick={handleSearch} className="text-white p-2 rounded">Rechercher</button>
          
          <br></br>
          <br></br>
          <br></br>

          <div className='grid grid-cols-none'>
              <BookCard
                  title="Titre du Livre 1"
                  author="Auteur 1"
                  /*coverImage="https://example.com/book1-cover.jpg"*/
              />

              <BookCard
                  title="Titre du Livre 2"
                  author="Auteur 2"
                  /*coverImage="https://example.com/book2-cover.jpg"*/
              />  
              <BookCard
                  title="Titre du Livre 3"
                  author="Auteur 3"
                  /*coverImage="https://example.com/book3-cover.jpg"*/
              />  
          </div>
        </div>
  );
}
export default Livres;

