import * as React from "react";
import { useEffect, useState } from "react";

export interface HelloWorldProps {
  userName: string;
}
function App() {
  const [message, setMessage] = useState<string>();

  useEffect(() => {
    fetch("http://localhost:8080/pa165/api/v1/hello")
      .then((response) => response.json())
      .then((data) => setMessage(data.data));
  }, []);

  return (
    <h1>
      Hello!
      {message}
    </h1>
  );
}

export default App;
