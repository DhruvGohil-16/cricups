import React from 'react'
import Header from '../components/Header'
import { FaInstagram, FaFacebook, FaXing } from 'react-icons/fa';

export default function Home() {
  return (
    <div>
        <Header/>
        <div className='bg-gray-900 h-svh bg-cover bg-center' 
           style={{ backgroundImage: "url('https://t3.ftcdn.net/jpg/06/59/60/16/240_F_659601698_daOUZD2jz3ItS6WknDAtAjo9nDW8JBUY.jpg')" }}></div>

        <div className="bg-gray-900 text-white py-5">
            <div className="absolute inset-0 bg-gray-900 opacity-50"></div>
            <div className="container mx-auto flex justify-center items-center h-full">
                <div className="text-center">
                <h2 className="text-4xl font-bold mb-4 text-white">About Us</h2>
                <p className="text-lg text-gray-300 mb-8">
                    Cricups is your ultimate destination for cricket news, live scores, match updates, and more. Stay connected with us for the latest happenings in the cricketing world.
                </p>
                </div>
            </div>
        </div>
        
        <div className="bg-gray-900 text-white py-5">
            <div className="container mx-auto text-center">
                <h2 className="text-4xl font-bold mb-4">Contact Us</h2>
                <p className="text-lg mb-8">Follow us on social media for updates and news!</p>
                <div className="flex justify-center">
                <a href="https://instagram.com" target="_blank" rel="noopener noreferrer" className="mr-4 mo">
                    <FaInstagram size={32} className="text-white hover:text-gray-300" />
                </a>
                <a href="https://facebook.com" target="_blank" rel="noopener noreferrer" className="mr-4">
                    <FaFacebook size={32} className="text-white hover:text-gray-300" />
                </a>
                <a href="https://example.com" target="_blank" rel="noopener noreferrer">
                    <FaXing size={32} className="text-white hover:text-gray-300" />
                </a>
                </div>
            </div>
        </div>

        <footer className="bg-gray-800 text-white text-center py-4">
            <div className="container mx-auto">
                <p>&copy; 2024 cricups. All rights reserved.</p>
            </div>
        </footer>
    </div>
  )
}
