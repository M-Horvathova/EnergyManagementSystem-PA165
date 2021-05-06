import React from "react";
import {useEffect, useState, FunctionComponent, FC} from "react";
import Container from "@material-ui/core/Container";
import { MuiThemeProvider, createMuiTheme } from "@material-ui/core/styles";
import {
  BrowserRouter as Router,
  Route,
  Redirect,
  Switch,
} from "react-router-dom";

import Hidden from '@material-ui/core/Hidden';
import red from '@material-ui/core/colors/red';
import green from '@material-ui/core/colors/green';
import About from "./pages/About";
import BasicMenu from "./components/BasicMenu";
import Home from "./pages/Home";
import MenuDrawerLeft from "./components/MenuDrawerLeft";
import Login from "./pages/Login";
import Register from "./pages/Register";
import UserContext, {User} from "./context/UserContext";

{/*
  author: Michaela Horváthová
*/}
const ourTheme = createMuiTheme({
  palette: {
    primary: {
      main: red[900],
    },

    secondary: {
      main: green[900],
    },
  },
  overrides: {
    MuiButton: {
      root: {
        '&:hover': {
          backgroundColor: red[600],
        }
      },
      containedPrimary: {
        '&:hover': {
          backgroundColor: red[600],
        }
      }
    },
    MuiCard: {
      root: {
        height: "100%",
        width: "100%",
        borderStyle: "solid",
        borderWidth: "medium",
        borderColor: green[900],
        backgroundColor: green[50],
        boxShadow: '0 3px 5px 2px rgba(56, 56, 56, 0.83)',
      }
    },
    MuiCardHeader: {
      root: {
        backgroundColor: green[200],
        color: 'black',
      }
    },
    MuiList: {
      root: {
        backgroundColor:green[50],
        borderWidth: 'medium',
        borderStyle: 'solid',
        borderColor: green[900],
        boxShadow: '0 3px 5px 2px rgba(56, 56, 56, 0.83)',
      }
    },
    MuiListItem: {
      root: {
        '&:hover': {
          backgroundColor: green[100],
        }
      }
    },
    MuiTypography: {
      h3: {
        fontSize: '2.8rem',
        textDecoration: 'underline',
        textDecorationColor: green[900],
        color:  red[900],
      },
      h4: {
        fontSize: '1.6rem',
      },
      h5: {
        fontSize: '1.6rem',
        color: green[900],
        fontStyle: "italic",
      }
    },
    MuiGrid: {
      root: {
        flex: 'auto',
      }
    },
    MuiFormControl: {
      root: {
        minWidth: '130px',
      }
    },
    MuiCardMedia: {
      root: {
        height: '25vh',
      }
    }
  },

});
const App: FC = () => {
  return (
      <MuiThemeProvider theme={ourTheme}>
      <Router>
        <Hidden xsDown>
          <BasicMenu/>
        </Hidden>
        <Hidden smUp>
          <MenuDrawerLeft/>
        </Hidden>
        <main className="App">
          <Container maxWidth="lg">
            <Switch>
              <Route exact path="/pa165/about/" component={About} />
              <Route exact path="/pa165/" render={(props) =>(
                  <Home {...props} header={"Hello!"} />
              )} />
              <Route exact path="/pa165/login/" component={Login} />
              <Route exact path="/pa165/register/" component={Register} />
            </Switch>
          </Container>
        </main>
      </Router>
      </MuiThemeProvider>
  );
}

export default App;
