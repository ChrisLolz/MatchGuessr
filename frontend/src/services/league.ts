const baseURL = "https://matchguessr.azurewebsites.net/api"
// const baseURL = "http://localhost:8080/api"

interface Standing {
    points: number,
    team_Name: string,
    matches_Played: number,
    crest: string,
    competition_Crest: string,
    wins: number,
    draws: number,
    losses: number,
    goals_For: number,
    goals_Against: number,
    goal_Difference: number
    position: number
}

export interface Match {
    id: number,
    homeTeam: {
        name: string,
        code: string,
        crest: string
    },
    awayTeam: {
        name: string,
        code: string,
        crest: string
    },
    homeGoals: number,
    awayGoals: number,
    matchDate: Date,
    status: string,
    round: number
}

const getLeagueStandings = async (code: string) => {
    const res = await fetch(baseURL+`/competitions/standings/${code}`);
    return await res.json() as Standing[];
}

const getMatches = async (code: string) => {
    const res = await fetch(baseURL+`/matches/competition/${code}`);
    return await res.json() as Match[];
}

export default { getLeagueStandings, getMatches }