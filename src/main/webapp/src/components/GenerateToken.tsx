export const generateToken = (userId: number) => {
    return `${userId}-${Math.random().toString(36)}`
}
