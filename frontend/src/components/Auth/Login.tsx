import { useContext, useState } from 'react';
import userService from '../../services/user';
import { useNavigate } from 'react-router-dom';
import { TokenContext } from '../../contexts/TokenContext';
import { useQueryClient } from '@tanstack/react-query';

const Login = () => {
    const { setToken } = useContext(TokenContext);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const queryClient = useQueryClient();
    const navigate = useNavigate();
    
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        try {
            const token = await userService.login(username, password);
            setToken(token);
            navigate('/MatchGuessr/')
            queryClient.invalidateQueries({queryKey: ['leaderboard']});
        } catch (error) {
            alert("Invalid username or password");
            return;
        }
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