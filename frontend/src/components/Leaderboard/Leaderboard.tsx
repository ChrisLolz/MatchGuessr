import { useState } from 'react';
import guessService from '../../services/guess';
import './Leaderboard.css'
import { useQuery, useQueryClient } from '@tanstack/react-query';

const Leaderboard = () => {
    const queryClient = useQueryClient();
    const [competition, setCompetition] = useState("All");

    const {data, isLoading, error} = useQuery({
        queryKey: ['leaderboard', competition],
        queryFn: () => guessService.getLeaderboard("/"+competition),
        staleTime: 1000 * 60 * 5,
    });

    const handleChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
        setCompetition(event.target.value);
        if (!queryClient.getQueryData(['leaderboard', event.target.value])) {
            queryClient.invalidateQueries({queryKey: ['leaderboard', event.target.value]});
        }
    }

    return (
        <main className="leaderboard">
            <h2>Leaderboard</h2>
            <select value={competition} onChange={handleChange}>
                <option value="All">All</option>
                <option value="PL">Premier League</option>
                <option value="PD">La Liga</option>
                <option value="SA">Serie A</option>
                <option value="BL1">Bundesliga</option>
                <option value="FL1">Ligue 1</option>
            </select>
            <table className="leaderboard-table">
                <thead>
                    <tr>
                        <th>Position</th>
                        <th>Username</th>
                        <th>Points</th>
                        <th>Correct Guesses</th>
                        <th>Guesses</th>
                    </tr>
                </thead>
                <tbody>
                    {data && data.map(leaderboard => (
                        <tr key={leaderboard.username}>
                            <td>{data.indexOf(leaderboard) + 1}</td>
                            <td>{leaderboard.username}</td>
                            <td>{leaderboard.points}</td>
                            <td>{leaderboard.correct_guesses}</td>
                            <td>{leaderboard.guesses}</td>
                        </tr>
                    ))}
                </tbody>
            </table>
            {isLoading && <div>Loading</div>}
            {error && <div>Error: something went wrong</div>}
        </main>
    )
}

export default Leaderboard;