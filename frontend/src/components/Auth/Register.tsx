import React, { useContext, useState } from 'react';
import userService from '../../services/user';
import './Auth.css';
import { useNavigate } from 'react-router-dom';
import { TokenContext } from '../../contexts/TokenContext';
import { useQueryClient } from '@tanstack/react-query';

const Register = () => {
    const { setToken } = useContext(TokenContext);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const queryClient = useQueryClient();
    const navigate = useNavigate();

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        if (password !== confirmPassword) {
            alert('Passwords do not match');
            return;
        }
        try {
            const token = await userService.register(username, password);
            setToken(token);
            navigate('/MatchGuessr/');
            queryClient.invalidateQueries({queryKey: ['leaderboard']});
        } catch (error) {
            alert("Invalid username or password. Username may already be taken or username and password may be too short.");
            return;
        }
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