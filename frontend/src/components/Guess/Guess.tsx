import './Guess.css';
import guessService, { Result } from '../../services/guess';
import { useMutation, useQuery, useQueryClient } from '@tanstack/react-query';
import { useContext, useState } from 'react';
import { TokenContext } from '../../contexts/TokenContext';
import { Match } from '../../services/league';

interface GuessProps {
    code: string;
}

const Guess = (props: GuessProps) => {
    const queryClient = useQueryClient();
    const { token } = useContext(TokenContext);
    const [round, setRound] = useState(1);

    const {data: matches, isLoading, error} = useQuery({
        queryKey: ['matches', props.code],
        queryFn: () => guessService.getGuesses(props.code, token),
        staleTime: 1000 * 60 * 5,
    });

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
            }
        },
    });

    if (isLoading) {
        return <div>Loading</div>
    }

    if (error instanceof Error) {
        return <div>Error: something went wrong</div>
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

    const guessMatch = (matchId: number, choice: Result, result: Result) => {
        mutation.mutate({matchId, choice, result});
    }

    return (
        matches && (
            <main className='guess'>
                <div className = 'matchweek'>
                    <button className='paginator' onClick={() => round > 1 && setRound(round - 1)}>{'<'}</button>
                    <h2>Matchweek {round}</h2>
                    <button className='paginator' onClick={() => round < Math.max(...matches.map(match => match.round)) && setRound(round + 1)}>{'>'}</button>
                </div>
                <div className='matches'>
                    {matches.filter(match => match.round == round).map((match, index) => (
                        <div className={'match'+correctGuess(match.status, match.result, match.homeGoals, match.awayGoals)} key={index}>
                            {match.status === 'LIVE' || match.status === 'POSTPONED' || match.status === 'CANCELLED'
                            ? <div>{match.status}</div>
                            : <div>{new Date(match.matchDate + 'Z').toLocaleString('en-US', {weekday: 'long', month: 'long', day: 'numeric', hour: '2-digit', minute: '2-digit', hour12: true, timeZone: 'America/New_York'})}</div>
                            }
                            <div className='match-buttons'>
                                <button 
                                    className={"home-team" + (match.result === 'HOME' ? ' selected' : '')}
                                    onClick={() => guessMatch(match.id, Result.HOME, match.result)}
                                    disabled={match.status === 'LIVE' || match.status === 'FINISHED'}>
                                    <img src={match.homeTeam.crest} alt={match.homeTeam.name}/>
                                    <div>{match.homeTeam.name}</div>
                                </button>
                                {match.status === 'FINISHED' || match.status === 'LIVE' 
                                ? <h1>{match.homeGoals} - {match.awayGoals}</h1>
                                : <button 
                                    className={"draw" + (match.result === 'DRAW' ? ' selected' : '')}
                                    onClick={() => guessMatch(match.id, Result.DRAW, match.result)}
                                    disabled={match.status === 'LIVE' || match.status === 'FINISHED'}>Draw</button>
                                }
                                <button 
                                    className={"away-team" + (match.result === 'AWAY' ? ' selected' : '')}
                                    onClick={() => guessMatch(match.id, Result.AWAY, match.result)}
                                    disabled={match.status === 'LIVE' || match.status === 'FINISHED'}>
                                    <img src={match.awayTeam.crest} alt={match.awayTeam.name}/>
                                    <div>{match.awayTeam.name}</div>
                                </button>
                            </div>
                        </div>
                    ))}
                </div>
            </main>
        )
    )
}

export default Guess;