/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable jsx-a11y/anchor-is-valid */
import logo from './../img/bibliotheque.png';
import { Navbar, Typography, IconButton, Button, Input } from "@material-tailwind/react";
import { BellIcon, Cog6ToothIcon } from "@heroicons/react/24/solid";
import './App.css';
import Acceuil from "./../pages/accueil/Accueil";
import Livres from "./../pages/livres/Livres";
import Recherches from "./../pages/recherches/Recherches";

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import React, {Fragment, useEffect, useState} from "react";
import {Route, Routes, useLocation} from "react-router-dom";
// import AppHeader, { AppHeaderProps } from './AppHeader';
import { Disclosure, Menu, Transition } from '@headlessui/react'
import { Bars3Icon, XMarkIcon } from '@heroicons/react/24/outline'

// build an instance of our AppHeaderProps to pass to the AppHeader component
// const headerProps: AppHeaderProps = {
//   title: 'My Dive Log',
//   description: 'Log, Track, Review your dive logs and relive the experience',
//   links: [
//     {
//       label: 'Logs',
//       route: '/logs/list',
//     },
//     {
//       label: 'New Entry',
//       route: '/logs/create',
//     },
//   ],
// };


function classNames(...classes: string[]) {
  return classes.filter(Boolean).join(' ')
}

function App() {
  const location = useLocation();
  const navigation = [
    { name: 'Menu', href: '#/', current: true  },
    { name: 'Recherches', href: '#/recherches', current: false  },
    { name: 'Livres', href: '#/livres', current: false  },
    { name: 'Auteurs', href: '#/auteurs', current: false  },
  ];

  return (
    <>
      <main className="App">
        {/* <AppHeader
          
          title={headerProps.title}
          description={headerProps.description}
          links={headerProps.links}
        /> */}

        <Disclosure as="nav" className="fixed top-0 w-full z-10 bg-gradient-to-r from-secondary-500 to-secondary-700">
          {({ open }) => (
            <>
              <div className="mx-auto max-w-7xl px-2 sm:px-6 lg:px-8">
                <div className="relative flex h-16 items-center justify-between">
                  <div className="absolute inset-y-0 left-0 flex items-center sm:hidden">
                    {/* Mobile menu button*/}
                    <Disclosure.Button className="relative inline-flex items-center justify-center rounded-md p-2 text-secondary-400 hover:bg-secondary-600 hover:text-tahiti focus:outline-none focus:ring-2 focus:ring-inset focus:ring-tahiti">
                      <span className="absolute -inset-0.5" />
                      <span className="sr-only">Open main menu</span>
                      {open ? (
                        <XMarkIcon className="block h-6 w-6" aria-hidden="true" />
                      ) : (
                        <Bars3Icon className="block h-6 w-6" aria-hidden="true" />
                      )}
                    </Disclosure.Button>
                  </div>
                  <div className="flex flex-1 items-center justify-center sm:items-stretch sm:justify-start">
                    <div className="flex flex-shrink-0 items-center">
                      <img
                        className="h-8 w-auto hidden sm:ml-6 sm:block"
                        src={logo}
                        alt="Biblio"
                      />
                    </div>
                    <div className="hidden sm:ml-6 sm:block">
                      <div className="flex space-x-4">
                        {navigation.map((item) => (
                          <a
                            key={item.name}
                            href={item.href}
                            className={classNames(
                              item.current ? 'bg-secondary-200 text-black' : 'text-secondary-300 hover:bg-secondary-600 hover:text-tahiti',
                              'rounded-md px-3 py-2 text-sm font-medium'
                            )}
                            aria-current={item.current ? 'page' : undefined}
                          >
                            {item.name}
                          </a>
                        ))}
                      </div>
                    </div>
                  </div>
                  <div className="absolute inset-y-0 right-0 flex items-center pr-2 sm:static sm:inset-auto sm:ml-6 sm:pr-0">
                    <div className="relative flex w-full gap-2 md:w-max justify-between">
                      <Input
                        type="search"
                        color="white"
                        variant="outlined"
                        crossOrigin={undefined}
                      />
                      <Button   
                        size="sm"
                        color="white"
                        className="right-0 top-0 rounded gap-2"
                        placeholder={undefined}
                      >
                        Search
                      </Button>
                    </div>
                    <button
                      type="button"
                      className="relative rounded-full bg-secondary-700 p-1 text-secondary-400 hover:text-tahiti focus:outline-none focus:ring-2 focus:ring-tahiti focus:ring-offset-2 focus:ring-offset-secondary-700 ml-2"
                    >
                      <span className="absolute -inset-1.5" />
                      <span className="sr-only">Voir notifications</span>
                      <BellIcon className="h-6 w-6" aria-hidden="true" />
                    </button>

                    {/* Profile dropdown */}
                    <Menu as="div" className="relative ml-3">
                      <div>
                        <Menu.Button className="relative flex rounded-full bg-secondary-700 text-sm focus:outline-none focus:ring-2 focus:ring-tahiti focus:ring-offset-2 focus:ring-offset-secondary-700">
                          <span className="absolute -inset-1.5" />
                          <span className="sr-only">Open user menu</span>
                          <img
                            className="h-8 w-8 rounded-full"
                            src="https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80"
                            alt=""
                          />
                        </Menu.Button>
                      </div>
                      <Transition
                        as={Fragment}
                        enter="transition ease-out duration-100"
                        enterFrom="transform opacity-0 scale-95"
                        enterTo="transform opacity-100 scale-100"
                        leave="transition ease-in duration-75"
                        leaveFrom="transform opacity-100 scale-100"
                        leaveTo="transform opacity-0 scale-95"
                      >
                        <Menu.Items className="absolute right-0 z-10 mt-2 w-48 origin-top-right rounded-md bg-white py-1 shadow-lg ring-1 ring-black ring-opacity-5 focus:outline-none">
                          <Menu.Item>
                            {({ active }) => (
                              <a
                                href="#"
                                className={classNames(active ? 'bg-secondary-100' : '', 'block px-4 py-2 text-sm text-secondary-600')}
                              >
                                Votre Profil
                              </a>
                            )}
                          </Menu.Item>
                          <Menu.Item>
                            {({ active }) => (
                              <a
                                href="#"
                                className={classNames(active ? 'bg-secondary-100' : '', 'block px-4 py-2 text-sm text-secondary-600')}
                              >
                                Parametres
                              </a>
                            )}
                          </Menu.Item>
                          <Menu.Item>
                            {({ active }) => (
                              <a
                                href="#"
                                className={classNames(active ? 'bg-secondary-100' : '', 'block px-4 py-2 text-sm text-secondary-600')}
                              >
                                Sign out
                              </a>
                            )}
                          </Menu.Item>
                        </Menu.Items>
                      </Transition>
                    </Menu>
                  </div>
                </div>
              </div>

              <Disclosure.Panel className="sm:hidden">
                <div className="space-y-1 px-2 pb-3 pt-2">
                  {navigation.map((item) => (
                    <Disclosure.Button
                      key={item.name}
                      as="a"
                      href={item.href}
                      className={classNames(
                        item.current ? 'bg-secondary-200 text-tahiti' : 'text-secondary-300 hover:bg-secondary-600 hover:text-tahiti',
                        'block rounded-md px-3 py-2 text-base font-medium'
                      )}
                      aria-current={item.current ? 'page' : undefined}
                    >
                      {item.name}
                    </Disclosure.Button>
                  ))}
                </div>
              </Disclosure.Panel>
            </>
          )}
        </Disclosure>
      </main>
      <br></br>
      <div className="m-20">
        <Routes>
          <Route path="/" element={<Acceuil key={"accueil-"} />} />
          <Route path="/livres" element={<Livres key={"livres-"} />} />
          <Route path="/recherches" element={<Recherches key={"recherches-"} />} />
        </Routes>
      </div>
    </>
  );
}

export default App;
