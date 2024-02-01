import { useQuery } from '@tanstack/react-query';
import leagueService from '../../services/league';
import './LeagueTable.css'

interface LeagueProps {
    name: string;
    code: string;
}

const LeagueTable = (props: LeagueProps) => {
    const { data, isLoading, error } = useQuery({
        queryKey: ['league', props.code],
        queryFn: () =>
          leagueService.getLeagueStandings(props.code),})

    if (isLoading) {
        return <div>Loading {'('}sorry if it takes a while, server has to cold start if it hasn't been used in a while{')'}</div>
    }

    if (error instanceof Error) {
        return <div>Error: something went wrong</div>
    }
    
    return (
        <div className="league-table">
            <h2 className="league-title">{props.name} {'('}updates every 5min{')'}</h2>
            <table className='table'>
                <thead>
                    <tr>
                        <th>Pos</th>
                        <th>Team</th>
                        <th>GP</th>
                        <th>W</th>
                        <th>D</th>
                        <th>L</th>
                        <th>GF</th>
                        <th>GA</th>
                        <th>GD</th>
                        <th>Pts</th>
                    </tr>
                </thead>
                <tbody>
                    {data?.map((team) => (
                        <tr key={Math.random()}>
                            <td className={data.indexOf(team) + 1 <=4 ? 'ucl' : data.indexOf(team) + 1 == 5 ? 'uel' : ''}>{data.indexOf(team) + 1}</td>
                            <td>{team.team_Name}</td>
                            <td>{team.matches_Played}</td>
                            <td>{team.wins}</td>
                            <td>{team.draws}</td>
                            <td>{team.losses}</td>
                            <td>{team.goals_For}</td>
                            <td>{team.goals_Against}</td>
                            <td>{team.goal_Difference}</td>
                            <td>{team.points}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}

export default LeagueTable