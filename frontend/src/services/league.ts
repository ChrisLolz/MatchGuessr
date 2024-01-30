const baseURL = "https://matchguessr.azurewebsites.net/competitions/standings"

interface Standing {
    points: number,
    team_Name: string,
    matches_Played: number,
    wins: number,
    draws: number,
    losses: number,
    goals_For: number,
    goals_Against: number,
    goal_Difference: number
}

const getLeagueStandings = async (code: string): Promise<Standing[]> => {
    const res = await fetch(baseURL+`/${code}/2023`);
    return await res.json() as Standing[];
}

export default { getLeagueStandings }