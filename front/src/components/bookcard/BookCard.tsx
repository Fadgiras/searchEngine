import React from 'react';
import Book from './../Book';

interface BookCardProps {
  book: Book;
}

const BookCard: React.FC<BookCardProps> = ({ book }) => {
  return (
    <div>
      <h2>{book.title}</h2>
      <p>{book.autor}</p>
    </div>
  );
};

export default BookCard;