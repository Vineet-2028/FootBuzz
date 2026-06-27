import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import "../App.css";

function Home() {
  const [players, setPlayers] = useState([]);
  const [search, setSearch] = useState("");
  const [searchType, setSearchType] = useState("name");
  const [page, setPage] = useState(0);

  const navigate = useNavigate();

  useEffect(() => {
    fetchPlayers(0);
  }, []);

  const fetchPlayers = (pageNumber = 0) => {
    axios
      .get(`http://localhost:8080/players?page=${pageNumber}&size=12`)
      .then((res) => {
        setPlayers(res.data.content);
        setPage(pageNumber);
      })
      .catch((err) => console.log(err));
  };

  const searchPlayers = () => {
    if (search.trim() === "") {
      fetchPlayers(0);
      return;
    }

    let url = "";

    if (searchType === "name") {
      url = `http://localhost:8080/players/search?name=${search}`;
    } else if (searchType === "club") {
      url = `http://localhost:8080/players/club?club=${search}`;
    } else if (searchType === "position") {
      url = `http://localhost:8080/players/position?position=${search}`;
    } else if (searchType === "id") {
      url = `http://localhost:8080/players/${search}`;
    }

    axios
      .get(url)
      .then((res) => {
        if (searchType === "id") {
          setPlayers(res.data ? [res.data] : []);
        } else {
          setPlayers(res.data);
        }
      })
      .catch((err) => {
        console.log(err);
        setPlayers([]);
      });
  };

  const clearSearch = () => {
    setSearch("");
    setSearchType("name");
    fetchPlayers(0);
  };

  return (
    <div className="app">
      <aside className="sidebar">
        <div>
          <div className="logo">
            ⚽ Foot<span>Buzz</span>
          </div>

          <nav>
            <p className="active">Players</p>
            <p>Clubs</p>
            <p>Competitions</p>
            <p>Transfers</p>
            <p>Stats</p>
            <p>Favorites</p>
          </nav>
        </div>

        <div className="side-card">
          <div className="ball">⚽</div>
          <h3>Live the game.</h3>
          <h3>Love the data.</h3>
          <span>FootBuzz</span>
        </div>
      </aside>

      <main className="main">
        <div className="topbar">
          <button className="icon-btn">🌙</button>
          <button className="fav-btn">☆ My Favorites</button>
        </div>

        <section className="hero">
          <h1>
            Discover <span>Amazing</span> Players
          </h1>
          <p>Search football profiles by name, ID, club, or position.</p>

          <div className="search-box">
            <select
              value={searchType}
              onChange={(e) => setSearchType(e.target.value)}
            >
              <option value="name">Name</option>
              <option value="id">ID</option>
              <option value="club">Club</option>
              <option value="position">Position</option>
            </select>

            <input
              value={search}
              onChange={(e) => setSearch(e.target.value)}
              placeholder={
                searchType === "id"
                  ? "Enter player ID..."
                  : `Search by ${searchType}...`
              }
              onKeyDown={(e) => {
                if (e.key === "Enter") searchPlayers();
              }}
            />

            <button onClick={searchPlayers}>Search</button>
            <button onClick={clearSearch} className="clear">
              Clear
            </button>
          </div>
        </section>

        <section className="grid">
          {players.map((player, index) => (
            <div
              className="card"
              key={player.player_id}
              onClick={() => navigate(`/player/${player.player_id}`)}
            >
              <div className="rating">{94 - (index % 8)}</div>

              <button
                className="heart"
                onClick={(e) => {
                  e.stopPropagation();
                }}
              >
                ♡
              </button>

              <div className="image-wrap">
                <img src={player.player_image_url} alt={player.player_name} />
              </div>

              <div className="card-body">
                <h2>{player.player_name}</h2>
                <p className="club">{player.current_club_name}</p>

                <div className="meta">
                  <span>Age: {player.age}</span>
                  <span>{player.foot}</span>
                </div>

                <p className="position">{player.position}</p>
              </div>
            </div>
          ))}
        </section>

        <div className="pagination">
          <button onClick={() => fetchPlayers(Math.max(page - 1, 0))}>
            Prev
          </button>
          <span>Page {page + 1}</span>
          <button onClick={() => fetchPlayers(page + 1)}>Next</button>
        </div>
      </main>
    </div>
  );
}

export default Home;