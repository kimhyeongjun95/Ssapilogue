import React from 'react';

import Routes from './pages/Routes';
import Navbar from './components/Navbar';
import Footer from './components/Footer';

function App() {
  return (
    <>
      <Navbar />
      <Routes />
      <Footer />
    </>
  );
}

export default App;