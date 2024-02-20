//const baseURL = "https://matchguessr.azurewebsites.net/guess"
const baseURL = "http://localhost:8080/guess"

export enum Result {
    HOME = 'HOME',
    AWAY = 'AWAY',
    DRAW = 'DRAW',
}

export interface Guess {
    matchId: number;
    result: Result;
}

interface Match {
    id: number;
    homeTeam: {
        id: number;
        name: string;
        crest: string;
        code: string;
    },
    awayTeam: {
        id: number;
        name: string;
        crest: string;
        code: string;
    },
    homeGoals: number;
    awayGoals: number;
    status: string;
    matchDate: string;
    round: number;
    result: Result;
}

const getGuesses = async (code: string, token: string | null) => {
    token = localStorage.getItem('token');
    const res = await fetch(baseURL+`/matches/${code}`, {
        method: "GET",
        headers: {
            "Authorization": `Bearer ${token}`
        },
    });
    return await res.json() as Match[];
}

const makeGuess = async (guess: Guess, token: string | null) => {
    const res = await fetch(baseURL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(guess)
    });
    if (res.status !== 201) {
        throw new Error("Failed to make guess");
    }
    return res.status === 201;
}

const updateGuess = async (guess: Guess, token: string | null) => {
    const res = await fetch(baseURL, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}`
        },
        body: JSON.stringify(guess)
    });
    if (res.status !== 200) {
        throw new Error("Failed to update guess");
    }
    return res.status === 200;
}

export default { getGuesses, makeGuess, updateGuess }

