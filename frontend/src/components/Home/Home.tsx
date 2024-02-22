import { useNavigate } from 'react-router-dom'
import './Home.css'
import premLogo from '../../assets/premlogo.png'
import serieAlogo from '../../assets/seriealogo.png'
import ligue1logo from '../../assets/ligue1logo.png'
import bundesLogo from '../../assets/bundesligalogo.png'
import laLigaLogo from '../../assets/laligalogo.png'
import Leaderboard from '../Leaderboard/Leaderboard'

const Home = () => {
    const navigate = useNavigate();
    return (
        <main className='home'>
            <section className="competitions">
                <h1>Competitions</h1>
                <div className="league-buttons">
                    <button className="league" onClick={() => navigate('/MatchGuessr/competition/PL')}><img src={premLogo} alt="Premier League"/></button>
                    <button className="league" onClick={() => navigate('/MatchGuessr/competition/PD')}><img src={laLigaLogo} alt="La Liga"/></button>
                    <button className="league" onClick={() => navigate('/MatchGuessr/competition/SA')}><img src={serieAlogo} alt="Serie A"/></button>
                    <button className="league" onClick={() => navigate('/MatchGuessr/competition/BL1')}><img src={bundesLogo} alt="Bundesliga"/></button>
                    <button className="league" onClick={() => navigate('/MatchGuessr/competition/FL1')}><img src={ligue1logo} alt="Ligue 1"/></button>
                </div>
            </section>
            <Leaderboard />
        </main>
    )
}

export default Home