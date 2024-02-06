import LeagueTable from './components/LeagueTable/LeagueTable'
import Header from './components/Header/Header'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Register from './components/Auth/Register'
import Login from './components/Auth/Login'
import useToken from './components/Auth/useToken'
import Home from './components/Home/Home'
import Guess from './components/Guess/Guess'

const App = () => {
  const { token, setToken } = useToken();
  return (
    <BrowserRouter>
      <Header token={token}/>
      <Routes>
        <Route path="/MatchGuessr/*" element={
          <Routes>
            <Route path="/" element={<Home/>}/>
            <Route path="/auth/register" element={<Register setToken={setToken}/>} />
            <Route path="/auth/login" element={<Login setToken={setToken}/>} />
            <Route path="/league/:code" element={<LeagueTable name="Premier League" code="PL"/>}/>
            <Route path="/league/:code/predict" element={<Guess code="PL"/>}/>
          </Routes>
        }/>
      </Routes>
    </BrowserRouter>
  )
}

export default App
