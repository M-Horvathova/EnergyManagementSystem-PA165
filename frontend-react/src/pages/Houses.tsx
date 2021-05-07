import React, { FunctionComponent, useEffect, useState, Fragment } from "react"
import User from "../interfaces/User"
import HouseList from "../components/HouseList"
import House from "../interfaces/House"

export interface HomesProps {
    user: User
}

const Houses: FunctionComponent<HomesProps> = ({ user }) => {
    const [houses, setHouses] = useState<Array<House>>([])

    useEffect(() => {
        setHouses([
            {
                id: 1,
                name: "Kounicova",
            },
            {
                id: 2,
                name: "Vinarská",
            },
            {
                id: 3,
                name: "Lesna",
            },
            {
                id: 4,
                name: "Moj domov",
            },
            {
                id: 5,
                name: "Muni",
            },
        ])
    }, [])

    return (
        <Fragment>
            <h1>Houses</h1>
            <HouseList houses={houses} />
        </Fragment>
    )
}

export default Houses
