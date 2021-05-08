import React from "react";
import {useEffect, useState, FunctionComponent} from "react";

interface AppProps {
  header: string;
}

const App: FunctionComponent<AppProps> = ({header}) => {
  const [message, setMessage] = useState<string>("Loading...");

  useEffect(() => {
    fetch("http://localhost:8080/pa165/rest/hello")
        .then((response) => response.json())
        .then((json) => setMessage(json.data))
  }, []);

  return (
    <div className="App">
      <h1>{header}</h1>
      {message}
    </div>
  );
}

export default App;
