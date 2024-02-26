import React, { useState } from 'react';
import "./SearchBar.css"; 

export default SearchBar ;

function SearchBar({ onSearch }){

    const [query, setQuery] = useState('');

    const handleChange = (event) => {
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