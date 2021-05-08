import React, { FC } from "react";
import Typography from "@material-ui/core/Typography";

const Dashboard: FC = () => {
    return (
        <div>
            <Typography variant={"h3"} align={"center"}>
                {"Welcome admin!"}
            </Typography>
        </div>
    );
};

export default Dashboard;
