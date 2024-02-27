import './BookCard.css';

interface BookCardProps {
    title: string;
    author: string;
    coverImage: string;
  }
  
  const BookCard: React.FC<BookCardProps> = ({ title, author, coverImage }) => {

    return (
        <>
            <div className="card">
                <div className="card-border-top">
                </div>
                <div className="img">
                    <img src={coverImage} alt=""className='image' />
                </div>
                <span><h3 className='title'>{title}</h3></span>
                <p className="job author">Auteur : {author}</p>
                <button> Lire
                </button>
            </div>
        </>
    );
}

export default BookCard;
