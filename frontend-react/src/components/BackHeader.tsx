import { IconButton } from "@material-ui/core";
import { ArrowBack } from "@material-ui/icons";
import React, { FunctionComponent } from "react";
import { useHistory } from "react-router-dom";

export interface BackHeaderProps {}

const BackHeader: FunctionComponent<BackHeaderProps> = () => {
    const history = useHistory();

    return (
        <div>
            <IconButton aria-label="back" onClick={() => history.goBack()}>
                <ArrowBack />
            </IconButton>
        </div>
    );
};

export default BackHeader;
