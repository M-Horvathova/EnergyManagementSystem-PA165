/*
 author: Michaela Horváthová
*/

const en = {
    translation: {
        about: {
            title: "Welcome to Smart Energy Monitoring System",
            description1:
                "This application was developed as a part of the PA165 Enterprise Applications in Java course at Masaryk University",
            description2:
                "The system will allow users to manage the smart meter devices at home. For a user, it will be possible to set different smart meters for the different houses they own/rent, and track the power consumption statistics over time that is collected automatically. They can track the power consumption of different devices in different time of the day (e.g., at night). They can also shut down the power to one of their houses through the system, when power is not needed. The power distribution company can consult the power consumption statistics over period of times of all the users registered into the system.",
            git:
                "The project is available at GitHub repository https://github.com/M-Horvathova/EnergyManagementSystem-PA165",
            creators:
                "Created by Michaela Horváthová, Patrik Valo, Martin Podhora, Matej Rišňovský, 2021",
        },
        menu: {
            home: "Home",
            about: "About",
            app_name: "Smart Energy",
            houses: "My Houses",
            logout: "Logout",
            login: "Login",
            register: "Register",
        },
        houses: {
            houses: "Houses",
            open: "Open",
            edit: "Edit",
            remove: "Remove",
            add: "Add house",
            nothing: "Nothing to show",
        },
        house: {
            open: "Open",
            edit: "Edit",
            remove: "Remove",
            add: "Add meter",
            nothing: "Nothing to show",
            off: "Turn off",
            on: "Turn on"
        },
        addHouse: {
            add: "Add house",
        },
        editHouse: {
            edit: "Edit house",
        },
        houseForm: {
            name: "Name",
            street: "Street",
            code: "Code",
            city: "City",
            postCode: "Post Code",
            country: "Country",
            confirm: "Confirm",
        },
        register: {
            register: "Register",
            email: "Email",
            password: "Password",
            password_conf: "Confirm password",
            already_registered: "Already registered",
            first_name: "First name",
            last_name: "Last name",
            phone: "Phone",
            err_invalid_email: "Invalid email",
            err_password_required: "Password required",
            err_confirm_password_required: "Password confirm required",
            err_password_not_same: "Passwords are not the same",
            err_first_name_required: "First name required",
            err_last_name_required: "Last name required",
            err_invalid_phone: "Invalid phone length",
        },
        login: {
            sign_in: "Sign in",
            login: "Login",
            email: "Email",
            password: "Password",
            new_user: "New user?",
            register: "Register here",
            invalid_credentials: "You have filled invalid credentials",
        },
 		dashboard: {
            welcome_admin: "Welcome admin",
            all_time_statistics: "All-time statistics",
            all_time_total: "All-time total (kW)",
            all_time_average: "All-time average per log (kW)",
            user_stats_from: "Statistics from",
            user_stats_to: "Statistics to",
            get_statistics: "Get statistics",
            table_username: "User",
            table_total_spent: "Total (kW)",
            table_average_spent: "Average across all houses and smart meters (kW)",
            rows_per_page: "Rows per page",
        },
 		smartMeterForm: {
            description: "Description",
            running: "Turned on",
            confirm: "Confirm"
        },
        smartMeter: {
            open: "Open",
            edit: "Edit",
            remove: "Remove",
            create: "Create",
            totalPowerConsumption: "Total power consumption:",
            turnedOn: "Turned on",
            turnedOff: "Turned off",
            averagePowerConsumptionAtNight: "Average power consumption at night",
            averagePowerConsumptionInDay: "Average power consumption in day",
            powerSpentForDate: "Show power spent for date"
        }
    },
};

export default en;
