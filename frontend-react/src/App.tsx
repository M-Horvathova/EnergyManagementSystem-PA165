import React from "react";
import {useEffect, useState, FunctionComponent} from "react";
import Container from "@material-ui/core/Container";
import {
  BrowserRouter as Router,
  Route,
  Redirect,
  Switch,
} from "react-router-dom";

import About from "./pages/About";
import BasicMenu from "./components/BasicMenu";


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
      <Router>
        <p>{message}</p>
        <p>{header}</p>
          <BasicMenu />
        <main className="App">
          <Container maxWidth="lg">
            <Switch>
              <Route exact path="/pa165/about/" component={About} />
            </Switch>
          </Container>
        </main>
      </Router>
  );
}

export default App;
