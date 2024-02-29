import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import SearchBar from '../../components/searchbar/SearchBar';

interface Book {
  id: number;
  title: string;
  author: string;
  content: string;
  tokens: number;
}

const Lectures = () => {
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

  if (!book) {
    return <div>Loading...</div>;
  }

  return (
    <div className='container m-20'>
      <h1 className="bg-blue-500 text-white rounded">Recherche sur les Livres</h1>
      <SearchBar onSearch={fetchData} />
      {error && <p className="text-red-500">{error}</p>}
      <br></br>
      <br></br>
      <br></br>

      <div className="p-6">
        <div className="mb-4">
          <input
            type="text"
            value={searchId}
            onChange={(e) => setSearchId(e.target.value)}
            className="mr-2"
            placeholder="Enter book ID"
          />
          <button onClick={handleSearch} className="p-2 bg-blue-500 text-white">
            Search
          </button>
        </div>
        <h1 className="text-3xl font-bold mb-4">{book.title}</h1>
        <h2 className="text-xl mb-2">{book.author}</h2>
        <p className="text-base text-justify">{book.content}</p>
      </div>
    </div>
  );
};

export default Lectures;