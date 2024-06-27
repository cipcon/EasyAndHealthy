import { createContext, useContext } from 'react';
import { User } from '../App';

interface AppContextType {
    userCredentials: User;
    setUserCredentials: React.Dispatch<React.SetStateAction<User>>;
}

export const AppContext = createContext<AppContextType>({} as AppContextType);

// Check if the user was defined
export function useUserContext() {
    const user = useContext(AppContext);

    if (user === undefined) {
        throw new Error('useUserContext must be used with an AppContext');
    }

    return user;
}
