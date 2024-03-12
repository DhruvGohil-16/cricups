import React from 'react'
import { FaInstagram, FaFacebook, FaXing } from 'react-icons/fa';
import Header from '../components/Header';

export default function ContactUs() {
  return (
    <>
        <Header/>
        <div className="bg-gray-900 h-screen text-white py-20">
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
    </>
  )
}
