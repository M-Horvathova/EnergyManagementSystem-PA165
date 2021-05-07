import React from "react";
import { FC } from "react";
import Container from "@material-ui/core/Container";
import { MuiThemeProvider, createMuiTheme } from "@material-ui/core/styles";
import {
    BrowserRouter as Router,
    Route,
    Redirect,
    Switch,
} from "react-router-dom";
import { getCurrentUser } from "./services/auth";

import Hidden from "@material-ui/core/Hidden";
import About from "./pages/About";
import BasicMenu from "./components/BasicMenu";
import Houses from "./pages/Houses";
import MenuDrawerLeft from "./components/MenuDrawerLeft";
import Login from "./pages/Login";
import Register from "./pages/Register";
import HousePage from "./pages/HousePage";

/*
  author: Michaela Horváthová
*/
const ourTheme = createMuiTheme({});
const App: FC = () => {
    const user = getCurrentUser();

    return (
        <MuiThemeProvider theme={ourTheme}>
            <Router>
                <Hidden xsDown>
                    <BasicMenu />
                </Hidden>
                <Hidden smUp>
                    <MenuDrawerLeft />
                </Hidden>
                <main className="App">
                    <Container maxWidth="lg">
                        <Switch>
                            <Route
                                exact
                                path="/pa165/about/"
                                component={About}
                            />
                            <Route
                                exact
                                path="/pa165/login/"
                                component={Login}
                            />
                            <Route
                                exact
                                path="/pa165/register/"
                                component={Register}
                            />
                            <Route
                                exact
                                path="/pa165/houses"
                                render={(props) =>
                                    user != null ? (
                                        <Houses {...props} user={user} />
                                    ) : (
                                        <Redirect
                                            to={{
                                                pathname: "/pa165/login",
                                                state: { from: props.location },
                                            }}
                                        />
                                    )
                                }
                            />
                            <Route
                                exact
                                path="/pa165/house/:id"
                                render={(props) =>
                                    user != null ? (
                                        <HousePage {...props} />
                                    ) : (
                                        <Redirect
                                            to={{
                                                pathname: "/pa165/login",
                                                state: { from: props.location },
                                            }}
                                        />
                                    )
                                }
                            />
                            <Redirect from="/pa165/" to="/pa165/houses" />
                        </Switch>
                    </Container>
                </main>
            </Router>
        </MuiThemeProvider>
    );
};

export default App;
