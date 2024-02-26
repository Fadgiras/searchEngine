import './index.css';
import './styles/layout.css';
import './styles/pages.css';

import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './layout/App';
import reportWebVitals from './reportWebVitals';

import {HashRouter} from "react-router-dom";
// import {migrateSavedSettings_V1} from "./utils/LocalStorageMigration";


const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

root.render(
  <React.StrictMode>
    <HashRouter>
      <App />
    </HashRouter>
  </React.StrictMode>
);

reportWebVitals();
