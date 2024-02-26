
import './BookCard.css';
export default BookCard;

function BookCard({ title, author }) {

    return (
        <div className='card w-80'>
            <div className='info'>
                <h3 className='title'>{title}</h3>
                <p className='author'>Auteur : {author}</p>
            </div>
            <img scr="" alt={title} className='image' />
        </div>
    );
}

