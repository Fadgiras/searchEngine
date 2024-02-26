import React, {useState} from 'react';
import './Recherches.css';
import './../../styles/pages.css';
// import Header from "../../components/Header";

const Recherches: React.FC = () => {
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

  return (
    <div className="flex items-center space-x-2">
      <select value={searchType} onChange={(e) => setSearchType(e.target.value as 'id' | 'author' | 'text')} className="border p-2 rounded">
        <option value="id">ID</option>
        <option value="author">Auteur</option>
        <option value="text">Texte</option>
      </select>
      <input type="text" value={searchValue} onChange={(e) => setSearchValue(e.target.value)} className="border p-2 rounded flex-grow" />
      <button onClick={handleSearch} className="bg-blue-500 text-white p-2 rounded">Rechercher</button>
    </div>
  );
};

export default Recherches;
