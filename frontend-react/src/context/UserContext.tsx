import React, {createContext, useEffect, useLayoutEffect, useState} from "react";

export type User = {
  email: string;
  firstName: string;
  lastName: string;
  role: 'user' | 'admin';
};

type UserCtx = {
  user: User | null;
};


const UserContext = createContext<UserCtx>({user: null});

export default UserContext;



