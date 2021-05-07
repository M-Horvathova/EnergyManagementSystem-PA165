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
                address: {
                    id: 1,
                    city: "Brno",
                    code: null,
                    country: "Cesko",
                    postCode: "8997666",
                    street: null,
                },
            },
            {
                id: 2,
                name: "Vinarská",
                address: {
                    id: 2,
                    city: "Brno",
                    code: "12",
                    country: "Cesko",
                    postCode: "8997666",
                    street: null,
                },
            },
            {
                id: 3,
                name: "Dom pri lesnej ceste",
                address: {
                    id: 3,
                    city: "Bratislava",
                    code: "158",
                    country: "Slovensko",
                    postCode: "8997666",
                    street: "Lesna ulica nad Dunajom",
                },
            },
            {
                id: 4,
                name: "Môj dom",
                address: {
                    id: 3,
                    city: "Bratislava",
                    code: "158",
                    country: "Slovensko",
                    postCode: "8997666",
                    street: "Lesna ulica nad Dunajom",
                },
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
