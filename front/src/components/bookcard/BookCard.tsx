// import React from 'react';
import React from 'react';
import image1 from './../../img/livre-de-magie.png';
import image2 from './../../img/peakpx.jpg';
import image3 from './../../img/journee-mondiale-du-livre.png';
import './BookCard.css';

interface BookCardProps {
    id: number; // Ajoutez cette ligne
    title: string;
    author: string;
    coverImage?: string; // Faites de coverImage une propriété optionnelle
    onRead: () => void; // Ajoutez cette ligne
  }
  
  const BookCard: React.FC<BookCardProps> = ({ id, title, author, coverImage, onRead }) => {
    const images = [image1, image2, image3]; // Remplacez par les chemins de vos images
    const randomIndex = Math.floor(Math.random() * images.length);
    const defaultImage = images[randomIndex];
    
    const displayTitle = title.length > 50 ? title.substring(0, 50) + '...' : title;
    const [isHovered, setIsHovered] = React.useState(false);

    return (
        <>
            <div key={id} className="card" onMouseEnter={() => setIsHovered(true)} onMouseLeave={() => setIsHovered(false)} title={`Titre : ${title}`}>
                <div className="card-border-top"></div>
                <div className="img">
                    <img src={coverImage ? coverImage : defaultImage} alt="" className='image' />
                </div>
                <span><h3 className='title'>{isHovered ? title : (title.length > 10 ? title.substring(0, 10) + '...' : title)}</h3></span>
                <p className="job author">Auteur : {isHovered ? author : (author.length > 15 ? author.substring(0, 15) + '...' : author)}</p>
                <button onClick={onRead}> Lire </button>
            </div>
        </>
    );
}

export default BookCard;
