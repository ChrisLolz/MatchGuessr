import React, { useState } from 'react';
import userService from '../../services/user';
import './Auth.css';
import { useNavigate } from 'react-router-dom';

interface RegisterProps {
    setToken: (token: string) => void;
}

const Register = (props: RegisterProps) => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (password !== confirmPassword) {
            alert('Passwords do not match');
            return;
        }
        const token = await userService.register(username, password);
        if (token == null) {
            alert("Invalid username or password. Username may already be taken or username and password may be too short.");
            return;
        }
        props.setToken(token);
        navigate('/MatchGuessr/');
    }

    return (
        <main className='auth'>
            <form onSubmit={handleSubmit}>
                <h2>Register</h2>
                <input 
                    type="username"
                    placeholder="Username"
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Password"
                    onChange={(e) => setPassword(e.target.value)}
                />
                <input
                    type="password"
                    placeholder="Confirm Password"
                    onChange={(e) => setConfirmPassword(e.target.value)}
                />
                <button type="submit">Register</button>
            </form>
        </main>
    );
}

export default Register