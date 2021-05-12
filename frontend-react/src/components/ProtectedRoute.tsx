import React, { FunctionComponent } from "react";
import { Redirect, Route, RouteProps } from "react-router-dom";
import { getCurrentUser } from "../services/auth";
import Config from "../utils/Config";

export interface ProtectedRouteProps extends RouteProps {
    // when role is not defined, it allowed for both
    role?: "User" | "Administrator";
}

const ProtectedRoute: FunctionComponent<ProtectedRouteProps> = ({
    role,
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
                const user = getCurrentUser();
                if (!user) {
                    return (
                        <Redirect
                            to={{
                                pathname: "/about",
                                state: { from: props.location },
                            }}
                        />
                    );
                }

                if (role && user.role !== role) {
                    return (
                        <Redirect
                            to={{
                                pathname:
                                    user.role === "User"
                                        ? Config.userHome
                                        : Config.adminHome,
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
