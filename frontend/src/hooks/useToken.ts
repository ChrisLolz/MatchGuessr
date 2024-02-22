import { useCallback, useEffect, useState } from 'react';
import userService from '../services/user';

export default function useToken() {
    const [token, setToken] = useState(<string | null>(null));

    const getToken = useCallback(async () => {
        const userToken = localStorage.getItem('token');
        if (userToken) {
            const verified = await userService.verify(userToken);
            if (verified) {
                setToken(userToken);
            } else {
                localStorage.removeItem('token');
            }
        }
    }, []);
    
    useEffect(() => {
        getToken();
    }, [getToken]);

    const saveToken = (userToken: string) => {
        localStorage.setItem('token', userToken);
        setToken(userToken);
    };
    
    return {
        token,
        setToken: saveToken as (userToken: string) => void
    }
}