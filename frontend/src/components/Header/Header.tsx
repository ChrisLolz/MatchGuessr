import { useContext } from 'react'
import './Header.css'
import { Link, useNavigate } from 'react-router-dom'
import { TokenContext } from '../../contexts/TokenContext'

const Header = () => {
    const navigate = useNavigate()
    const { token } = useContext(TokenContext);

    const logout = () => {
        localStorage.removeItem('token');
        window.location.reload();
    }

    return (
        <header>
            <div className="left-header">
                <Link to="/MatchGuessr/">
                    <h2>MatchGuessr</h2>
                </Link>
                <nav>
                    <ul>
                        <li><Link to="/MatchGuessr/competition/PL">Premier League</Link></li>
                        <li><Link to="/MatchGuessr/competition/BL1">Bundesliga</Link></li>
                        <li><Link to="/MatchGuessr/competition/PD">La Liga</Link></li>
                        <li><Link to="/MatchGuessr/competition/SA">Serie A</Link></li>
                        <li><Link to="/MatchGuessr/competition/FL1">Ligue 1</Link></li>
                    </ul>
                </nav>
            </div>
            {!token &&
                <div className="auth-buttons">
                    <button onClick={() => navigate('/MatchGuessr/auth/register')}>Register</button>
                    <button onClick={() => navigate('/MatchGuessr/auth/login')}>Login</button>
                </div>
            }
            {token &&
                <div className="auth-buttons">
                    <button onClick={() => logout()}>Logout</button>
                </div>
            }
        </header>
    )
}

export default Header