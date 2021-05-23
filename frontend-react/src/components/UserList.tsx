import { Grid } from "@material-ui/core";
import React, {FunctionComponent, Fragment, useEffect, useState} from "react";
import HouseDTO from "../interfaces/HouseDTO";
import HouseTile from "./HouseTile";
import { useTranslation } from "react-i18next";
import PortalUserListingDTO from "../interfaces/PortalUserListingDTO";
import {Pagination} from "@material-ui/lab";
import UserTile from "./UserTile";
import axios from "axios";
import Config from "../utils/Config";
import {getCurrentUser} from "../services/auth";

export interface UserListProps {
    users: PortalUserListingDTO;
}

const UserList: FunctionComponent<UserListProps> = ({ users }) => {
    const { t } = useTranslation();

    const handleOnChange = async (event: object, page: number) => {
        axios({
            method: "GET",
            url: Config.urlRestBase + `/users/${page}`,
        }).then((response) => {
            const result: PortalUserListingDTO = response.data as PortalUserListingDTO;
            users = result;
        });
    };

    return (
        <Fragment>
            <Grid container direction="row" alignItems="flex-start" spacing={3}>
                {users.portalUserDTOs.length === 0 ? (
                    <Grid item>{t("users.nothing")}</Grid>
                ) : (
                    ""
                )}
                {users.portalUserDTOs.map((user) => {
                    return (
                        <Grid key={user.id} item>
                            <UserTile user={user}/>
                        </Grid>
                    );
                })}
            </Grid>
            {users.portalUserDTOs.length !== 0 && <Pagination count={users.pagesCount} page={users.page} variant="outlined" onChange={handleOnChange} />}
        </Fragment>
    );
};

export default UserList;
