import { createContext } from "react";

// eslint-disable-next-line
export const TokenContext = createContext({ token: null as string | null, setToken: (_userToken: string) => {} });
