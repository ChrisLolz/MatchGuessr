import { useQuery } from '@tanstack/react-query';
import leagueService from '../../services/league';
import './LeagueTable.css'
import { useNavigate } from 'react-router-dom';

interface LeagueProps {
    name: string;
    code: string;
}

const LeagueTable = (props: LeagueProps) => {
    const navigate = useNavigate();
    const { data, isLoading, error } = useQuery({
        queryKey: ['league', props.code],
        queryFn: () => leagueService.getLeagueStandings(props.code),
        staleTime: 1000 * 60 * 5, 
    })

    if (isLoading) {
        return <div>Loading {'('}sorry if it takes a while, server has to cold start if it hasn't been used in a while{')'}</div>
    }

    if (error instanceof Error) {
        return <div>Error: something went wrong</div>
    }

    const europeSpot = (position: number) => {
        if (props.code === 'PL' || props.code === 'PD' || props.code === 'SA' || props.code === 'BL1') {
            if (position <= 4) {
                return 'ucl';
            } else if (position === 5) {
                return 'uel';
            }
        } else {
            if (position <= 3) {
                return 'ucl';
            } else if (position === 4) {
                return 'uel';
            } else if (position === 5) {
                return 'uecl';
            }
        }
    }
    
    return (
        data &&
        <div className="league-table">
            <button onClick={()=>navigate('/MatchGuessr/competition/' + props.code + '/predict')}>Predict Matchweeks</button>
            <img className="league-logo" src={data[0].competition_Crest} alt="league crest" />
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
                    {data.map((team) => (
                        <tr key={Math.random()}>
                            <td className={'pos ' + europeSpot(data.indexOf(team) + 1)}>{data.indexOf(team) + 1}</td>
                            <td className="team-cell"><img className="team-logo" src={team.crest} alt="team crest" />{team.team_Name}</td>
                            <td className="stat">{team.matches_Played}</td>
                            <td className="stat">{team.wins}</td>
                            <td className="stat">{team.draws}</td>
                            <td className="stat">{team.losses}</td>
                            <td className="stat">{team.goals_For}</td>
                            <td className="stat">{team.goals_Against}</td>
                            <td className="stat">{team.goal_Difference}</td>
                            <td className="stat">{team.points}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    )
}

export default LeagueTable