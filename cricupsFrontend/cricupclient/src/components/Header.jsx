import React from 'react'

export default function Header() {
  return (
    <div className="bg-gray-900 text-gray-100 font-sans">
      <nav className="bg-gray-800 text-white p-4">
          <div className="container mx-auto flex justify-between items-center">
              <div className="flex items-center">
                  <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQxz8a8Sr1cNImyrg8O4qWOhZAO5UwZRxTHVQ&usqp=CAU" alt="Cricket Web App Logo" className="h-8 mr-2"/>
                  <span className="font-semibold text-xl">Cricups</span>
              </div>
              <div className="hidden md:block">
                  <a href="#" className="text-white hover:text-gray-200 px-4">Live Matches</a>
                  <a href="#" className="text-white hover:text-gray-200 px-4">Recent Matches</a>
                  <a href="#" className="text-white hover:text-gray-200 px-4">About</a>
                  <a href="#" className="text-white hover:text-gray-200 px-4">Contact Us</a>
              </div>
          </div>
      </nav>
    </div>
  )
}
