import React, { useState } from 'react';
import "./SearchBar.css"; 

function SearchBar({ onSearch }: { onSearch: (query: string) => Promise<void> }) {

    const [query, setQuery] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    const handleChange = (event: { target: { value: React.SetStateAction<string>; }; }) => {
        setQuery(event.target.value);
    };

    const handleSubmit = async (event: { preventDefault: () => void; }) => {
        event.preventDefault();
        const url = `http://127.0.0.1:8080/search?q=${query}`;
        setIsLoading(true);
        await onSearch(url);
        setIsLoading(false);
    }

    return (
        <div className='search-bar'>
            <form onSubmit={handleSubmit}>
                <input 
                    type="text" 
                    value={query}
                    onChange={handleChange}
                    className='input' />

                <button className="button" type="submit">Recherche</button>
            </form>
            {isLoading && <div className="spinner-backdrop"></div>}
            {isLoading && <div className="loader"></div>}
        </div>
    )
}

export default SearchBar ;