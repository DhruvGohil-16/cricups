import React, { useEffect, useState } from 'react'
import axios from "axios";
import { Card, CardBody, CardFooter, Text, Heading, Stack } from '@chakra-ui/react'
import Header from '../components/Header'
import '../App.css'
import Footer from '../components/Footer';

export default function LiveMatches() {

    const [liveMatchData, setLiveMatchData] = useState([]);
    const [dataloaded, setDataLoaded] = useState(false);
    const [loading, setLoading] = useState(true); // Added loading state

    const fetchData = async () => {
        try {
          let response = await axios.get("http://localhost:8081/match/live");

          setLiveMatchData(response.data);
          setDataLoaded(true);
          setLoading(false); // Data loaded, set loading to false
        } catch (error) {
          console.log(error);
        }
      };

      useEffect(()=>{
        fetchData();
        const intervalId = setInterval(() => {
            fetchData();
            console.log("called fetchdata");
        }, 5000);
      
        return () => clearInterval(intervalId); // Cleanup function to clear interval
    }, []);

  return (
    <div className='bg-gray-900'>
        <Header/>
        <div className="container mx-auto w-screen min-h-screen text-white">
            <h2 className="text-3xl font-bold my-4">Live Matches</h2>
            {loading ? ( // Check loading state
                <p>Loading...</p>
            ) : (
                dataloaded ? ( // Check dataloaded state
                    <div className='bg-gray-900'>
                    {liveMatchData.length > 0 ? ( // Check if liveMatchData is empty
                        liveMatchData.map(match => (
                            <Card
                            direction={{ base: 'column', sm: 'row' }}
                            overflow='hidden'
                            variant='outline'
                            justifyContent='space-evenly'
                            margin='10px'
                            >
                                <img className='min-h-60 max-h-60 min-w-72 max-w-72'
                                src={match.flag1}
                                alt={match.team1}
                                />
                            
                                <Stack>
                                    <CardBody>
                                        <Heading size='md'>{match.teams}</Heading>
                                
                                        <Text py='2'>
                                            <p className="mb-3 font-normal text-gray-700 dark:text-gray-400">{match.matchInfo}</p>
                                            <div className="text-sm font-normal text-gray-700 dark:text-gray-400"><span>{match.team1}</span>-<span>{match.team1Score}</span></div>
                                            <div className="mb-3 text-sm font-normal text-gray-700 dark:text-gray-400"><span>{match.team2}</span>-<span>{match.team2Score}</span></div>
                                            <div className="mb-3 text-base font-normal text-blue-600 dark:text-gray-400">{match.liveText}</div>
                                        </Text>
                                    </CardBody>
                                
                                    <CardFooter>
                                        <a href={match.matchLink} className="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-gray-700 rounded-lg hover:bg-gray-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                                            more info
                                        </a>
                                    </CardFooter>
                                </Stack>

                                <img className='min-h-60 max-h-60 min-w-72 max-w-72'
                                src={match.flag2}
                                alt={match.team2}
                                />
                          </Card>
                        ))
                    ) : (
                        <h1>No live matches!!!</h1> // Display if no live matches
                    )}
                    </div>
                ) : (
                    <p>Loading...</p> // Display if data is not yet loaded
                )
            )}
        </div>
        <Footer/>
    </div>
  )
}
