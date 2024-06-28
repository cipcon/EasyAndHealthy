import { createContext, useContext } from 'react';
import { User } from '../App';

interface AppContextType {
    userCredentials: User;
    setUserCredentials: React.Dispatch<React.SetStateAction<User>>;
    logout: () => void;
}

export const AppContext = createContext<AppContextType | undefined>(undefined);

// Check if the user was defined
export function useUserContext() {
    const context = useContext(AppContext);

    if (context === undefined) {
        throw new Error('useUserContext must be used with an AppContext.Provider');
    }

    return context;
}
