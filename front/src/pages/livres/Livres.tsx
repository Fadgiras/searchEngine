// eslint-disable-next-line @typescript-eslint/no-unused-vars
import React, {Dispatch, SetStateAction, useContext, useEffect, useState} from 'react';
import './Livres.css';
import './../../styles/pages.css';
// import Header from "../../components/Header";
import BookCard from '../../components/bookcard/BookCard';
import SearchBar from '../../components/searchbar/SearchBar';


interface Book {
  id: number;
  title: string;
  author: string;
  coverImage: string;
}

const Livres = () => {

  const [books, setBooks] = useState<Book[]>([]);
  const [searchType, setSearchType] = useState<'id' | 'author' | 'text'>('id');
  const [searchValue, setSearchValue] = useState('');
  const [error, setError] = useState(null);
 
  const fetchData = async (url: string) => {
    try {
      const response = await fetch(url);

      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }

      const data = await response.json();
      setBooks(data);
    } catch (error: any) {
      setError(error.message);
      console.error('Une erreur est survenue lors de la récupération des données.', error);
    }
  };

  useEffect(() => {
    // Initial fetch removed to only fetch data based on search
  }, []);

  

  return (
    <>
      <div className='container'>
        <h1 className="bg-blue-500 text-white rounded">Recherche sur les Livres</h1>
        <SearchBar onSearch={fetchData} />
        {error && <p className="text-red-500">{error}</p>}
        <br></br>
        <br></br>
        <br></br>

        <div className='grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-4'>
          {books.length > 0 ? (
            books.map((book, index) => (
              <BookCard
                key={book.id}
                id={book.id}
                title={book.title}
                author={book.author}
                coverImage={book.coverImage}
                onRead={() => {
                  // Ajoutez le code à exécuter lorsque l'utilisateur clique sur "Lire"
                }}
              />
            ))
            ) : (
              <p className="text-white">Aucun livre correspondant à la recherche n'a été trouvé.</p>
            )
          }
        </div>
      </div>
    </>
  )
}

export default Livres;
