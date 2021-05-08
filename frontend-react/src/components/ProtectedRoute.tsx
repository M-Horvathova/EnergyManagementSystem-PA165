import React, { FunctionComponent } from "react";
import { Redirect, Route, RouteProps } from "react-router-dom";
import { getCurrentUser } from "../services/auth";

export interface ProtectedRouteProps extends RouteProps {}

const ProtectedRoute: FunctionComponent<ProtectedRouteProps> = ({
    exact,
    path,
    render,
    component: Component,
    ...other
}) => {
    return (
        <Route
            {...other}
            exact={exact}
            path={path}
            render={(props) => {
                if (!getCurrentUser()) {
                    return (
                        <Redirect
                            to={{
                                pathname: "/login",
                                state: { from: props.location },
                            }}
                        />
                    );
                }

                if (Component) {
                    return <Component {...props} />;
                }

                if (render) {
                    return render(props);
                }

                throw new Error("Component or render must be initializet");
            }}
        />
    );
};

export default ProtectedRoute;
