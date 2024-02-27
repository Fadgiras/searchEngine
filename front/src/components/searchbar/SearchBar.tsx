import React, { useState } from 'react';
import "./SearchBar.css"; 

export default SearchBar ;

function SearchBar({ onSearch }: { onSearch: (query: string) => void }) {

    const [query, setQuery] = useState('');

    const handleChange = (event: { target: { value: React.SetStateAction<string>; }; }) => {
        setQuery(event.target.value);
    };

    const handleClick = () => {
        onSearch(query);
    }

    return (
        <div className='search-bar'>
            <input 
                type="text" 
                value={query}
                onChange={handleChange}
                className='input' />

            <button className="button" onClick={handleClick}>Search</button>
        </div>
    )
}