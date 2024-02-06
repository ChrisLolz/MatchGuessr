import { useNavigate } from 'react-router-dom'
import './Home.css'

const Home = () => {
    const navigate = useNavigate();
    return (
        <main>
            <button onClick={() => navigate('/MatchGuessr/league/PL')}>Premier League</button>
        </main>
    )
}

export default Home