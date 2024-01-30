import './Header.css'

const Header = () => {
    return (
        <header>
            <h1>MatchGuessr</h1>
            <div className="auth-buttons">
                <button>Sign Up</button>
                <button>Log In</button>
            </div>
        </header>
    )
}

export default Header