import React from 'react'
import { Link } from 'react-router-dom'

export default function Header() {
  return (
    <div className="bg-gray-900 text-gray-100 font-sans">
      <nav className="bg-gray-800 text-white p-4">
          <div className="container mx-auto flex justify-between items-center">
              <div className="flex items-center">
                <Link to={'/'}>
                    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQxz8a8Sr1cNImyrg8O4qWOhZAO5UwZRxTHVQ&usqp=CAU" alt="Cricket Web App Logo" className="h-8 mr-2 cursor-pointer"/>
                </Link>
                <Link to={'/'}>
                    <span className="font-semibold text-xl cursor-pointer">Cricups</span>
                </Link>
              </div>
              <div className="hidden md:block">
                  <Link to={'/live'} className="text-white hover:text-gray-200 px-4 cursor-pointer">Live Matches</Link>
                  <Link to={'/recent'} className="text-white hover:text-gray-200 px-4 cursor-pointer">Recent Matches</Link>
                  <Link to={'/contact-us'} className="text-white hover:text-gray-200 px-4 cursor-pointer">Contact-us</Link>
                  <Link to={'/about-us'} className="text-white hover:text-gray-200 px-4 cursor-pointer">About-us</Link>
                  
              </div>
          </div>
      </nav>
    </div>
  )
}
