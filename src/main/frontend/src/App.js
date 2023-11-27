import React, { useState, useEffect } from 'react';

function App() {
    const [restaurants, setRestaurants] = useState([]);

    useEffect(() => {
        const fetchRestaurants = async () => {
            try {
                const response = await fetch('http://localhost:8080/restaurant');
                const data = await response.json();
                setRestaurants(data);
            } catch (error) {
                console.error('Error fetching restaurants:', error);
            }
        };

        fetchRestaurants();
    }, []);

    return (
        <div>
            <h1>Restaurant List</h1>
            {restaurants.map((restaurant) => (
                <div key={restaurant.id}>
                    <p><strong>Name:</strong> {restaurant.name}</p>
                    <p><strong>City:</strong> {restaurant.city}</p>
                    <p><strong>Estimated Cost:</strong> {restaurant.estimatedCost}</p>
                    <p><strong>Average Rating:</strong> {restaurant.averageRating}</p>
                    <p><strong>Votes:</strong> {restaurant.votes}</p>
                    <hr />
                </div>
            ))}
        </div>
    );
};

export default App;
