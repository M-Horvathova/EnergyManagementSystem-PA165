import * as React from "react";
import {useEffect, useState} from "react";

export interface HelloWorldProps {
    userName: string;
}
function App() {
    const [message, setMessage] = useState<string>();

    useEffect(() => {
        fetch('https://localhost:8080/pa165')
            .then(response => response.json()).
            then(data => setMessage(data));
    }, [])

    return (
        <h1>
            Hello!

            {message}
        </h1>
    );
}

export default App;