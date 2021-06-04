import {FC, useEffect, useState} from "react";
import Container from "@material-ui/core/Container";
import { MuiThemeProvider, createMuiTheme } from "@material-ui/core/styles";
import { Route, Redirect, Switch, HashRouter } from "react-router-dom";

import Hidden from "@material-ui/core/Hidden";
import About from "./pages/About";
import BasicMenu from "./components/BasicMenu";
import Houses from "./pages/Houses";
import MenuDrawerLeft from "./components/MenuDrawerLeft";
import Login from "./pages/Login";
import Register from "./pages/Registration";
import HousePage from "./pages/HousePage";
import EditHouse from "./pages/EditHouse";
import AddHouse from "./pages/AddHouse";
import SmartMeterDetail from "./pages/SmartMeterDetail";
import EditSmartMeter from "./pages/EditSmartMeter";
import AddSmartMeter from "./pages/AddSmartMeter";
import ProtectedRoute from "./components/ProtectedRoute";
import Config from "./utils/Config";
import Dashboard from "./pages/Dashboard";
import {UserContext, getCurrentUser} from "./services/auth";
import {indigo, orange} from "@material-ui/core/colors";
import {CssBaseline} from "@material-ui/core";

/*
  author: Michaela Horváthová
*/
const ourLightTheme = ({
    palette: {
        primary: {
            main: indigo[500],
            light: '#757ce8',
            dark: '#002884',
        },
        secondary: {
            main: '#8c433a',
        },
    },
    overrides: {
        MuiCard: {
            root: {
                minWidth: 275,
                height: "100%",
                width: "100%",
                borderWidth: "medium",
            },
        },
        MuiButton: {
          root: {
            '&:hover': {
               backgroundColor: indigo[800],
            }
          },
        },
    },
});

const ourDarkTheme = createMuiTheme({
    palette: {
        type: 'dark',
        primary: {
            main: '#050c3d',
            light: '#534bae',
            dark: '#000041',
        },
        secondary: {
            main: orange[100],
        },
        background: {
            default: '#303030',
            paper: '#424242',
        }

    },
    overrides: {
        MuiCard: {
            root: {
                minWidth: 275,
                height: "100%",
                width: "100%",
                borderWidth: "medium",
            },
        },
        MuiButton: {
            root: {
                '&:hover': {
                    backgroundColor: indigo[700],
                }
            },
        },
    },
});

const App: FC = () => {
    const user = getCurrentUser();
    const [theme, setTheme] = useState<'light' | 'dark'>('light');
    const muiTheme = theme === 'light' ? createMuiTheme(ourLightTheme) : createMuiTheme(ourDarkTheme);

    useEffect(() => {
        const locTheme = window.localStorage.getItem('theme');
        locTheme && setTheme(locTheme === 'light' ? 'light' : 'dark');
    }, [])

    return (
        <MuiThemeProvider theme={muiTheme}>
            <CssBaseline />
            <UserContext.Provider value={{user}}>
            <HashRouter>
                <Hidden xsDown>
                    <BasicMenu theme={theme} setTheme={setTheme}/>
                </Hidden>
                <Hidden smUp>
                    <MenuDrawerLeft theme={theme} setTheme={setTheme}/>
                </Hidden>

                <main className="App" style={{ marginTop: 50 }}>
                    <Container maxWidth="lg">
                        <Switch>
                            <Route exact path="/about" component={About} />
                            <Route exact path="/login" component={Login} />
                            <Route exact path="/register" component={Register} />

                            <ProtectedRoute
                                role="User"
                                exact
                                path={Config.userHome}
                                component={Houses}
                            />
                            <ProtectedRoute
                                role="Administrator"
                                exact
                                path={Config.adminHome}
                                component={Dashboard}
                            />
                            <ProtectedRoute
                                role="User"
                                exact
                                path="/house/edit/:id"
                                component={EditHouse}
                            />
                            <ProtectedRoute
                                role="User"
                                exact
                                path="/house/create"
                                component={AddHouse}
                            />
                            <ProtectedRoute
                                role="User"
                                exact
                                path="/house/:id"
                                component={HousePage}
                            />
                            <ProtectedRoute
                                role="User"
                                exact
                                path="/smartmeter/edit/:id"
                                component={EditSmartMeter}
                            />
                            <ProtectedRoute
                                role="User"
                                exact
                                path="/smartmeter/create"
                                component={AddSmartMeter}
                            />
                            <ProtectedRoute
                                role="User"
                                exact
                                path="/smartmeter/:id"
                                component={SmartMeterDetail}
                            />
                            <Redirect from="/" to="/houses" />
                        </Switch>
                    </Container>
                </main>
            </HashRouter>
            </UserContext.Provider>
        </MuiThemeProvider>
    );
};

export default App;
