import { useEffect } from 'react';
import './Header.css'
import { Link, useNavigate } from 'react-router-dom'

interface HeaderProps {
    token: string | null;
}

const Header = (props: HeaderProps) => {
    const navigate = useNavigate()

    useEffect(() => {
        if (props.token) {
            navigate('/MatchGuessr/')
        }
    }, [props.token, navigate]);

    return (
        <header>
            <Link to="/MatchGuessr/">
                <h1>MatchGuessr</h1>
            </Link>
            {!props.token &&
                <div className="auth-buttons">
                    <button onClick={() => navigate('/MatchGuessr/auth/register')}>Register</button>
                    <button onClick={() => navigate('/MatchGuessr/auth/login')}>Login</button>
                </div>
            }
        </header>
    )
}

export default Header