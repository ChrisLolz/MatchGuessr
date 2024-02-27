import './Guess.css';
import guessService, { Result } from '../../services/guess';
import leagueService from '../../services/league';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useContext, useEffect, useState } from 'react';
import { TokenContext } from '../../contexts/TokenContext';
import { Match } from '../../services/league';
import { useNavigate } from 'react-router-dom';

interface GuessProps {
    code: string;
}

const Guess = (props: GuessProps) => {
    const queryClient = useQueryClient();
    const [round, setRound] = useState(1);
    const { token } = useContext(TokenContext);
    const navigate = useNavigate();

    useEffect(() => {
        if (!localStorage.getItem('token')) {
            navigate('/MatchGuessr/auth/login');
        }
    }, [navigate]);

    useEffect(() => {
        if (localStorage.getItem('round-' + props.code)) {
            setRound(parseInt(localStorage.getItem('round-' + props.code) as string));
        }
    }, [props.code]);

    const {data: matches, isLoading, error} = useQuery({
        queryKey: ['matches', props.code],
        queryFn: () => guessService.getGuesses(props.code, token),
        staleTime: 1000 * 60,
    });

    const { data: leagueTable } = useQuery({
        queryKey: ['league', props.code],
        queryFn: async () => {
            const league = await leagueService.getLeagueStandings(props.code);
            return league.map((team, index) => ({
                ...team,
                position: index + 1
            }));
        },
        staleTime: 1000 * 60 * 5, 
    })

    const mutation = useMutation({
        mutationFn: (guess: {matchId: number, choice: Result, result: Result}) => {
            if (guess.result == null) {
                return guessService.makeGuess({matchId: guess.matchId, result: guess.choice}, token);
            } else if (guess.result === guess.choice) {
                return Promise.resolve(true);
            } else {
                return guessService.updateGuess({matchId: guess.matchId, result: guess.choice}, token);
            }
        },
        onSuccess: (_data, variables) => {
            const cache = queryClient.getQueryData(['matches', props.code]) as Match[];
            if (cache) {
                const match = cache.map(match => match.id === variables.matchId ? {...match, result: variables.choice} : match);
                queryClient.setQueryData(['matches', props.code], match);
                queryClient.invalidateQueries({queryKey: ['leaderboard']});
            }
        },
    });

    if (isLoading) {
        return <div>Loading</div>
    }

    if (error instanceof Error) {
        return <div>Error: something went wrong</div>
    }

    const handleButtonClick = (direction: string) => {
        setRound(prevRound => {
            let newRound = prevRound;
            if (direction === 'next' && matches && prevRound < Math.max(...matches.map(match => match.round))) {
                newRound = prevRound + 1;
            } else if (direction === 'previous' && prevRound > 1) {
                newRound = prevRound - 1;
            }
            localStorage.setItem('round-' + props.code, newRound.toString());
            return newRound;
        });
    }


    const correctGuess = (status: string, result: Result, homeGoals: number, awayGoals: number) => {
        if (status === 'FINISHED' && result === Result.HOME && homeGoals > awayGoals) {
            return ' correct';
        } else if (status === 'FINISHED' && result === Result.AWAY && awayGoals > homeGoals) {
            return ' correct';
        } else if (status === 'FINISHED' && result === Result.DRAW && homeGoals === awayGoals) {
            return ' correct';
        } else if (status === 'FINISHED' && result) {
            return ' incorrect';
        } else {
            return '';
        }
    }

    const getOrdinalSuffix = (position: number) => {
        const s = ["th", "st", "nd", "rd"];
        const v = position % 100;
        return position + (s[(v - 20) % 10] || s[v] || s[0]);
    }

    const guessMatch = (matchId: number, choice: Result, result: Result) => {
        mutation.mutate({matchId, choice, result});
    }

    return (
        matches && (
            <main className='guess'>
                <button className='leaguetable-button' onClick={() => navigate('/MatchGuessr/competition/' + props.code)}>Go back to league table</button>
                <div className = 'matchweek'>
                    <button className='paginator' onClick={()=>handleButtonClick('previous')}>{'<'}</button>
                    <h2>Matchweek {round}</h2>
                    <button className='paginator' onClick={()=>handleButtonClick('next')}>{'>'}</button>
                </div>
                <div className='matches'>
                    {matches.filter(match => match.round == round).map((match, index) => (
                        <div className={'match'+correctGuess(match.status, match.result, match.homeGoals, match.awayGoals)} key={index}>
                            {match.status === 'LIVE' || match.status === 'POSTPONED' || match.status === 'CANCELLED'
                            ? <div>{match.status}</div>
                            : <div>{new Date(match.matchDate + 'Z').toLocaleString('en-US', {weekday: 'long', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit', hour12: true, timeZone: 'America/New_York'})}</div>
                            }
                            <div className='match-buttons'>
                                <div className='home-team'>
                                    <button 
                                        className={match.result === 'HOME' ? 'selected' : ''}
                                        onClick={() => guessMatch(match.id, Result.HOME, match.result)}
                                        disabled={match.status === 'LIVE' || match.status === 'FINISHED'}>
                                        <img src={match.homeTeam.crest} alt={match.homeTeam.name}/>
                                        <div>{match.homeTeam.name}</div>
                                    </button>
                                    <div className='position'>{getOrdinalSuffix(leagueTable?.find(team => team.team_Name === match.homeTeam.name)?.position ?? 0)}</div>
                                </div>
                                {match.status === 'FINISHED' || match.status === 'LIVE' 
                                ? <h1 className={match.result === 'DRAW' ? 'score-selected' : ''}>{match.homeGoals} - {match.awayGoals}</h1>
                                : <button 
                                    className={"draw" + (match.result === 'DRAW' ? ' selected' : '')}
                                    onClick={() => guessMatch(match.id, Result.DRAW, match.result)}
                                    disabled={match.status === 'LIVE' || match.status === 'FINISHED'}>Draw</button>
                                }
                                <div className='away-team'>
                                    <button 
                                        className={match.result === 'AWAY' ? 'selected' : ''}
                                        onClick={() => guessMatch(match.id, Result.AWAY, match.result)}
                                        disabled={match.status === 'LIVE' || match.status === 'FINISHED'}>
                                        <img src={match.awayTeam.crest} alt={match.awayTeam.name}/>
                                        <div>{match.awayTeam.name}</div>
                                    </button>
                                    <div className='position'>{getOrdinalSuffix(leagueTable?.find(team => team.team_Name === match.awayTeam.name)?.position ?? 0)}</div>
                                </div>
                            </div>
                        </div>
                    ))}
                </div>
            </main>
        )
    )
}

export default Guess;