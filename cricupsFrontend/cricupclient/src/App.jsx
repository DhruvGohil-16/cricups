import { useState } from 'react'
import { BrowserRouter, Routes, Route } from "react-router-dom";
import ContactUs from './pages/ContactUs';
import AboutUs from './pages/AboutUs';
import Home from './pages/Home';
import LiveMatches from './pages/LiveMatches';
import RecentMatches from './pages/RecentMatches';

function App() {
  const [count, setCount] = useState(0);

  return (
    <BrowserRouter>
      <Routes>
        <Route exact path="/" element={<Home />} />
        <Route path="/live" element={<LiveMatches />} />
        <Route path="/recent" element={<RecentMatches />} />
        <Route path="/about-us" element={<AboutUs />} />
        <Route path="/contact-us" element={<ContactUs />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App
