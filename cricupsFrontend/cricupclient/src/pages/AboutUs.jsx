// AboutUs.js
import React from 'react';
import Header from '../components/Header';

const AboutUs = () => {
  return (
    <>
        <Header/>
        <div className="bg-gray-900 h-screen text-white py-5">
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
    </>
  );
};

export default AboutUs;
