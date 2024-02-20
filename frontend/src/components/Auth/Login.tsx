import { useContext, useState } from 'react';
import userService from '../../services/user';
import { useNavigate } from 'react-router-dom';
import { TokenContext } from '../../contexts/TokenContext';

const Login = () => {
    const { setToken } = useContext(TokenContext);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();
    
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const token = await userService.login(username, password);
        if (token == null) {
            alert("Invalid username or password")
            return;
        }
        setToken(token);
        navigate('/MatchGuessr/')
    };
    
    return (
        <main className='auth'>
            <form onSubmit={handleSubmit}>
                <h2>Login</h2>
                <input
                    type="username"
                    placeholder='Username'
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    type="password"
                    placeholder='Password'
                    onChange={(e) => setPassword(e.target.value)}
                />
                <button type="submit">Login</button>
            </form>
        </main>
    );
}

export default Login