import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import ScrollToTopButton from "../../components/ScrollToTopButton/ScrollToTopButton";
import './Lectures.css';

interface Book {
  id: number;
  title: string;
  author: string;
  content: string;
  tokens: number;
}

function Lectures({ onSearch }: { onSearch: (query: string) => Promise<void> }) {

  const { id } = useParams<{ id: string }>();
  const [book, setBook] = useState<Book | null>(null);
  const [searchId, setSearchId] = useState("");
  const [error, setError] = useState<string | null>(null);

  const navigate = useNavigate();

  useEffect(() => {
    const fetchBook = async () => {
      try {
        const response = await fetch(`http://localhost:8080/getBook?id=${id}`);
        if (!response.ok) {
          throw new Error("An error occurred while fetching the book");
        }
        const data = await response.json();
        setBook(data);
      } catch (error: any) {
        setError(error.message);
      }
    };

    fetchBook();
  }, [id]);

  const handleSearch = () => {
    navigate(`/lecture/${searchId}`);
  };

  const fetchData = async (query: string) => {
    setSearchId(query);
    handleSearch();
  };

  const handleSubmit = async (event: { preventDefault: () => void; }) => {
    event.preventDefault();
    const url = `http://127.0.0.1:8080/search?q=${searchId}`;
    await onSearch(url);
  }

  if (!book) {
    return <div>Loading...</div>;
  }

  return (
    <>
      <div className="container m-20">
        <h1 className="bg-blue-500 text-white rounded">Recherche sur les Livres</h1>
          <div className="rounded-lg bg-gray-200 p-1">
            <div className="flex">
              <div className="flex w-10 items-center justify-center rounded-tl-lg rounded-bl-lg border-r border-gray-200 bg-white">
                <svg viewBox="0 0 20 20" aria-hidden="true" className="pointer-events-none absolute w-5 fill-gray-500 transition">
                  <path d="M16.72 17.78a.75.75 0 1 0 1.06-1.06l-1.06 1.06ZM9 14.5A5.5 5.5 0 0 1 3.5 9H2a7 7 0 0 0 7 7v-1.5ZM3.5 9A5.5 5.5 0 0 1 9 3.5V2a7 7 0 0 0-7 7h1.5ZM9 3.5A5.5 5.5 0 0 1 14.5 9H16a7 7 0 0 0-7-7v1.5Zm3.89 10.45 3.83 3.83 1.06-1.06-3.83-3.83-1.06 1.06ZM14.5 9a5.48 5.48 0 0 1-1.61 3.89l1.06 1.06A6.98 6.98 0 0 0 16 9h-1.5Zm-1.61 3.89A5.48 5.48 0 0 1 9 14.5V16a6.98 6.98 0 0 0 4.95-2.05l-1.06-1.06Z"></path>
                </svg>
              </div>
              <input type="text" value={searchId} onChange={(e) => setSearchId(e.target.value)} className="w-full max-w-[160px] bg-white pl-2 text-base font-semibold outline-0" placeholder="Entrer l'ID du livre" id="" />
              <input type="button" onClick={handleSearch} value="Recherche" className="bg-Yellow-500 p-2 rounded-tr-lg rounded-br-lg text-white font-semibold hover:bg-blue-800 transition-colors" />
            </div>
          </div>
        </div>

        <div className="content m-20" style={{ display: 'flex', flexDirection: 'column' }}>
          <div className="text" style={{ flex: 1 }}>
            {book.title}
          </div>
          <form action="#">
            <div className="field">
              <span className="span">
                <svg 
                  className="" 
                  xmlSpace="preserve" 
                  viewBox="0 0 512 512" 
                  y="0" 
                  x="0" 
                  height="20" 
                  width="50" 
                  xmlnsXlink="http://www.w3.org/1999/xlink" 
                  version="1.1" 
                  xmlns="http://www.w3.org/2000/svg"
                >
                  <g>
                    <path 
                      className="" 
                      data-original="#000000" 
                      fill="#595959" 
                      d="M256 0c-74.439 0-135 60.561-135 135s60.561 135 135 135 135-60.561 135-135S330.439 0 256 0zM423.966 358.195C387.006 320.667 338.009 300 286 300h-60c-52.008 0-101.006 20.667-137.966 58.195C51.255 395.539 31 444.833 31 497c0 8.284 6.716 15 15 15h420c8.284 0 15-6.716 15-15 0-52.167-20.255-101.461-57.034-138.805z"
                    >
                    </path>
                  </g>
                </svg>
              </span>
              <label className="label" aria-label="Autheur">Auteur : {book.author}</label>
            </div>
          </form>
        </div>
        <div className="content m-20" style={{ display: 'flex', flexDirection: 'column' }}>
          <div className="field" style={{ flex: 1 }}>
            {book.content}
          </div>
        </div>
      <ScrollToTopButton />
    </>
  );
};

export default Lectures;