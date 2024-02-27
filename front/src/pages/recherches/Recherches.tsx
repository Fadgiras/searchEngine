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
    <>
      <div className="flex items-center space-x-2">
        <select value={searchType} onChange={(e) => setSearchType(e.target.value as 'id' | 'author' | 'text')} className="border p-2 rounded">
          <option value="id">ID</option>
          <option value="author">Auteur</option>
          <option value="text">Texte</option>
        </select>
        <input type="text" value={searchValue} onChange={(e) => setSearchValue(e.target.value)} className="border p-2 rounded flex-grow" />
        <button onClick={handleSearch} className="bg-blue-500 text-white p-2 rounded">Rechercher</button>
      </div>

      {/* <div className="tss-1l4p3k-root MuiBox-root mui-0" style={{ "--banner-height": "450px" } as React.CSSProperties}>
        <h1 className="MuiTypography-root MuiTypography-h1 tss-1hkdiuq-root-header mui-16lxm23">Cherchez des ouvrages dans les bibliothèques près de chez vous</h1>
        <div className="MuiBox-root mui-103ku3o">
          <div className="MuiBox-root mui-gmuwbf">
            <form className="MuiPaper-root MuiPaper-elevation MuiPaper-rounded MuiPaper-elevation1 tss-w5pr3k-paper mui-hsycap" role="search">
              <div data-testid="hero-search-box-select" className="MuiInputBase-root MuiOutlinedInput-root MuiInputBase-colorPrimary tss-kyr3pl-select-searchBox mui-1elnojk">
                <div tabIndex={0} role="combobox" aria-controls=":r1p:" aria-expanded="false" aria-haspopup="listbox" className="MuiSelect-select MuiSelect-outlined MuiInputBase-input MuiOutlinedInput-input mui-1yhuea8">
                  Ouvrages
                </div>
                <input aria-invalid="false" aria-hidden="true" tabIndex={-1} className="MuiSelect-nativeInput mui-1k3x8v3" data-testid="hero-search-box-select-input" value="items">
                  <svg className="MuiSvgIcon-root MuiSvgIcon-fontSizeMedium MuiSelect-icon MuiSelect-iconOutlined mui-1yza03u" focusable="false" aria-hidden="true" viewBox="0 0 24 24" data-testid="ArrowDropDownIcon">
                    <path d="M7 10l5 5 5-5z"></path>
                  </svg>
                  <fieldset aria-hidden="true" className="MuiOutlinedInput-notchedOutline mui-igs3ac">
                    <legend className="mui-ihdtdm">
                      <span className="notranslate">
                      ​</span>
                    </legend>
                  </fieldset>
                </input>
                <div className="MuiAutocomplete-root MuiAutocomplete-fullWidth tss-1qcoye3-root-inputBase-input mui-vtpdau">
                  <div className="MuiFormControl-root MuiFormControl-fullWidth MuiTextField-root mui-feqhe6">
                    <div className="MuiInputBase-root MuiOutlinedInput-root MuiInputBase-colorPrimary MuiInputBase-fullWidth MuiInputBase-formControl MuiAutocomplete-inputRoot mui-jfcr8b">
                      <input aria-invalid="false" autoComplete="off" id="home-page-search-box" placeholder="Cherchez des livres, articles et plus" type="text" data-testid="home-page-search-bar" aria-label="Entrez l'énoncé de recherche" className="MuiInputBase-input MuiOutlinedInput-input MuiAutocomplete-input MuiAutocomplete-inputFocused mui-dhw3sk" aria-autocomplete="both" aria-expanded="false" autoCapitalize="none" spellCheck="false" role="combobox" value="">
                        <fieldset aria-hidden="true" className="MuiOutlinedInput-notchedOutline mui-igs3ac">
                          <legend className="mui-ihdtdm">
                            <span className="notranslate">​</span>
                          </legend>
                        </fieldset>
                      </input>
                    </div>
                  </div>
                </div>
                <button className="MuiButtonBase-root MuiButton-root MuiButton-contained MuiButton-containedPrimary MuiButton-sizeMedium MuiButton-containedSizeMedium MuiButton-root MuiButton-contained MuiButton-containedPrimary MuiButton-sizeMedium MuiButton-containedSizeMedium tss-f4wmgr-button mui-1acs0kp" tabIndex={0} type="submit" aria-label="Chercher" data-testid="search-button">
                  <svg className="MuiSvgIcon-root MuiSvgIcon-fontSizeMedium mui-14yq2cq" focusable="false" aria-hidden="true" viewBox="0 0 24 24" data-testid="MagnifyIcon">
                    <path d="M9.5,3A6.5,6.5 0 0,1 16,9.5C16,11.11 15.41,12.59 14.44,13.73L14.71,14H15.5L20.5,19L19,20.5L14,15.5V14.71L13.73,14.44C12.59,15.41 11.11,16 9.5,16A6.5,6.5 0 0,1 3,9.5A6.5,6.5 0 0,1 9.5,3M9.5,5C7,5 5,7 5,9.5C5,12 7,14 9.5,14C12,14 14,12 14,9.5C14,7 12,5 9.5,5Z"></path>
                  </svg>
                  <span className="MuiTouchRipple-root mui-w0pj6f"></span>
                </button>
              </div>
            </form>
            <div className="MuiBox-root mui-1iuj5ih">
              <button className="MuiButtonBase-root MuiIconButton-root MuiIconButton-colorInherit MuiIconButton-sizeLarge tss-cg9iy4-iconButton mui-6qpy1h" tabIndex={parseInt("0")} type="button" data-testid="advanced-search-button" aria-label="Recherche avancée">
                <svg className="MuiSvgIcon-root MuiSvgIcon-fontSizeMedium mui-14yq2cq" focusable="false" aria-hidden="true" viewBox="0 0 24 24" data-testid="TuneVerticalVariantIcon">
                  <path d="M8 12.14V2H6V12.14C4.28 12.59 3 14.14 3 16S4.28 19.41 6 19.86V22H8V19.86C9.72 19.41 11 17.86 11 16S9.72 12.59 8 12.14M7 14C8.1 14 9 14.9 9 16S8.1 18 7 18C5.9 18 5 17.1 5 16S5.9 14 7 14M18 2H16V4.14C14.28 4.59 13 6.14 13 8S14.28 11.41 16 11.86V22H18V11.86C19.72 11.41 21 9.86 21 8S19.72 4.59 18 4.14V2M17 6C18.1 6 19 6.9 19 8S18.1 10 17 10C15.9 10 15 9.1 15 8S15.9 6 17 6Z"></path>
                </svg>
                <span className="MuiTouchRipple-root mui-w0pj6f"></span>
              </button>
            </div>
          </div>
        </div>
      </div> */}
    </>
  );
};

export default Recherches;
