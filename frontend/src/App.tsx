import LeagueTable from './components/LeagueTable/LeagueTable'
import Header from './components/Header/Header'
import { BrowserRouter, Route, Routes } from 'react-router-dom'
import Register from './components/Auth/Register'
import Login from './components/Auth/Login'
import useToken from './components/Auth/useToken'
import Home from './components/Home/Home'
import Guess from './components/Guess/Guess'
import { TokenContext } from './contexts/TokenContext'

const App = () => {
  const { token, setToken } = useToken();
  return (
    <TokenContext.Provider value={{ token, setToken }}>
      <BrowserRouter>
        <Header/>
        <Routes>
          <Route path="/MatchGuessr/*" element={
            <Routes>
              <Route path="/" element={<Home/>}/>
              <Route path="/auth/register" element={<Register/>} />
              <Route path="/auth/login" element={<Login/>} />
              <Route path="/competition/PL" element={<LeagueTable name="Premier League" code="PL"/>}/>
              <Route path="/competition/PD" element={<LeagueTable name="La Liga" code="PD"/>}/>
              <Route path="/competition/SA" element={<LeagueTable name="Serie A" code="SA"/>}/>
              <Route path="/competition/BL1" element={<LeagueTable name="Bundesliga" code="BL1"/>}/>
              <Route path="/competition/FL1" element={<LeagueTable name="Ligue 1" code="FL1"/>}/>
              <Route path="/competition/PL/predict" element={<Guess code="PL"/>}/>
              <Route path="/competition/PD/predict" element={<Guess code="PD"/>}/>
              <Route path="/competition/SA/predict" element={<Guess code="SA"/>}/>
              <Route path="/competition/BL1/predict" element={<Guess code="BL1"/>}/>
              <Route path="/competition/FL1/predict" element={<Guess code="FL1"/>}/>
            </Routes>
          }/>
        </Routes>
      </BrowserRouter>
    </TokenContext.Provider>
  )
}

export default App
