import { useState } from 'react';
import userService from '../../services/user';

interface LoginProps {
    setToken: (token: string) => void;
}

const Login = (props: LoginProps) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    
    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const token = await userService.login(username, password);
        if (token == null) {
            alert("Invalid username or password")
            return;
        }
        props.setToken(token);
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