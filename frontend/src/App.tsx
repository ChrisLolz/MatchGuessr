import LeagueTable from './components/LeagueTable/LeagueTable'
import Header from './components/Header/Header'

function App() {
  
  return (
    <>
      <Header/>
      <main>
        <LeagueTable name="Premier League" code="PL"/>
      </main>
    </>
  )
}

export default App
