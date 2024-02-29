// eslint-disable-next-line @typescript-eslint/no-unused-vars
import React, {Dispatch, SetStateAction, useContext, useEffect, useState} from 'react';
import { ChevronLeftIcon, ChevronRightIcon } from '@heroicons/react/20/solid'
import './Livres.css';
import './../../styles/pages.css';
// import Header from "../../components/Header";
import BookCard from '../../components/bookcard/BookCard';
import SearchBar from '../../components/searchbar/SearchBar';
import { useNavigate } from "react-router-dom";
import ScrollToTopButton from "../../components/ScrollToTopButton/ScrollToTopButton";


interface Book {
  id: number;
  title: string;
  author: string;
  coverImage: string;
}

const Livres = () => {

  const [books, setBooks, ] = useState<Book[]>([]);
  const [suggestedBooks, setSuggestedBooks] = useState<Book[]>([]);

  const [currentPage, setCurrentPage] = useState(1);
  const [error, setError] = useState(null);
  const booksPerPage = 50; // Nombre de livres par page
  const navigate = useNavigate();

  const fetchData = async (query: string) => {
    try {
      const response = await fetch(`http://localhost:8080/search?q=${query}`);
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`);
      }
      console.log(response)
  
      const data = await response.json();
      if (Array.isArray(data.books)) {
        setBooks(data.books);
      } else {
        setBooks([]);
      }
  
      if (Array.isArray(data.suggestedBooks)) {
        setSuggestedBooks(data.suggestedBooks);
      } else {
        setSuggestedBooks([]);
      }
  
    } catch (error: any) {
      setError(error.message);
      console.error('Une erreur est survenue lors de la récupération des données.', error);
    }
  };


  // Calculer l'index de début et de fin des livres à afficher
  const startIndex = (currentPage - 1) * booksPerPage;
  const endIndex = Math.min(startIndex + booksPerPage, books.length);

  const handlePageChange = (pageNumber: number) => {
    setCurrentPage(pageNumber);
  };

  // Calculer le nombre total de pages
  const totalPages = Math.ceil(books.length / booksPerPage);

  // Calculer les pages à afficher
  const pagesToShow = 5; // Nombre de pages à afficher à la fois
  const startPage = Math.max(1, currentPage - Math.floor(pagesToShow / 2));
  const endPage = Math.min(totalPages, startPage + pagesToShow - 1);

  return (
    <>
      <div className='container m-20'>
        <h1 className="bg-blue-500 text-white rounded">Recherche sur les Livres</h1>
        <SearchBar onSearch={fetchData} />
        {error && <p className="text-red-500">{error}</p>}
        <br></br>
        <br></br>
        <br></br>

        <div className='grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-4'>
          {
            books.slice(startIndex, endIndex).map((book) => (
              <BookCard
                key={book.id}
                id={book.id}
                title={book.title}
                author={book.author}
                coverImage={book.coverImage}
                onRead={() => {
                  navigate(`/lecture/${book.id}`);
                }}
              />
            ))
          }
        </div>

        <br></br>
        <br></br>

        <h1 className="bg-blue-500 text-white rounded">Livres suggérés</h1>
        <br></br>
        <div className='grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5 gap-4'>
          {suggestedBooks.length > 0 ? (
            suggestedBooks.map((book, index) => (
              <BookCard
                key={book.id}
                id={book.id}
                title={book.title}
                author={book.author}
                coverImage={book.coverImage}
                onRead={() => {
                  navigate(`/lecture/${book.id}`);
                }}
              />
            ))
            ) : (
              <p className="text-white">Aucun livre suggéré n'a été trouvé.</p>
            )
          }
        </div>

        <ScrollToTopButton />

        <br></br>

        {/* Pagination */}
        <div className="fixed bottom-0 left-0 right-0 flex flex-col items-center justify-between sm:flex-row border-t border-Slate-500 bg-Slate-200 px-4 py-3 sm:px-6">
          <div className="flex flex-col items-center sm:flex-row sm:items-center sm:justify-between w-full">
            <div className="mb-2 sm:mb-0">
              <p className="text-sm text-Slate-700">
                Affichage de <span className="font-medium">{(currentPage - 1) * booksPerPage + 1}</span> à <span className="font-medium">{Math.min(currentPage * booksPerPage, books.length)}</span> sur{' '}
                <span className="font-medium">{books.length}</span> résultats
              </p>
            </div>
            <div>
              <nav className="isolate inline-flex -space-x-px rounded-md shadow-sm" aria-label="Pagination">
                <button
                  onClick={() => handlePageChange(currentPage - 1)}
                  className="relative inline-flex items-center rounded-l-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0"
                  disabled={currentPage === 1}
                >
                  <span className="sr-only">Précédent</span>
                  <ChevronLeftIcon className="h-5 w-5" aria-hidden="true" />
                </button>
                {[...Array(endPage - startPage + 1)].map((_, i) => (
                  <button 
                    key={i} 
                    onClick={() => handlePageChange(startPage + i)}
                    className={`relative inline-flex items-center px-2 py-2 text-sm font-semibold ${currentPage === startPage + i ? 'bg-indigo-600 text-Rose-600' : 'text-gray-900 ring-1 ring-inset ring-gray-300 hover:bg-gray-50'} focus:z-20 focus:outline-offset-0`}
                  >
                    {startPage + i}
                  </button>
                ))}
                <button
                  onClick={() => handlePageChange(currentPage + 1)}
                  className="relative inline-flex items-center rounded-r-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0"
                  disabled={currentPage === totalPages}
                >
                  <span className="sr-only">Suivant</span>
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
