import { FC } from "react";
import Container from "@material-ui/core/Container";
import { MuiThemeProvider, createMuiTheme } from "@material-ui/core/styles";
import {
    BrowserRouter as Router,
    Route,
    Redirect,
    Switch,
} from "react-router-dom";

import Hidden from "@material-ui/core/Hidden";
import About from "./pages/About";
import BasicMenu from "./components/BasicMenu";
import Houses from "./pages/Houses";
import MenuDrawerLeft from "./components/MenuDrawerLeft";
import Login from "./pages/Login";
import Register from "./pages/Register";
import HousePage from "./pages/HousePage";
import EditHouse from "./pages/EditHouse";
import AddHouse from "./pages/AddHouse";
import ProtectedRoute from "./components/ProtectedRoute";

/*
  author: Michaela Horváthová
*/
const ourTheme = createMuiTheme({});
const App: FC = () => {
    return (
        <MuiThemeProvider theme={ourTheme}>
            <Router>
                <Hidden xsDown>
                    <BasicMenu />
                </Hidden>
                <Hidden smUp>
                    <MenuDrawerLeft />
                </Hidden>
                <main className="App" style={{ marginTop: 50 }}>
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
                            <ProtectedRoute
                                exact
                                path="/pa165/houses"
                                component={Houses}
                            />
                            <ProtectedRoute
                                exact
                                path="/pa165/house/edit/:id"
                                component={EditHouse}
                            />
                            <ProtectedRoute
                                exact
                                path="/pa165/house/create"
                                component={AddHouse}
                            />
                            <ProtectedRoute
                                exact
                                path="/pa165/house/:id"
                                component={HousePage}
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
