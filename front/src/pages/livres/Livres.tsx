// eslint-disable-next-line @typescript-eslint/no-unused-vars
import React, {Dispatch, SetStateAction, useContext, useEffect, useState} from 'react';
import { ChevronLeftIcon, ChevronRightIcon } from '@heroicons/react/20/solid'
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

  const [currentPage, setCurrentPage] = useState(1);
  const [error, setError] = useState(null);
  const booksPerPage = 50; // Nombre de livres par page
 
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

  // Calculer les livres à afficher pour la page actuelle
  const startIndex = (currentPage - 1) * booksPerPage;
  const endIndex = startIndex + booksPerPage;
  const currentBooks = books.slice(startIndex, endIndex);

  // Calculer le nombre total de pages
  const totalPages = Math.ceil(books.length / booksPerPage);

  const handlePageChange = (pageNumber: number) => {
    setCurrentPage(pageNumber);
  };

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
          {currentBooks.length > 0 ? (
            currentBooks.map((book, index) => (
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

        {/* Pagination */}
        <div className="flex items-center justify-between border-t border-Slate-500 bg-Slate-300 px-4 py-3 sm:px-6">
          <div className="hidden sm:flex sm:flex-1 sm:items-center sm:justify-between">
            <div>
              <p className="text-sm text-Slate-700">
                Showing <span className="font-medium">{(currentPage - 1) * booksPerPage + 1}</span> to <span className="font-medium">{Math.min(currentPage * booksPerPage, books.length)}</span> of{' '}
                <span className="font-medium">{books.length}</span> results
              </p>
            </div>
            <div>
              <nav className="isolate inline-flex -space-x-px rounded-md shadow-sm" aria-label="Pagination">
                <button
                  onClick={() => handlePageChange(currentPage - 1)}
                  className="relative inline-flex items-center rounded-l-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0"
                  disabled={currentPage === 1}
                >
                  <span className="sr-only">Previous</span>
                  <ChevronLeftIcon className="h-5 w-5" aria-hidden="true" />
                </button>
                {[...Array(totalPages)].map((_, i) => (
                  <button 
                    key={i} 
                    onClick={() => handlePageChange(i + 1)}
                    className={`relative inline-flex items-center px-4 py-2 text-sm font-semibold ${currentPage === i+1 ? 'bg-indigo-600 text-Rose-600' : 'text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50'} focus:z-20 focus:outline-offset-0`}
                  >
                    {i + 1}
                  </button>
                ))}
                <button
                  onClick={() => handlePageChange(currentPage + 1)}
                  className="relative inline-flex items-center rounded-r-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0"
                  disabled={currentPage === totalPages}
                >
                  <span className="sr-only">Next</span>
                  <ChevronRightIcon className="h-5 w-5" aria-hidden="true" />
                </button>
              </nav>
            </div>
          </div>
        </div>
      </div>
    </>
  )
}

export default Livres;
